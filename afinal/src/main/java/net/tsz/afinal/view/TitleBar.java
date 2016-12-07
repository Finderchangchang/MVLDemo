package net.tsz.afinal.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.tsz.afinal.R;

/**
 * Created by Administrator on 2016/11/28.
 */

public class TitleBar extends LinearLayout {
    int str_left_iv;
    int str_center_iv;
    int str_right_iv;
    String center_str;//中间字
    String str_center_tv;
    ImageView left_iv;
    ImageView center_iv;
    ImageView right_iv;
    TextView center_tv;
    LeftClick leftClick;
    RightClick rightClick;

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TitleBar, defStyleAttr, 0);
        str_left_iv = a.getResourceId(R.styleable.TitleBar_left_iv, 0);
        str_center_iv = a.getResourceId(R.styleable.TitleBar_center_iv, 0);
        str_right_iv = a.getResourceId(R.styleable.TitleBar_right_iv, 0);
        str_center_tv = a.getString(R.styleable.TitleBar_center_tv);
        a.recycle();
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.v_title_bar, this);
        left_iv = (ImageView) findViewById(R.id.left_iv);
        center_iv = (ImageView) findViewById(R.id.center_iv);
        right_iv = (ImageView) findViewById(R.id.right_iv);
        center_tv = (TextView) findViewById(R.id.center_tv);
        if (!("").equals(str_center_tv)) {//中间文字显示隐藏
            center_tv.setVisibility(VISIBLE);
            center_tv.setText(str_center_tv);
            center_iv.setVisibility(GONE);
        } else {
            center_tv.setVisibility(GONE);
            center_iv.setVisibility(VISIBLE);
        }
        if (str_left_iv != 0) {
            left_iv.setImageResource(str_left_iv);
        }
        left_iv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (leftClick != null) {
                    leftClick.onClick();
                }
            }
        });
        if (str_right_iv != 0) {
            right_iv.setImageResource(str_right_iv);
            right_iv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (rightClick != null) {
                        rightClick.onClick();
                    }
                }
            });
        }
    }

    public TitleBar(Context context) {
        this(context, null);
        init(context);
    }

    public interface LeftClick {
        void onClick();
    }

    public interface RightClick {
        void onClick();
    }

    public TitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init(context);
    }


    public void setLeftClick(LeftClick leftClick) {
        this.leftClick = leftClick;
    }


    public void setRightClick(RightClick rightClick) {
        this.rightClick = rightClick;
    }

    public String getCenter_str() {
        return center_str;
    }

    public void setCenter_str(String center_str) {
        center_tv.setText(center_str);
    }
}
