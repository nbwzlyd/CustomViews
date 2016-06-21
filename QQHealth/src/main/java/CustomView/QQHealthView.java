package CustomView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import customView.TicketView;
import utils.DensityUtils;
import view.lyd.com.qqhealth.R;

/**
 * Created by lyd10892 on 2016/6/14.
 */
public class QQHealthView extends View {

    private Context mContext = null;
    private int mBlueColor;
    private int mGrayColor;

    private int mCenterX;
    private int mCenterY;
    private int mRadius;

    private Paint mTextSmallPaint = null;
    private Paint mTextLargePaint = null;
    private Paint mArcPaint = null;
    private Paint mArcGrayPaint = null;

    public QQHealthView(Context context) {
        this(context, null);
    }

    public QQHealthView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QQHealthView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initColor();
        initPaint();
    }

    private void initColor() {
        mBlueColor = mContext.getResources().getColor(R.color.main_blue_light);
        mGrayColor = mContext.getResources().getColor(R.color.main_gray);
    }

    private void initPaint() {
        //步数的画笔
        mTextLargePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextLargePaint.setColor(mBlueColor);
        mTextLargePaint.setTextSize(DensityUtils.dp2px(mContext, 18));
        mTextLargePaint.setTextAlign(Paint.Align.CENTER);
        mTextLargePaint.setStrokeWidth(6);

        //小字体画笔
        mTextSmallPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextSmallPaint.setColor(mGrayColor);
        mTextSmallPaint.setTextSize(DensityUtils.dp2px(mContext, 12));
        mTextSmallPaint.setTextAlign(Paint.Align.CENTER);
        mTextSmallPaint.setStrokeWidth(3);

        //灰色圆弧画笔
        mArcGrayPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mArcGrayPaint.setColor(mGrayColor);
        mArcGrayPaint.setAntiAlias(true);
        mArcGrayPaint.setDither(true);
        mArcGrayPaint.setStrokeWidth(DensityUtils.dp2px(mContext, 18));

        //蓝色圆弧画笔
        mArcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mArcPaint.setColor(mBlueColor);
        mArcPaint.setDither(true);//防止抖动
        mArcPaint.setAntiAlias(true);//抗锯齿
        mArcPaint.setStrokeWidth(DensityUtils.dp2px(mContext, 18));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        int minSpec = Math.min(width, height);

        mCenterX = getWidth() / 2;
        mCenterY = getHeight() / 2;
        mRadius = mCenterX - DensityUtils.dp2px(mContext, 18) / 2;

        setMeasuredDimension(minSpec, minSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawLargeText(canvas);
        drawSmallText(canvas);
        drawGrayArc(canvas);
        drawBlueArc(canvas);
    }

    private void drawBlueArc(Canvas canvas) {


    }

    private void drawGrayArc(Canvas canvas) {
        RectF rectF = new RectF();
        rectF.left = 12;
        rectF.bottom = 30;
        rectF.top = 30;
        rectF.right = 12;
        canvas.drawArc(rectF,0,180,false,mArcPaint);
    }

    private void drawSmallText(Canvas canvas) {
    }

    private void drawLargeText(Canvas canvas) {
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }
}
