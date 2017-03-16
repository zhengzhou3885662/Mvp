package mvp.com.zmvp.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;

import java.util.ArrayList;
import java.util.Random;


/**
 * 流星雨效果逆向向上
 */

public class MeteorShowerView extends View {
    /**
     * 动画最少持续时间
     */
    private final long DURATION_MIN = 300;
    /**
     * 动画最大持续时间
     */
    private final long DURATION_MAX = 1000;

    /**
     * 延时动画开始时间
     */
    private final long START_OFFSET = 0;
    /**
     * 半径最大值
     */
    private final float RADIUS_MAX = 4f;
    /**
     * 半径最小值
     */
    private final float RADIUS_MIN = .5f;
    /**
     * 水平每个分块长度
     */
    private final int HORIZONTAL_BLOCK_LENGTH = 16;


    /**
     * 星星画笔
     */
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    /**
     * 透明层画笔
     */
    private Paint mPaintLayer = new Paint(Paint.ANTI_ALIAS_FLAG);

    private Rect mRectLayer = new Rect();

    /**
     *
     */
    private ArrayList<Star> mStarts = new ArrayList<>();
    /**
     * 激活动画
     */
    private boolean mActivate;


    private Interpolator mInterpolator = new AccelerateInterpolator();


    private Random mRandom = new Random();


    public MeteorShowerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public MeteorShowerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public MeteorShowerView(Context context) {
        super(context);
        initView(context);
    }

    private void initView(Context context) {
        mPaint.setColor(Color.parseColor("#EAF8F0"));
        mPaintLayer.setColor(Color.parseColor("#0FFFFFFF"));
    }


    private void initStar() {
        mStarts.clear();

        final int width = getWidth();
        final int height = getHeight();

        final int blockLengthH = height / 3;   // Y轴分块大小


        mRectLayer.set(0, blockLengthH, width, height);

        final int maxNum = width / HORIZONTAL_BLOCK_LENGTH;

        for (int i = 0; i < maxNum; i++) {
            Star star = new Star(i);

            star.mRadius = RADIUS_MIN + mRandom.nextInt((int) ((RADIUS_MAX - RADIUS_MIN) * 10) + 1) / 10f;
            star.mFromXDelta = i * HORIZONTAL_BLOCK_LENGTH + mRandom.nextInt(HORIZONTAL_BLOCK_LENGTH + 1);
            star.mFromYDelta = height / 2 + mRandom.nextInt(height / 2);
//            star.mFromYDelta = blockLengthH * 2 + mRandom.nextInt(blockLengthH);
//            star.mFromYDelta = blockLengthH + mRandom.nextInt(height - blockLengthH);
            star.mToXDelta = star.mFromXDelta;
            star.mToYDelta = mRandom.nextInt(blockLengthH);

            star.mDuration = DURATION_MIN + mRandom.nextInt((int) (DURATION_MAX - DURATION_MIN));
            star.mStartOffset = START_OFFSET;

            mStarts.add(star);
        }
    }

    /**
     * 更新Star坐标
     *
     * @param star
     */
    private void updateStarCoord(Star star) {
        final int height = getHeight();
        final int blockLengthH = height / 3;   // Y轴分块大小

        star.mRadius = RADIUS_MIN + mRandom.nextInt((int) ((RADIUS_MAX - RADIUS_MIN) * 10) + 1) / 10f;
        star.mFromXDelta = star.index * HORIZONTAL_BLOCK_LENGTH + mRandom.nextInt(HORIZONTAL_BLOCK_LENGTH + 1);
        star.mFromYDelta = height / 2 + mRandom.nextInt(height / 2);
//        star.mFromYDelta = blockLengthH * 2 + mRandom.nextInt(blockLengthH);
//        star.mFromYDelta = blockLengthH + mRandom.nextInt(height - blockLengthH);
        star.mToXDelta = star.mFromXDelta;
        star.mToYDelta = mRandom.nextInt(blockLengthH);

        star.mDuration = DURATION_MIN + mRandom.nextInt((int) (DURATION_MAX - DURATION_MIN));
        star.mStartOffset = START_OFFSET;
    }

    public void start() {
        initStar();
        mActivate = true;
        invalidate();
    }

    public void cancel() {
        mActivate = false;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mActivate && mStarts.size() > 0) {
            int size = mStarts.size();
            for (int i = 0; i < size; i++) {
                mStarts.get(i).onDraw(canvas);
            }
        }
        onDrawLayerHyaline(canvas);
        invalidate();
    }

    private void onDrawLayerHyaline(Canvas canvas) {
        canvas.drawRect(mRectLayer, mPaintLayer);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        cancel();
    }

    /**
     *
     */
    class Star {
        Star(int i) {
            index = i;
        }

        int index;
        float mRadius;
        long mDuration;
        long mStartOffset;  // 延时启动

        float mFromXDelta;
        float mFromYDelta;
        float mToXDelta;
        float mToYDelta;

        long mStartTime = -1;


        void onDraw(Canvas canvas) {
            if (mStartTime == -1) {
                mStartTime = SystemClock.uptimeMillis();
            }
            long currentTime = SystemClock.uptimeMillis();

            final long startOffset = mStartOffset;
            final long duration = mDuration;
            float normalizedTime;
            if (duration != 0) {
                normalizedTime = ((float) (currentTime - (mStartTime + startOffset))) / (float) duration;
            } else {
                normalizedTime = currentTime < mStartTime ? 0.0f : 1.0f;
            }

            final boolean expired = normalizedTime >= 1.0f || isCanceled();

            normalizedTime = Math.max(Math.min(normalizedTime, 1.0f), 0.0f);

            final float interpolatedTime = mInterpolator.getInterpolation(normalizedTime);
            applyTransformation(interpolatedTime, canvas);

            /**
             * 过期更新坐标
             */
            if (expired) {
                mStartTime = -1;
                updateStarCoord(this);
            }
        }


        protected void applyTransformation(float interpolatedTime, Canvas canvas) {
            float dx = mFromXDelta;
            float dy = mFromYDelta;
            if (mFromXDelta != mToXDelta) {
                dx = mFromXDelta + ((mToXDelta - mFromXDelta) * interpolatedTime);
            }
            if (mFromYDelta != mToYDelta) {
                dy = mFromYDelta + ((mToYDelta - mFromYDelta) * interpolatedTime);
            }

//            if (interpolatedTime <= .1f) {
//                mPaint.setAlpha((int) (255 * interpolatedTime));
//            } else {
                mPaint.setAlpha((int) (255 - 255 * interpolatedTime));
//            }
            canvas.drawCircle(dx, dy, mRadius, mPaint);
        }


        private boolean isCanceled() {
            return mStartTime == Long.MIN_VALUE;
        }

        public void cancel() {
            mStartTime = Long.MIN_VALUE;
        }

    }
}
