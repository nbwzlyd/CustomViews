package CustomView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.lang.annotation.Target;
import java.util.Timer;
import java.util.TimerTask;

import customView.TicketView;
import utils.DensityUtils;
import view.lyd.com.qqhealth.R;

/**
 * Created by lyd10892 on 2016/6/14.
 */
public class QQHealthView extends View {

    private Context mContext = null;
    private int mBlueColor;
    private int mBlueColorLight;
    private int mGrayColor;

    //背景
    private Paint mBgPaint = null;
    private int mBgColorWhite;
    private float mBgRadius;
    private Path mPath = null;
    private int mRadius;
    private Paint mLinePaint = null;

    //虚线
    private Paint mDumPaint = null;
    private Path mLinePath = null;

    //波浪线
    private Paint mWavePaint = null;
    private Path mWavePath = null;


    //柱子
    private Paint mColumPaint = null;
    private Path mColumPath = null;



    //圆弧一类
    private Paint mTextSmallPaint = null;
    private Paint mTextLargePaint = null;
    private Paint mTextRankPaint = null;
    private Paint mArcPaint = null;
    private Paint mArcGrayPaint = null;

    private RectF mArcRect = null;
    private int mSteps = 0;//步数

    private Handler mHandler = null;

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
        initPath();

//        customTimerTask task = new customTimerTask();
//        Timer timer = new Timer();
//        timer.schedule(task, 1000, 1000);
//        mHandler = new Handler(new Handler.Callback() {
//            @Override
//            public boolean handleMessage(Message msg) {
//                if (msg.what == 1) {
//                    invalidate();
//                }
//                return true;
//            }
//        });
    }

    private void initRectF() {
        mArcRect = new RectF();
        mArcRect.left = getWidth() / 2 - mRadius;
        mArcRect.right = mArcRect.left + mRadius * 2;
        mArcRect.top = getHeight() / 2 - mRadius-240;
        mArcRect.bottom = mArcRect.top + mRadius * 2;
    }

    private void initColor() {
        mBlueColor = mContext.getResources().getColor(R.color.main_blue_dark);
        mBlueColorLight = mContext.getResources().getColor(R.color.main_blue_light);
        mGrayColor = mContext.getResources().getColor(R.color.main_gray);
        mBgColorWhite = mContext.getResources().getColor(R.color.main_white);
        mBgRadius = DensityUtils.dp2px(mContext, 10);
    }

    private void initPaint() {
        //步数的画笔
        mTextLargePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextLargePaint.setColor(mBlueColor);
        mTextLargePaint.setTextSize(DensityUtils.sp2px(mContext, 33));
        mTextLargePaint.setTextAlign(Paint.Align.CENTER);
        mTextLargePaint.setStrokeWidth(18);

        //小字体画笔
        mTextSmallPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextSmallPaint.setColor(mGrayColor);
        mTextSmallPaint.setTextSize(DensityUtils.sp2px(mContext, 15));
        mTextSmallPaint.setTextAlign(Paint.Align.CENTER);
        mTextSmallPaint.setStrokeWidth(12);

        //排名
        mTextRankPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextRankPaint.setColor(mBlueColor);
        mTextRankPaint.setTextSize(DensityUtils.sp2px(mContext, 24));
        mTextRankPaint.setTextAlign(Paint.Align.CENTER);
        mTextRankPaint.setStrokeWidth(12);

        //灰色圆弧画笔
        mArcGrayPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mArcGrayPaint.setColor(mBlueColorLight);
        mArcGrayPaint.setAntiAlias(true);
        mArcGrayPaint.setDither(true);
        mArcGrayPaint.setStrokeWidth(DensityUtils.sp2px(mContext, 12));
        mArcGrayPaint.setStyle(Paint.Style.STROKE);
        mArcGrayPaint.setStrokeCap(Paint.Cap.ROUND);
        mArcGrayPaint.setStrokeJoin(Paint.Join.ROUND);

        //蓝色圆弧画笔
        mArcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mArcPaint.setColor(mBlueColor);
        mArcPaint.setDither(true);//防止抖动
        mArcPaint.setAntiAlias(true);//抗锯齿
        mArcPaint.setStrokeWidth(DensityUtils.sp2px(mContext, 12));
        mArcPaint.setStyle(Paint.Style.STROKE);
        mArcPaint.setStrokeCap(Paint.Cap.ROUND);
        mArcPaint.setStrokeJoin(Paint.Join.ROUND);

        //背景图
        mBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBgPaint.setColor(mBgColorWhite);
        mBgPaint.setDither(true);//防止抖动
        mBgPaint.setAntiAlias(true);//抗锯齿
        mBgPaint.setStrokeWidth(DensityUtils.sp2px(mContext, 0.5f));
        mBgPaint.setStyle(Paint.Style.FILL);

        //贝塞尔曲线
        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setColor(mBgColorWhite);
        mLinePaint.setDither(true);//防止抖动
        mLinePaint.setAntiAlias(true);//抗锯齿
        mLinePaint.setStrokeWidth(DensityUtils.sp2px(mContext, 6));

        //柱子
        mColumPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mColumPaint.setStrokeCap(Paint.Cap.ROUND);
        mColumPaint.setStrokeJoin(Paint.Join.ROUND);
        mColumPaint.setStrokeWidth(DensityUtils.sp2px(mContext, 12));
        mColumPaint.setColor(mBlueColor);
        mColumPaint.setStyle(Paint.Style.STROKE);


        //虚线

        mDumPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mDumPaint.setStyle(Paint.Style.STROKE);
        mDumPaint.setColor(mGrayColor);
        mDumPaint.setStrokeWidth(2);

        //波浪
        mWavePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mWavePaint.setColor(mBlueColor);
        mWavePaint.setDither(true);//防止抖动
        mWavePaint.setAntiAlias(true);//抗锯齿
        mWavePaint.setStrokeWidth(2);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        measureWidth(widthMeasureSpec);


        if (widthMode == MeasureSpec.AT_MOST){

        }
        if (heightMode == MeasureSpec.UNSPECIFIED){

        }

//        mCenterX = getWidth() / 2;
//        mCenterY = getHeight() / 2;
        setMeasuredDimension(width, height);
        mRadius = getWidth() / 4;
        initRectF();
    }

    private void measureWidth(int widthMeasureSpec) {
    }

    private void measureHeight(int heightMeasureSpec){

    }

    private void initPath() {
        mPath = new Path();
        mLinePath = new Path();
        mWavePath = new Path();
        mColumPath = new Path();
//        float cx = getWidth() / 2 - mRadius - 12;
//        float cy = getHeight() / 2 + mRadius + 12;
//        mPath.moveTo(cx, cy);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBg(canvas);
        drawLargeText(canvas);
        drawSmallText(canvas);
        drawGrayArc(canvas);
        drawBlueArc(canvas);
        drawDummyLine(canvas);
        drawWaves(canvas);
        drawColumn(canvas);
    }
    private void drawBg(Canvas canvas) {
        mPath.moveTo(0, getHeight());
        mPath.lineTo(0, mBgRadius);
        mPath.quadTo(0, 0, mBgRadius, 0);
//        mPath.moveTo(mBgRadius, 0);//多余
        mPath.lineTo(getWidth() - mBgRadius, 0);
        mPath.quadTo(getWidth(), 0, getWidth(), mBgRadius);
//        mPath.moveTo(getWidth(), mBgRadius);//多余
        mPath.lineTo(getWidth(), getHeight());
//        mPath.moveTo(getWidth(), getHeight());//多余
        mPath.lineTo(0, getHeight());
        mPath.close();
        canvas.drawPath(mPath, mBgPaint);
    }

    private void drawDummyLine(Canvas canvas) {
        PathEffect effect = new DashPathEffect(new float[]{2,2},4);
        mLinePath.moveTo(0,getHeight()/2+mRadius+120);
        mLinePath.lineTo(getWidth(),getHeight()/2+mRadius+120);
//        mLinePath.
        mDumPaint.setPathEffect(effect);
//        mLinePath.quadTo(getWidth() / 2, getHeight() / 2, getWidth(), getHeight());
        canvas.drawPath(mLinePath, mDumPaint);
    }

    private void drawBlueArc(Canvas canvas) {
        canvas.drawArc(mArcRect, 120, 120 + (mSteps * 180 / 6000), false, mArcPaint);
        Log.i("msteps_blue", mSteps * 300 / 6000 + "");
    }

    private void drawGrayArc(Canvas canvas) {
        canvas.drawArc(mArcRect, 120, 300, false, mArcGrayPaint);
        Log.i("msteps_gray", mSteps + "");
    }

    private void drawSmallText(Canvas canvas) {
        canvas.drawText("今天一共走了", getWidth() / 2,mArcRect.top+mRadius/2, mTextSmallPaint);
        canvas.drawText("步", getWidth() / 2, mArcRect.bottom-mRadius/2, mTextSmallPaint);
        canvas.drawText("第", getWidth() / 2 - mRadius / 4, mArcRect.bottom, mTextSmallPaint);
        canvas.drawText("名", getWidth() / 2 + mRadius / 4, mArcRect.bottom, mTextSmallPaint);
        canvas.drawText("最近7天", getWidth() / 8, getHeight() * 9 / 10, mTextSmallPaint);
        canvas.drawText("平均每天200步", getWidth() * 7 / 8, getHeight() * 9 / 10, mTextSmallPaint);
    }

    private void drawLargeText(Canvas canvas) {
        String text = mSteps + "";
        Rect rect = new Rect();
        mTextLargePaint.getTextBounds(text, 0, text.length(), rect);
        canvas.drawText(text, getWidth() / 2, mArcRect.top+mRadius+rect.height() / 2, mTextLargePaint);
        canvas.drawText("5", getWidth() / 2, mArcRect.bottom, mTextRankPaint);

    }

    private void drawWaves(Canvas canvas) {
        int waveRadius = getWidth()/4;

        mWavePath.moveTo(0,getHeight());
        mWavePath.lineTo(0,getHeight()-30);

//        mWavePath.moveTo(0,getHeight()-30);
//        mWavePath.lineTo(getWidth(),getHeight()-30);
        mWavePath.cubicTo(waveRadius,getHeight()-30-waveRadius,waveRadius*3,getHeight()-30,getWidth(),getHeight()-30-20);
        mWavePath.lineTo(getWidth(),getHeight());
        mWavePath.lineTo(0,getHeight());
        canvas.drawPath(mWavePath,mWavePaint);

    }



    private void drawColumn(Canvas canvas){


        int paintWidth = DensityUtils.sp2px(mContext, 12);
        int width = (getWidth()-paintWidth*7)/8;

        for (int i = 0;i<7;i++){
            mColumPath.moveTo(width+(paintWidth+width)*i,getHeight()/2+mRadius+200);
            mColumPath.lineTo(width+(paintWidth+width)*i ,getHeight()/2+mRadius+150);
            canvas.drawPath(mColumPath,mColumPaint);

        }


        mColumPath.moveTo(width+paintWidth+width+paintWidth+width,getHeight()/2+mRadius+200);
        mColumPath.lineTo(width+paintWidth+width+paintWidth+width ,getHeight()/2+mRadius+150);
//        mColumPath.moveTo(90,getHeight()/2+mRadius+200);
//        mColumPath.lineTo(90 ,getHeight()/2+mRadius+150);
//        mColumPath.moveTo(120,getHeight()/2+mRadius+200);
//        mColumPath.lineTo(120 ,getHeight()/2+mRadius+150);

    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    class customTimerTask extends TimerTask {

        @Override
        public void run() {
            mSteps += 100;
            Message message = new Message();
            message.what = 1;
            mHandler.sendMessage(message);
        }
    }

}
