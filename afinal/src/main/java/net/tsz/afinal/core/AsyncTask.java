package net.tsz.afinal.core;

/**
 * Created by LiuWeiJie on 2015/7/25 0025.
 * Email:1031066280@qq.com
 */
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.util.Log;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.ThreadPoolExecutor.DiscardOldestPolicy;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import net.tsz.afinal.core.ArrayDeque;

public abstract class AsyncTask<Params, Progress, Result> {
    private static final String LOG_TAG = "AsyncTask";
    private static final int CORE_POOL_SIZE = 5;
    private static final int MAXIMUM_POOL_SIZE = 128;
    private static final int KEEP_ALIVE = 1;
    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        public Thread newThread(Runnable r) {
            Thread tread = new Thread(r, "AsyncTask #" + this.mCount.getAndIncrement());
            tread.setPriority(4);
            return tread;
        }
    };
    private static final BlockingQueue<Runnable> sPoolWorkQueue = new LinkedBlockingQueue(10);
    public static final Executor THREAD_POOL_EXECUTOR;
    public static final Executor SERIAL_EXECUTOR;
    public static final Executor DUAL_THREAD_EXECUTOR;
    private static final int MESSAGE_POST_RESULT = 1;
    private static final int MESSAGE_POST_PROGRESS = 2;
    private static final AsyncTask.InternalHandler sHandler;
    private static volatile Executor sDefaultExecutor;
    private final AsyncTask.WorkerRunnable<Params, Result> mWorker;
    private final FutureTask<Result> mFuture;
    private volatile AsyncTask.Status mStatus;
    private final AtomicBoolean mCancelled;
    private final AtomicBoolean mTaskInvoked;

    static {
        THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(5, 128, 1L, TimeUnit.SECONDS, sPoolWorkQueue, sThreadFactory, new DiscardOldestPolicy());
        SERIAL_EXECUTOR = new AsyncTask.SerialExecutor((AsyncTask.SerialExecutor)null);
        DUAL_THREAD_EXECUTOR = Executors.newFixedThreadPool(3, sThreadFactory);
        sHandler = new AsyncTask.InternalHandler((AsyncTask.InternalHandler)null);
        sDefaultExecutor = SERIAL_EXECUTOR;
    }

    public static void init() {
        sHandler.getLooper();
    }

    public static void setDefaultExecutor(Executor exec) {
        sDefaultExecutor = exec;
    }

    public AsyncTask() {
        this.mStatus = AsyncTask.Status.PENDING;
        this.mCancelled = new AtomicBoolean();
        this.mTaskInvoked = new AtomicBoolean();
        this.mWorker = new AsyncTask.WorkerRunnable((AsyncTask.WorkerRunnable)null) {
            public Result call() throws Exception {
                AsyncTask.this.mTaskInvoked.set(true);
                Process.setThreadPriority(10);
                return AsyncTask.this.postResult(AsyncTask.this.doInBackground((Params[]) this.mParams));
            }
        };
        this.mFuture = new FutureTask(this.mWorker) {
            protected void done() {
                try {
                    AsyncTask.this.postResultIfNotInvoked((Result) this.get());
                } catch (InterruptedException var2) {
                    Log.w("AsyncTask", var2);
                } catch (ExecutionException var3) {
                    throw new RuntimeException("An error occured while executing doInBackground()", var3.getCause());
                } catch (CancellationException var4) {
                    AsyncTask.this.postResultIfNotInvoked((Result) null);
                }

            }
        };
    }

    private void postResultIfNotInvoked(Result result) {
        boolean wasTaskInvoked = this.mTaskInvoked.get();
        if(!wasTaskInvoked) {
            this.postResult(result);
        }

    }

    private Result postResult(Result result) {
        Message message = sHandler.obtainMessage(1, new AsyncTask.AsyncTaskResult(this, new Object[]{result}));
        message.sendToTarget();
        return result;
    }

    public final AsyncTask.Status getStatus() {
        return this.mStatus;
    }

    protected abstract Result doInBackground(Params... var1);

    protected void onPreExecute() {
    }

    protected void onPostExecute(Result result) {
    }

    protected void onProgressUpdate(Progress... values) {
    }

    protected void onCancelled(Result result) {
        this.onCancelled();
    }

    protected void onCancelled() {
    }

    public final boolean isCancelled() {
        return this.mCancelled.get();
    }

    public final boolean cancel(boolean mayInterruptIfRunning) {
        this.mCancelled.set(true);
        return this.mFuture.cancel(mayInterruptIfRunning);
    }

    public final Result get() throws InterruptedException, ExecutionException {
        return this.mFuture.get();
    }

    public final Result get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return this.mFuture.get(timeout, unit);
    }

    public final AsyncTask<Params, Progress, Result> execute(Params... params) {
        return this.executeOnExecutor(sDefaultExecutor, params);
    }

    public final AsyncTask<Params, Progress, Result> executeOnExecutor(Executor exec, Params... params) {
        if(this.mStatus != AsyncTask.Status.PENDING) {
            switch(this.mStatus.ordinal()) {
                case 2:
                    throw new IllegalStateException("Cannot execute task: the task is already running.");
                case 3:
                    throw new IllegalStateException("Cannot execute task: the task has already been executed (a task can be executed only once)");
            }
        }

        this.mStatus = AsyncTask.Status.RUNNING;
        this.onPreExecute();
        this.mWorker.mParams = params;
        exec.execute(this.mFuture);
        return this;
    }

    public static void execute(Runnable runnable) {
        sDefaultExecutor.execute(runnable);
    }

    protected final void publishProgress(Progress... values) {
        if(!this.isCancelled()) {
            sHandler.obtainMessage(2, new AsyncTask.AsyncTaskResult(this, values)).sendToTarget();
        }

    }

    private void finish(Result result) {
        if(this.isCancelled()) {
            this.onCancelled(result);
        } else {
            this.onPostExecute(result);
        }

        this.mStatus = AsyncTask.Status.FINISHED;
    }

    private static class AsyncTaskResult<Data> {
        final AsyncTask mTask;
        final Data[] mData;

        AsyncTaskResult(AsyncTask task, Data... data) {
            this.mTask = task;
            this.mData = data;
        }
    }

    private static class InternalHandler extends Handler {
        private InternalHandler(InternalHandler internalHandler) {
        }

        public void handleMessage(Message msg) {
            AsyncTask.AsyncTaskResult result = (AsyncTask.AsyncTaskResult)msg.obj;
            switch(msg.what) {
                case 1:
                    result.mTask.finish(result.mData[0]);
                    break;
                case 2:
                    result.mTask.onProgressUpdate(result.mData);
            }

        }
    }

    private static class SerialExecutor implements Executor {
        final ArrayDeque<Runnable> mTasks;
        Runnable mActive;

        private SerialExecutor(SerialExecutor serialExecutor) {
            this.mTasks = new ArrayDeque();
        }

        public synchronized void execute(final Runnable r) {
            this.mTasks.offer(new Runnable() {
                public void run() {
                    try {
                        r.run();
                    } finally {
                        SerialExecutor.this.scheduleNext();
                    }

                }
            });
            if(this.mActive == null) {
                this.scheduleNext();
            }

        }

        protected synchronized void scheduleNext() {
            if((this.mActive = (Runnable)this.mTasks.poll()) != null) {
                AsyncTask.THREAD_POOL_EXECUTOR.execute(this.mActive);
            }

        }
    }

    public static enum Status {
        PENDING,
        RUNNING,
        FINISHED;

        private Status() {
        }
    }

    private abstract static class WorkerRunnable<Params, Result> implements Callable<Result> {
        Params[] mParams;

        private WorkerRunnable(WorkerRunnable workerRunnable) {
        }
    }
}
