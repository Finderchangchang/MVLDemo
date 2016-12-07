package net.tsz.afinal.view;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import net.tsz.afinal.R;

/**
 * Created by Administrator on 2015/10/16.
 */
public class NormalDialog extends Dialog {
    TextView title;
    Button ensure_btn, cancel_btn;
    TextView mes;

    public NormalDialog(Context context) {
        super(context, R.style.Base_Theme_AppCompat_Light_Dialog);
        View mView = LayoutInflater.from(getContext()).inflate(R.layout.normal_dialog, null);
        title = (TextView) mView.findViewById(R.id.title_ll_dialog);
        ensure_btn = (Button) mView.findViewById(R.id.ensure_btn_ll_dialog);
        cancel_btn = (Button) mView.findViewById(R.id.cancel_btn_ll_dialog);
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mes = (TextView) mView.findViewById(R.id.middle_et_ll_dialog_mes);
        super.setContentView(mView);
    }

    /*设置顶部文字*/
    public void setTitle(String tit) {
        title.setText(tit);
    }


    //中间提示信息
    public void setMiddleMessage(String text) {
        mes.setText(text);
    }

    /*设置左侧按钮文字*/
    public void setLeftButtonVal(String val) {
        ensure_btn.setText(val);
    }

    /*设置右侧按钮文字*/
    public void setRightButtonVal(String val) {
        cancel_btn.setText(val);
    }

    /**
     * 取消键监听器
     *
     * @param listener
     */
    public void setOnPositiveListener(View.OnClickListener listener) {
        cancel_btn.setVisibility(View.VISIBLE);
        cancel_btn.setOnClickListener(listener);
    }

    /**
     * 确定键监听器
     *
     * @param listener
     */
    public void setOnNegativeListener(View.OnClickListener listener) {
        ensure_btn.setOnClickListener(listener);
        //dismiss();
    }
}
