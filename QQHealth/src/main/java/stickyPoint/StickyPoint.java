package stickyPoint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by lyd10892 on 2016/7/28.
 */

public class StickyPoint extends View {

    private static final int MAX_PULL = 15;//最大的下拉距离,超过该距离则直接


    private Paint mPointPaint;
    private int mPointColor;


    public StickyPoint(Context context) {
        this(context, null);
    }

    public StickyPoint(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StickyPoint(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
