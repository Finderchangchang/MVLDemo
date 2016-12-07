package net.tsz.afinal.model;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 需要进行改变的组件集合
 * Created by Administrator on 2016/5/26.
 */
public class ChangeItem {
    private TextView tv;
    private ImageView iv;
    private RelativeLayout rl;
    private LinearLayout ll;

    public ChangeItem(TextView mtv, ImageView miv) {
        tv = mtv;
        iv = miv;
    }

    public TextView getTv() {
        return tv;
    }

    public void setTv(TextView tv) {
        this.tv = tv;
    }

    public ImageView getIv() {
        return iv;
    }

    public void setIv(ImageView iv) {
        this.iv = iv;
    }

    public RelativeLayout getRl() {
        return rl;
    }

    public void setRl(RelativeLayout rl) {
        this.rl = rl;
    }

    public LinearLayout getLl() {
        return ll;
    }

    public void setLl(LinearLayout ll) {
        this.ll = ll;
    }
}
