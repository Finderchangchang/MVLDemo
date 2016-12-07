package net.tsz.afinal.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.tsz.afinal.R;

/**
 * Created by Administrator on 2016/5/23.
 */
public class ImageEditText extends LinearLayout {
    private String mLeftLab;//左侧文字内容
    private String mCenterText;//中间EditText内容
    private EditText mEditText;//中间EditText控件
    private ImageView mRightDelImg;
    private LinearLayout mEditLL;
    private ImageView mLeftImg;//左侧图片
    private boolean mHaveDel;//有删除按钮
    private TextView mLeftText;
    private boolean mHavePwd = false;
    private int mEditBg_int;
    private int mLeftImg_int;
    private String mHintText;
    private LinearLayout main_ll;
    private boolean isNum = false;

    public ImageEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ImageEditText, defStyleAttr, 0);
        mLeftLab = a.getString(R.styleable.ImageEditText_ie_left_text);
        mCenterText = a.getString(R.styleable.ImageEditText_ie_text);
        mHaveDel = a.getBoolean(R.styleable.ImageEditText_ie_have_del, true);
        mHavePwd = a.getBoolean(R.styleable.ImageEditText_ie_have_pwd, false);
        mEditBg_int = a.getResourceId(R.styleable.ImageEditText_ie_et_bg, 0);
        mLeftImg_int = a.getResourceId(R.styleable.ImageEditText_ie_left_img, 0);//文本框左侧显示的图片
        mHintText = a.getString(R.styleable.ImageEditText_ie_hint_text);
//        isNum = a.getBoolean(R.styleable.ImageEditText_num, false);
        a.recycle();
        init(context);
    }

    /**
     * 加载组件
     *
     * @param context
     */
    /*--*/
    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_image_edittext, this, true);//自定义控件绑定
        initViews();
        initEvents();
        if (mHavePwd) {
            mEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
    }

    /*-自定义方法-*/
    public void clearEditText() {
        mEditText.setText("");
    }

    public void setCenterTextRedColor() {
        mEditText.setTextColor(Color.RED);
    }

    public void setCenterTextRedColor2() {
        Resources resource = (Resources) getResources();
        ColorStateList csl = (ColorStateList) resource.getColorStateList(R.color.title_lab_color);
        mEditText.setTextColor(csl);
    }

    public void setFocus() {
        mEditText.setFocusable(true);
        mEditText.setFocusableInTouchMode(true);
        mEditText.requestFocus();
    }

    public void setFocusFasle() {
        mEditText.setFocusable(false);
    }

    public String getText() {
        return mEditText.getText().toString().trim();
    }

    public void setText(String val) {
        mEditText.setText(val);
        mEditText.setSelection(val.length());
    }

    public String getmLeftLab() {
        return mLeftLab;
    }

    public void setmLeftLab(String mLeftLab) {
        this.mLeftLab = mLeftLab;
    }

    private void initEvents() {
        if (mLeftLab != "" || mLeftLab != null) {
            mLeftText.setVisibility(VISIBLE);
            mLeftText.setText(mLeftLab);//左侧文字头设置
        }

        //设置EditText里面显示的内容
        if (mCenterText != null) {
            mEditText.setText(mCenterText);
            mEditText.setSelection(mCenterText.length());
        }
        if (mHintText != "" || mHintText != null) {
            mEditText.setHint(mHintText);
        }
        //设置EditTextll的背景
        if (mEditBg_int != 0) {
            mEditLL.setBackgroundResource(mEditBg_int);
        }
        //设置左侧显示的图片
        if (mLeftImg_int != 0) {
            mLeftImg.setVisibility(VISIBLE);
            mLeftImg.setImageResource(mLeftImg_int);
        }
        //设置删除图片显示隐藏
        if (!mEditText.getText().toString().trim().equals("")) {
            setDelVisible(true);
        } else {
            setDelVisible(false);
        }
        //设置组件中文字内容改变触发事件
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if (mEditText.getText().toString().length() > 0) {
                    setDelVisible(true);
                } else {
                    setDelVisible(false);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });
        //设置点击删除触发事件
        mRightDelImg.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mEditText.setText("");
                return true;
            }
        });
        if (isNum) {
            mEditText.setInputType(InputType.TYPE_CLASS_PHONE);
        } else {
            mEditText.setInputType(InputType.TYPE_CLASS_TEXT);
        }
    }

    /**
     * 设置是否显示删除键
     *
     * @param result true:显示~ false:隐藏
     */
    private void setDelVisible(boolean result) {
        if (mHaveDel && result) {
            mRightDelImg.setVisibility(View.VISIBLE);
        } else {
            mRightDelImg.setVisibility(View.GONE);
        }
    }

    private void initViews() {
        mLeftText = (TextView) this.findViewById(R.id.img_edit_left_lab);
        mEditText = (EditText) this.findViewById(R.id.img_edit_center_edit);
        mRightDelImg = (ImageView) this.findViewById(R.id.img_edit_right_del_img);
        mEditLL = (LinearLayout) this.findViewById(R.id.img_edit_ll);
        mLeftImg = (ImageView) this.findViewById(R.id.img_edit_left_img);
        main_ll = (LinearLayout) this.findViewById(R.id.img_edit_main_ll);
    }

    public ImageEditText(Context context) {
        this(context, null);
        init(context);
    }
    public void AddChangeMethod(TextWatcher textWatcher) {
        mEditText.addTextChangedListener(textWatcher);
    }
    public ImageEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init(context);
    }
}
