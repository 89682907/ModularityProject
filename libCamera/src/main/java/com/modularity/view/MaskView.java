package com.modularity.view;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

import com.modularity.util.DisplayUtil;

/**
 * 人脸扫描屏幕中间正方形框
 */
public class MaskView extends AppCompatImageView {
    private Paint   mLinePaint;
    private Paint   mAreaPaint;
    private Rect    mCenterRect;
    private Context mContext;
//	/**
//	 * 扫描框中的中间线的宽度
//	 */
//	private static final int MIDDLE_LINE_WIDTH = 6;
//	/**
//	 * 扫描框中的中间线的与扫描框左右的间隙
//	 */
//	private static final int MIDDLE_LINE_PADDING = 5;
//
//	/**
//	 * 中间那条线每次刷新移动的距离
//	 */
//	private static final int SPEEN_DISTANCE = 5;
//	/**
//	 * 中间滑动线的最顶端位置
//	 */
//	private int slideTop;
//
//	boolean isFirst;
//
//	/**
//	 * 中间滑动线的最底端位置
//	 */
//	private int slideBottom;
//	private static final long ANIMATION_DELAY = 10L;
//	/**
//	 * 画笔对象的引用
//	 */
//	private Paint paint;


    public MaskView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
        mContext = context;
        Point p = DisplayUtil.getScreenMetrics(mContext);
        widthScreen = p.x;
        heightScreen = p.y;
    }

    private void initPaint() {
        //绘制中间透明区域矩形边界的Paint
        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setColor(Color.BLUE);
        mLinePaint.setStyle(Style.STROKE);
        mLinePaint.setStrokeWidth(5f);
        mLinePaint.setAlpha(30);

        //绘制四周阴影区域
        mAreaPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mAreaPaint.setColor(Color.GRAY);
        mAreaPaint.setStyle(Style.FILL);
        mAreaPaint.setAlpha(100);

//		paint = new Paint();
//		paint.setColor(Color.WHITE);
//		paint.setAlpha(50);
//		paint.setStyle(Style.STROKE);
//		paint.setStrokeWidth(20f);


    }

    public void setCenterRect(Rect r) {
        this.mCenterRect = r;
        postInvalidate();
    }

    public void clearCenterRect(Rect r) {
        this.mCenterRect = null;
    }

    int widthScreen, heightScreen;

    @Override
    protected void onDraw(Canvas canvas) {
        if (mCenterRect == null)
            return;
        //绘制四周阴影区域
        canvas.drawRect(0, 0, widthScreen, mCenterRect.top, mAreaPaint);
        canvas.drawRect(0, mCenterRect.bottom + 1, widthScreen, heightScreen, mAreaPaint);
        canvas.drawRect(0, mCenterRect.top, mCenterRect.left - 1, mCenterRect.bottom + 1, mAreaPaint);
        canvas.drawRect(mCenterRect.right + 1, mCenterRect.top, widthScreen, mCenterRect.bottom + 1, mAreaPaint);

        //绘制目标透明区域
        canvas.drawRect(mCenterRect, mLinePaint);


//		//初始化中间线滑动的最上边和最下边
//		if(!isFirst){
//			isFirst = true;
//			slideTop = mCenterRect.top;
//			slideBottom = mCenterRect.bottom;
//		}
//
//		//绘制中间的线,每次刷新界面，中间的线往下移动SPEEN_DISTANCE
//		slideTop += SPEEN_DISTANCE;
//		if(slideTop >= mCenterRect.bottom){
//			slideTop = mCenterRect.top;
//		}
//		canvas.drawRect(mCenterRect.left + MIDDLE_LINE_PADDING, slideTop - MIDDLE_LINE_WIDTH/2, mCenterRect.right - MIDDLE_LINE_PADDING,slideTop + MIDDLE_LINE_WIDTH/2, paint);
////只刷新扫描框的内容，其他地方不刷新
//		postInvalidateDelayed(ANIMATION_DELAY, mCenterRect.left, mCenterRect.top,
//				mCenterRect.right, mCenterRect.bottom);
        super.onDraw(canvas);
    }


}
