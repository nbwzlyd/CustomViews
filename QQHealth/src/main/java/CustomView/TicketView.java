package CustomView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by lyd10892 on 2016/6/13.
 */
public class TicketView extends RelativeLayout {
    private Paint mPaint = null;

    private int mRadius = 8;
    private int mGap = 10;
    private int mCircleNum = 0;
    private float mRemain = 0;//最终剩余

    public TicketView(Context context) {
        this(context, null);
    }

    public TicketView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TicketView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();

    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.WHITE);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mRemain == 0) {
            mRemain = (w - mGap) % (2 * mRadius + mGap);
        }
        mCircleNum = (w - mGap) / (2 * mRadius + mGap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCircles(canvas);
    }

    private void drawCircles(Canvas canvas) {
        for (int i = 0; i < mCircleNum; i++) {
            float cx = ((mGap + 2 * mRadius) * i) + mGap + mRadius + mRemain / 2;
            canvas.drawCircle(cx, 0, mRadius, mPaint);
            canvas.drawCircle(cx, getHeight(), mRadius, mPaint);

        }
        Log.i("mRemain", "" + mRemain);
    }
}
