package net.tsz.afinal.exception;

/**
 * Created by LiuWeiJie on 2015/7/25 0025.
 * Email:1031066280@qq.com
 */
public class AfinalException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public AfinalException() {
    }

    public AfinalException(String msg) {
        super(msg);
    }

    public AfinalException(Throwable ex) {
        super(ex);
    }

    public AfinalException(String msg, Throwable ex) {
        super(msg, ex);
    }
}

