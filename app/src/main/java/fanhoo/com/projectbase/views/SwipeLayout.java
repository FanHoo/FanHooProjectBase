package fanhoo.com.projectbase.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import fanhoo.com.projectbase.R;

/**
 * 滑动Layout显示功能按钮,可用于List滑动删除等功能.
 *
 * @author 胡焕
 * @date 2016/11/28 10:51
 */
public class SwipeLayout extends HorizontalScrollView {

    public static final int SWIPE_MODE_LEFT = 1, SWIPE_MODE_RIGHT = 2;

    private View contentView;
    private View swipeView;
    private LinearLayout container;
    private int screenWidth;

    private int swipeMode = SWIPE_MODE_LEFT;
    private int leftOffset;
    private int keyOffset;
    private final int keyVelocity = 500;

    private boolean isOpen;

    private VelocityTracker mVelocityTracker;

    public SwipeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public SwipeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        setSmoothScrollingEnabled(true);
        if (attrs != null) {
            TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.SwipeLayout);
            swipeMode = ta.getInt(R.styleable.SwipeLayout_swipe_type, SWIPE_MODE_LEFT);//向左没去
        }
        setHorizontalScrollBarEnabled(false);
}


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        leftOffset = l;
        //ll向左移动增大,向右减小
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();//获得VelocityTracker类实例
        }
        mVelocityTracker.addMovement(ev);//将事件加入到VelocityTracker类实例中
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:
                //判断当ev事件是MotionEvent.ACTION_UP时：计算速率
                final VelocityTracker velocityTracker = mVelocityTracker;
                // 1000 provides pixels per second
                velocityTracker.computeCurrentVelocity(1, (float) 0.01); //设置maxVelocity值为0.1时，速率大于0.01时，显示的速率都是0.01,速率小于0.01时，显示正常
                velocityTracker.computeCurrentVelocity(1000);
                //速度<0向左移动
                System.out.println("speed:" + velocityTracker.getXVelocity());
                float speed = velocityTracker.getXVelocity();
                boolean speedAble = Math.abs(speed) > keyOffset;
                if (speed < 0 && leftOffset == swipeView.getWidth()) {
                    speedAble = false;
                } else if (speed > 0 && leftOffset == 0) {
                    speedAble = false;
                }

                boolean distanceAble;

                if (swipeMode == SWIPE_MODE_LEFT) {
                    if (isOpen) {
                        distanceAble = swipeView.getWidth() - leftOffset >= getKeyOffset();
                        if (distanceAble || speedAble) {
                            close();
                        } else {
                            open();
                        }
                    } else {
                        distanceAble = leftOffset >= getKeyOffset();
                        if (distanceAble || speedAble) {
                            open();
                        } else {
                            close();
                        }
                    }
                } else {
                    if (isOpen) {
                        distanceAble = leftOffset >= getKeyOffset();
                        if (distanceAble || speedAble) {
                            close();
                        } else {
                            open();
                        }
                    } else {
                        distanceAble = swipeView.getWidth() - leftOffset >= getKeyOffset();
                        if (distanceAble || speedAble) {
                            open();
                        } else {
                            close();
                        }
                    }
                }
                return true;
        }
        return super.onTouchEvent(ev);
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void open() {
        isOpen = true;
        smoothScrollTo(swipeMode != SWIPE_MODE_LEFT ? 0 : swipeView.getWidth(), 0);
    }

    public void close() {
        isOpen = false;
        smoothScrollTo(swipeMode != SWIPE_MODE_LEFT ? swipeView.getWidth() : 0, 0);
    }

    private int getKeyOffset() {
        return (keyOffset = keyOffset == 0 ? keyOffset = swipeView.getWidth() : keyOffset) / 3;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildAt(0) instanceof LinearLayout) {
            if (((LinearLayout) getChildAt(0)).getOrientation() != LinearLayout.HORIZONTAL) {
                throw new IllegalArgumentException("SwipeLayout's container must be a horizontal LinearLayout");
            }
            if (((ViewGroup) getChildAt(0)).getChildCount() != 2) {
                throw new IllegalArgumentException("SwipeLayout's container must contains only two children");
            }
        }
        container = (LinearLayout) getChildAt(0);

        if (swipeMode == SWIPE_MODE_LEFT) {
            swipeView = container.getChildAt(1);
            contentView = container.getChildAt(0);
        } else {
            swipeView = container.getChildAt(0);
            contentView = container.getChildAt(1);
        }

        screenWidth = getResources().getDisplayMetrics().widthPixels;
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) contentView.getLayoutParams();
        layoutParams.width = screenWidth;
        contentView.setLayoutParams(layoutParams);
        if (swipeMode == SWIPE_MODE_RIGHT)
            getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    if (swipeView.getWidth() != 0) {
                        getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }
                    SwipeLayout.super.scrollTo(swipeView.getWidth(), 0);
                }
            });

        swipeView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) {
                    clickListener.onClick(v);
                }
                close();
            }
        });
    }

    private OnClickListener clickListener;

    public void setSwipeViewListener(OnClickListener clickListener) {
        this.clickListener = clickListener;
    }
}
