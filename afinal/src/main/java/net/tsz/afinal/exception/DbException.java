package net.tsz.afinal.exception;

/**
 * Created by LiuWeiJie on 2015/7/25 0025.
 * Email:1031066280@qq.com
 */

public class DbException extends AfinalException {
    private static final long serialVersionUID = 1L;

    public DbException() {
    }

    public DbException(String msg) {
        super(msg);
    }

    public DbException(Throwable ex) {
        super(ex);
    }

    public DbException(String msg, Throwable ex) {
        super(msg, ex);
    }
}
