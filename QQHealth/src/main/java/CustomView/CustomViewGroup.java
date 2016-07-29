package CustomView;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import utils.DensityUtils;

/**
 * Created by lyd10892 on 2016/7/1.
 */
public class CustomViewGroup extends ViewGroup {

    /**
     * 不去涉及自定义属性
     *
     * @param context
     */

    private  Context mContext;

    public CustomViewGroup(Context context) {
        this(context, null);
    }

    public CustomViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    //计算父view的宽高
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        //计算出所有子view的宽和高
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int desireWidth1 = 0;
        int desireWidth2 = 0;

        int desireHeight1 = 0;
        int desireHeight2 = 0;

        int childCount = getChildCount();

        MarginLayoutParams params;

//        int measureWidth = 0;
//        int measureHeight = 0;
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            int childWidth;//子view占据的宽度
            int childHeight;//子view占据的高度
            params = (MarginLayoutParams) childView.getLayoutParams();
            childWidth = getPaddingLeft() + getPaddingRight() + childView.getMeasuredWidth() + params.leftMargin + params.rightMargin;
            childHeight = getPaddingBottom() + getPaddingTop() + childView.getMeasuredHeight() + params.topMargin + params.bottomMargin;

            //wrapContent情况下需要计算宽度和高度
            if (i == 0 || i == 1) {
                desireWidth1 += childWidth;
            }

            if (i == 2 || i == 3) {
                desireWidth2 += childWidth;
            }
            if (i == 0 || i == 2) {
                desireHeight1 += childHeight;

            }

            if (i == 1 || i == 3) {
                desireHeight2 += childHeight;
            }


        }
        int desireWidth = Math.max(desireWidth1, desireWidth2);
        int desireHeight = Math.max(desireHeight1, desireHeight2);

        setMeasuredDimension(widthMode == MeasureSpec.EXACTLY ? widthSize : desireWidth, heightMode
                == MeasureSpec.EXACTLY ? heightSize : desireHeight);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i("getWidth","getWidth==="+getWidth()+"getHeight==="+getHeight()+"measureWidth"+getMeasuredWidth()+"measureHeight"+getMeasuredHeight());
        Log.i("iiiii===",DensityUtils.dp2px(mContext,50)+"");
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int left = 0, top = 0, right, bottom;
        MarginLayoutParams params;

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {

            View childView = getChildAt(i);
            params = (MarginLayoutParams) childView.getLayoutParams();
            switch (i) {
                case 0:
                    left = params.leftMargin;
                    top = params.topMargin;
                    break;
                case 1:
                    left = getWidth() - childView.getMeasuredWidth() - params.rightMargin - params.leftMargin;
                    top = params.topMargin;
                    break;
                case 2:
                    left = params.leftMargin;
                    top = getHeight() - childView.getMeasuredHeight() - params.bottomMargin;
                    break;
                case 3:
                    left = getWidth() - childView.getMeasuredWidth() - params.rightMargin - params.leftMargin;
                    top = getHeight() - childView.getMeasuredHeight() - params.bottomMargin;
                    break;
            }
            right = left + childView.getMeasuredWidth();
            bottom = childView.getMeasuredHeight() + top;
            childView.layout(left, top, right, bottom);
        }


    }
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
}
