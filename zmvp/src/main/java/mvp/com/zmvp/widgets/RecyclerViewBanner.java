package mvp.com.zmvp.widgets;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import mvp.com.zmvp.R;

/**
 * Created by zz on 2016/11/17.
 */

public class RecyclerViewBanner extends FrameLayout {
    private RecyclerView mRecyclerView;
    private LinearLayout mLinearLayout;
    private GradientDrawable defaultDrawable, selectedDrawable;

    private ReyclerAdapter mAdapter;
    private ArrayList<BannerEntity> mDatas = new ArrayList<>();
    private boolean isPlaying;

    private int mCurrentIndex;
    /**
     * dot size
     */
    private int size;
    private float startX;
    private float startY;


    public RecyclerViewBanner(Context context) {
        this(context, null);
    }

    public RecyclerViewBanner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecyclerViewBanner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        size = (int) (6 * context.getResources().getDisplayMetrics().density + 0.5f);

        defaultDrawable = new GradientDrawable();
        defaultDrawable.setSize(size, size);
        defaultDrawable.setCornerRadius(size);
        defaultDrawable.setColor(0xffffffff);

        selectedDrawable = new GradientDrawable();
        selectedDrawable.setSize(size, size);
        selectedDrawable.setCornerRadius(size);
        selectedDrawable.setColor(0xff0094ff);


        mRecyclerView = new RecyclerView(context);

        LayoutParams vpLayoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        mLinearLayout = new LinearLayout(context);
        mLinearLayout.setOrientation(LinearLayout.HORIZONTAL);

        LayoutParams linearLayoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        mLinearLayout.setGravity(Gravity.CENTER);

        mLinearLayout.setPadding(size * 2, size * 2, size * 2, size * 2);

        linearLayoutParams.gravity = Gravity.BOTTOM;

        addView(mRecyclerView, vpLayoutParams);
        addView(mLinearLayout, linearLayoutParams);


        new LinearSnapHelper().attachToRecyclerView(mRecyclerView);
//        new PagerSnapHelper().attachToRecyclerView(mRecyclerView);


        mRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int first = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                int last = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
                if (mCurrentIndex != (first + last) / 2) {
                    mCurrentIndex = (first + last) / 2;
                    updatePoint();
                }

                if (RecyclerView.SCROLL_STATE_IDLE == newState && !isPlaying) {
                    setPlaying(true);
                }
            }
        });

        mAdapter = new ReyclerAdapter();
        mRecyclerView.setAdapter(mAdapter);

    }


    private Handler handler = new Handler();
    private Runnable playTask = new Runnable() {
        @Override

        public void run() {
            mRecyclerView.smoothScrollToPosition(++mCurrentIndex);
            updatePoint();
            handler.postDelayed(this, 3000);
        }
    };


//    public void setOnPagerClickListener(OnPagerClickListener onPagerClickListener) {
//        this.onPagerClickListener = onPagerClickListener;
//    }


    public synchronized void setPlaying(boolean playing) {
        handler.removeCallbacksAndMessages(null);
        isPlaying = playing;
        if (mAdapter != null && mAdapter.getItemCount() > 2) {
            handler.postDelayed(playTask, 3000);
        }

//        if (!isPlaying && playing && mAdapter != null && mAdapter.getItemCount() > 2) {
//            handler.removeCallbacksAndMessages(null);
//            handler.postDelayed(playTask, 3000);
//            isPlaying = true;
//        } else if (isPlaying && !playing) {
//            handler.removeCallbacksAndMessages(null);
//            isPlaying = false;
//        }
    }


    public int setDatas(ArrayList<BannerEntity> datas) {
        setPlaying(false);
        mDatas.clear();
        mLinearLayout.removeAllViews();
        if (null != datas) {
            mDatas.addAll(datas);
        }
        if (mDatas.size() > 1) {
            mCurrentIndex = mDatas.size() * 10000;
            mAdapter.notifyDataSetChanged();
            mRecyclerView.scrollToPosition(mCurrentIndex);

            for (int i = 0; i < this.mDatas.size(); i++) {
                ImageView img = new ImageView(getContext());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                lp.leftMargin = size / 2;
                lp.rightMargin = size / 2;
                img.setImageDrawable(i == 0 ? selectedDrawable : defaultDrawable);
                mLinearLayout.addView(img, lp);
            }
            setPlaying(true);
        } else {
            mCurrentIndex = 0;
            mAdapter.notifyDataSetChanged();
        }
        return mDatas.size();
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = ev.getX();
                startY = ev.getY();
                getParent().requestDisallowInterceptTouchEvent(true);
                setPlaying(false);
                break;
            case MotionEvent.ACTION_MOVE:
                float moveX = ev.getX();
                float moveY = ev.getY();
                float disX = moveX - startX;
                float disY = moveY - startY;
                getParent().requestDisallowInterceptTouchEvent(2 * Math.abs(disX) > Math.abs(disY));
                setPlaying(false);
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
//                setPlaying(true);
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    private void updatePoint() {
        if (mLinearLayout != null) {
            for (int i = 0; i < mLinearLayout.getChildCount(); i++) {
                ((ImageView) mLinearLayout.getChildAt(i)).setImageDrawable(i == mCurrentIndex % mDatas.size() ? selectedDrawable : defaultDrawable);
            }
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        setPlaying(true);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        setPlaying(false);
    }


    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        setPlaying(visibility == View.VISIBLE);
    }

    private class ReyclerAdapter extends RecyclerView.Adapter {
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ImageView img = new ImageView(parent.getContext());
            RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            img.setScaleType(ImageView.ScaleType.CENTER_CROP);
            img.setLayoutParams(params);
            img.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            return new RecyclerView.ViewHolder(img) {

            };
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//            ImageView img = (ImageView) holder.itemView.findViewById(R.id.icon);
//            Glide.with(img.getContext()).load(datas.get(position % datas.size()).getUrl()).placeholder(R.mipmap.ic_launcher).into(img);
            Picasso.with(getContext()).load(mDatas.get(position % mDatas.size()).getUrl()).placeholder(R.mipmap.ic_launcher).into((ImageView) holder.itemView);
        }

        @Override
        public int getItemCount() {
            return mDatas.size() < 2 ? mDatas.size() : Integer.MAX_VALUE;
        }

    }


    private class PagerSnapHelper extends LinearSnapHelper {
        @Override
        public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int velocityX, int velocityY) {
            int targetPos = super.findTargetSnapPosition(layoutManager, velocityX, velocityY);
            final View currentView = findSnapView(layoutManager);
            if (targetPos != RecyclerView.NO_POSITION && currentView != null) {
                int currentPostion = layoutManager.getPosition(currentView);
                int first = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
                int last = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                currentPostion = targetPos < currentPostion ? last : (targetPos > currentPostion ? first : currentPostion);
                targetPos = targetPos < currentPostion ? currentPostion - 1 : (targetPos > currentPostion ? currentPostion + 1 : currentPostion);
            }
            return targetPos;

        }
    }


    public interface BannerEntity {
        String getUrl();
    }
}
