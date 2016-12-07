package net.tsz.afinal.annotation.view;

/**
 * Created by LiuWeiJie on 2015/7/25 0025.
 * Email:1031066280@qq.com
 */
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import java.lang.reflect.Method;
import net.tsz.afinal.exception.ViewException;

public class EventListener implements OnClickListener, OnLongClickListener, OnItemClickListener, OnItemSelectedListener, OnItemLongClickListener {
    private Object handler;
    private String clickMethod;
    private String longClickMethod;
    private String itemClickMethod;
    private String itemSelectMethod;
    private String nothingSelectedMethod;
    private String itemLongClickMehtod;

    public EventListener(Object handler) {
        this.handler = handler;
    }

    public EventListener click(String method) {
        this.clickMethod = method;
        return this;
    }

    public EventListener longClick(String method) {
        this.longClickMethod = method;
        return this;
    }

    public EventListener itemLongClick(String method) {
        this.itemLongClickMehtod = method;
        return this;
    }

    public EventListener itemClick(String method) {
        this.itemClickMethod = method;
        return this;
    }

    public EventListener select(String method) {
        this.itemSelectMethod = method;
        return this;
    }

    public EventListener noSelect(String method) {
        this.nothingSelectedMethod = method;
        return this;
    }

    public boolean onLongClick(View v) {
        return invokeLongClickMethod(this.handler, this.longClickMethod, new Object[]{v});
    }

    public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        return invokeItemLongClickMethod(this.handler, this.itemLongClickMehtod, new Object[]{arg0, arg1, Integer.valueOf(arg2), Long.valueOf(arg3)});
    }

    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        invokeItemSelectMethod(this.handler, this.itemSelectMethod, new Object[]{arg0, arg1, Integer.valueOf(arg2), Long.valueOf(arg3)});
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        invokeNoSelectMethod(this.handler, this.nothingSelectedMethod, new Object[]{arg0});
    }

    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        invokeItemClickMethod(this.handler, this.itemClickMethod, new Object[]{arg0, arg1, Integer.valueOf(arg2), Long.valueOf(arg3)});
    }

    public void onClick(View v) {
        invokeClickMethod(this.handler, this.clickMethod, new Object[]{v});
    }

    private static Object invokeClickMethod(Object handler, String methodName, Object... params) {
        if(handler == null) {
            return null;
        } else {
            Method method = null;

            try {
                method = handler.getClass().getDeclaredMethod(methodName, new Class[]{View.class});
                if(method != null) {
                    return method.invoke(handler, params);
                } else {
                    throw new ViewException("no such method:" + methodName);
                }
            } catch (Exception var5) {
                var5.printStackTrace();
                return null;
            }
        }
    }

    private static boolean invokeLongClickMethod(Object handler, String methodName, Object... params) {
        if(handler == null) {
            return false;
        } else {
            Method method = null;

            try {
                method = handler.getClass().getDeclaredMethod(methodName, new Class[]{View.class});
                if(method != null) {
                    Object e = method.invoke(handler, params);
                    return e == null?false:Boolean.valueOf(e.toString()).booleanValue();
                } else {
                    throw new ViewException("no such method:" + methodName);
                }
            } catch (Exception var5) {
                var5.printStackTrace();
                return false;
            }
        }
    }

    private static Object invokeItemClickMethod(Object handler, String methodName, Object... params) {
        if(handler == null) {
            return null;
        } else {
            Method method = null;

            try {
                method = handler.getClass().getDeclaredMethod(methodName, new Class[]{AdapterView.class, View.class, Integer.TYPE, Long.TYPE});
                if(method != null) {
                    return method.invoke(handler, params);
                } else {
                    throw new ViewException("no such method:" + methodName);
                }
            } catch (Exception var5) {
                var5.printStackTrace();
                return null;
            }
        }
    }

    private static boolean invokeItemLongClickMethod(Object handler, String methodName, Object... params) {
        if(handler == null) {
            throw new ViewException("invokeItemLongClickMethod: handler is null :");
        } else {
            Method method = null;

            try {
                method = handler.getClass().getDeclaredMethod(methodName, new Class[]{AdapterView.class, View.class, Integer.TYPE, Long.TYPE});
                if(method != null) {
                    Object e = method.invoke(handler, params);
                    return Boolean.valueOf(e == null?false:Boolean.valueOf(e.toString()).booleanValue()).booleanValue();
                } else {
                    throw new ViewException("no such method:" + methodName);
                }
            } catch (Exception var5) {
                var5.printStackTrace();
                return false;
            }
        }
    }

    private static Object invokeItemSelectMethod(Object handler, String methodName, Object... params) {
        if(handler == null) {
            return null;
        } else {
            Method method = null;

            try {
                method = handler.getClass().getDeclaredMethod(methodName, new Class[]{AdapterView.class, View.class, Integer.TYPE, Long.TYPE});
                if(method != null) {
                    return method.invoke(handler, params);
                } else {
                    throw new ViewException("no such method:" + methodName);
                }
            } catch (Exception var5) {
                var5.printStackTrace();
                return null;
            }
        }
    }

    private static Object invokeNoSelectMethod(Object handler, String methodName, Object... params) {
        if(handler == null) {
            return null;
        } else {
            Method method = null;

            try {
                method = handler.getClass().getDeclaredMethod(methodName, new Class[]{AdapterView.class});
                if(method != null) {
                    return method.invoke(handler, params);
                } else {
                    throw new ViewException("no such method:" + methodName);
                }
            } catch (Exception var5) {
                var5.printStackTrace();
                return null;
            }
        }
    }
}
