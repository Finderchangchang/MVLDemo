package net.tsz.afinal.exception;

/**
 * Created by LiuWeiJie on 2015/7/25 0025.
 * Email:1031066280@qq.com
 */
public class ViewException extends AfinalException {
    private static final long serialVersionUID = 1L;
    private String strMsg = null;

    public ViewException(String strExce) {
        this.strMsg = strExce;
    }

    public void printStackTrace() {
        if (this.strMsg != null) {
            System.err.println(this.strMsg);
        }
        super.printStackTrace();
    }
}
