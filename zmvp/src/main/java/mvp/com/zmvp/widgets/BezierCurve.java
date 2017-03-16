package mvp.com.zmvp.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.OvershootInterpolator;

/**
 * Created by zz on 2017/3/10.
 * 自定义贝塞尔曲线动画
 */

public class BezierCurve extends View {

    /**
     * 线的粗度
     */
    private final float STROKE_WIDTH = 50;

    /**
     * 橡皮线左右偏移量
     */
    private final int MARGIN_LEFT_RIGHT = 100;

    /**
     * 画笔
     */
    private Paint mPaint;

    /**
     * 起点
     */
    private Point mStartPoint = new Point();

    /**
     * 终点
     */
    private Point mEndPoint = new Point();

    /**
     * 平移点
     */
    private Point mLastPoint = new Point();

    /**
     * 路径
     */
    private Path mPath = new Path();

    private Rect mRect = new Rect();

//    private AnticipateOvershootInterpolator  mInterpolator = new AnticipateOvershootInterpolator(2.0F);
//    private AnticipateInterpolator mInterpolator = new AnticipateInterpolator(2.0F);
    private OvershootInterpolator mInterpolator = new OvershootInterpolator(3.0F);

    private boolean isTrigger;
    private boolean isAnim;
    private float mToXDelta;
    private float mToYDelta;
    private long mStartTime;
    private final long mDuration = 500;  // 动画持续时间

    public BezierCurve(Context context) {
        super(context);
        initView(context);
    }

    public BezierCurve(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public BezierCurve(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(STROKE_WIDTH);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeJoin(Paint.Join.ROUND);


        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeOnGlobalLayoutListener(this);

                int width = getWidth();
                int height = getHeight();

                mStartPoint.x = MARGIN_LEFT_RIGHT;
                mStartPoint.y = height / 2;

                mEndPoint.x = width - MARGIN_LEFT_RIGHT;
                mEndPoint.y = height / 2;

                mRect.left = MARGIN_LEFT_RIGHT;
                mRect.top = (int) ((height - STROKE_WIDTH) / 2);
                mRect.right = width - mRect.left;
                mRect.bottom = (int) (mRect.top + STROKE_WIDTH);
            }
        });
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int ex = (int) event.getX();
        int ey = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (contains(ex, ey)) {
                    isTrigger = true;
                    mLastPoint.x = ex;
                    mLastPoint.y = ey;
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (isTrigger) {
                    mLastPoint.x = ex;
                    mLastPoint.y = ey;
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                isTrigger = false;
                stopAnim();
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                if (isTrigger) {
                    isTrigger = false;
                    if (!contains(ex, ey)) {
                        mLastPoint.x = ex;
                        mLastPoint.y = ey;
                        startAnim();
                    } else {
                        stopAnim();
                    }
                }
                invalidate();
                break;
        }

        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPath.reset();
        mPath.moveTo(mStartPoint.x, mStartPoint.y);

        if (isAnim) {
            if (mStartTime == -1) {
                mStartTime = SystemClock.uptimeMillis();
            }
            long currentTime = SystemClock.uptimeMillis();
            final long duration = mDuration;
            float normalizedTime;
            if (duration != 0) {
                normalizedTime = ((float) (currentTime - mStartTime)) / (float) duration;
            } else {
                normalizedTime = currentTime < mStartTime ? 0.0f : 1.0f;
            }
            final boolean expired = normalizedTime >= 1.0f;
            normalizedTime = Math.max(Math.min(normalizedTime, 1.0f), 0.0f);

            final float interpolatedTime = mInterpolator.getInterpolation(normalizedTime);

            Log.i("zz", "测试[ normalizedTime = " + normalizedTime + " , expired = " + expired + " ,  interpolatedTime = " + interpolatedTime);

            float dx = mLastPoint.x;
            float dy = mLastPoint.y;
            if (dx != mToXDelta) {
                dx += ((mToXDelta - dx) * interpolatedTime);
            }
            if (dy != mToYDelta) {
                dy += ((mToYDelta - dy) * interpolatedTime);
            }
            mPath.quadTo(dx, dy, mEndPoint.x, mEndPoint.y);
            canvas.drawPath(mPath, mPaint);

            if (expired) {
                stopAnim();
            } else {
                invalidate();
            }
        } else if (isTrigger) {
            mPath.quadTo(mLastPoint.x, mLastPoint.y, mEndPoint.x, mEndPoint.y);
            canvas.drawPath(mPath, mPaint);
        } else {
            mPath.lineTo(mEndPoint.x, mEndPoint.y);
            canvas.drawPath(mPath, mPaint);
        }
    }

    /**
     * 线条范围
     *
     * @param x
     * @param y
     * @return
     */
    private boolean contains(int x, int y) {
        return mRect.contains(x, y);
    }


    private void startAnim() {
        isAnim = true;
        mStartTime = -1;
        mToXDelta = getWidth() / 2;
        mToYDelta = getHeight() / 2;
//        mToXDelta = getWidth() - mLastPoint.x;
//        mToYDelta = getHeight() - mLastPoint.y;
    }

    private void stopAnim() {
        isAnim = false;
        mStartTime = -1;
        mLastPoint.x = -1;
        mLastPoint.y = -1;
    }


}
