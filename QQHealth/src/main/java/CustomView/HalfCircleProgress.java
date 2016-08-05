package CustomView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import utils.DensityUtils;

/**
 * Created by lyd10892 on 2016/8/5.
 */

public class HalfCircleProgress extends View {

    private static final int MAX_VALUE = 100;

    private Context mContext;

    private Paint mBigCirclePaint;
    private Paint mBigTextPaint;
    private Paint mlitleCirlclePaint;
    private Paint mlitleTextPaint;


    private Path mPath;
    private int mBigRadius = 200;
    private int mLittleRadius = 20;


    public HalfCircleProgress(Context context) {
        this(context, null);
    }

    public HalfCircleProgress(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HalfCircleProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        mBigCirclePaint = new Paint();
        mBigCirclePaint.setColor(mContext.getResources().getColor(android.R.color.holo_blue_bright));
        mBigCirclePaint.setStrokeWidth(DensityUtils.dp2px(mContext,10));
        mBigTextPaint = new Paint();
        mBigTextPaint.setColor(mContext.getResources().getColor(android.R.color.holo_blue_bright));
        mBigTextPaint.setTextSize(20);

        mlitleCirlclePaint = new Paint();
        mlitleCirlclePaint.setColor(mContext.getResources().getColor(android.R.color.holo_blue_bright));
        mlitleCirlclePaint.setStrokeWidth(DensityUtils.dp2px(mContext,5));
        mlitleTextPaint = new Paint();
        mlitleTextPaint.setColor(mContext.getResources().getColor(android.R.color.white));
        mlitleTextPaint.setTextSize(12);

        setPaintStyles(mBigCirclePaint);
        setPaintStyles(mBigTextPaint);
        setPaintStyles(mlitleCirlclePaint);
        setPaintStyles(mlitleTextPaint);

        mPath = new Path();

    }

    private void setPaintStyles(Paint paint) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBigCircle(canvas);
    }

    private void drawBigCircle(Canvas canvas) {

        RectF rectf = new RectF();
        rectf.left = getWidth() / 2 - mBigRadius;
        rectf.right = rectf.left + 2 * mBigRadius;
        rectf.top = getHeight() / 2 - mBigRadius;
        rectf.bottom = rectf.top + 2 * mBigRadius;
        canvas.drawArc(rectf, 0, 360, false, mBigCirclePaint);

    }

    private void drawBigText(Canvas canvas) {

    }

    private void drawLittleCircle(Canvas canvas) {

        RectF rectf = new RectF();
        rectf.left = getWidth() / 2 - mBigRadius;
        rectf.right = rectf.left + 2 * mBigRadius;
        rectf.top = getHeight() / 2 - mBigRadius;
        rectf.bottom = rectf.top + 2 * mBigRadius;
        canvas.drawArc(rectf, 0, 360, false, mBigCirclePaint);

    }

    private void drawLittleText(Canvas canvas) {

    }

}
