package net.tsz.afinal.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 重写ListView解决与Scroll冲突问题。
 * Created by Administrator on 2016/5/19.
 */
public class TotalListView extends ListView {
    public TotalListView(Context context) {
        super(context);
    }

    public TotalListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TotalListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
