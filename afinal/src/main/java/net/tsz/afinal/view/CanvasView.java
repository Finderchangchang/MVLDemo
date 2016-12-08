package net.tsz.afinal.view;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import net.tsz.afinal.R;

/**
 * Created by Finder丶畅畅 on 2016/12/8 23:03
 * QQ群481606175
 */

public class CanvasView extends View {
    public CanvasView(Context context) {
        super(context);
    }

    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        Paint paint=new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setMaskFilter(new BlurMaskFilter(20, BlurMaskFilter.Blur.OUTER));
        paint.setColor(Color.BLACK);
        canvas.drawRoundRect(new RectF(20, 0, 500, 100), 5, 5, paint);
        Paint p = new Paint();
        p.setColor(Color.RED);// 设置红色
        Path path = new Path();
        path.moveTo(0, 45);
        path.lineTo(20, 30);
        path.lineTo(20, 60);
        path.close();
        canvas.drawPath(path, p);

        p.setColor(Color.RED);
        canvas.drawRoundRect(new RectF(20, 0, 500, 100), 5, 5, p);

        super.dispatchDraw(canvas);

    }
}
