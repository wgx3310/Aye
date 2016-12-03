package reid.recycler;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.support.annotation.AttrRes;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import aye.Constants;

/**
 * Created by reid on 2016/11/27.
 */

public class RecyclerList extends FrameLayout {

    private static final int DEFAULT_COUNT_LEFT_LOAD_MORE = 3;
    public static final int DEFAULT_SPAN_COUNT = Constants.DEFAULT_RECYCLER_SPAN_COUNT;

    private boolean mClipToPadding;
    private int mPadding;
    private int mPaddingLeft;
    private int mPaddingRight;
    private int mPaddingTop;
    private int mPaddingBottom;

    private SwipeRefreshLayout mSwipeRefresh;
    private RecyclerView mRecyclerView;
    private View mEmptyView;
    private View mLoadingView;
    private View mMoreView;

    private OnLoadMoreListener mOnLoadMoreListener;
    private boolean isLoadingMore;
    private int mItemCountLeftToLoadMore = DEFAULT_COUNT_LEFT_LOAD_MORE;

    private RecyclerView.OnScrollListener mExternalOnScrollListener;
    private OnItemClickListener mOnItemClickListener;
    private ItemTouchHelper mItemTouchHelper;

    private Adapter mAdapter;
    private GridLayoutManager mLayoutManager;

    public RecyclerList(@NonNull Context context) {
        this(context, null);
    }

    public RecyclerList(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecyclerList(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int
            defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
        initView();
    }

    private void initView() {
        View root = LayoutInflater.from(getContext()).inflate(R.layout.layout_recycler_list, this);
        mSwipeRefresh = (SwipeRefreshLayout) root.findViewById(R.id.layout_refresh);
        mSwipeRefresh.setClipChildren(false);
        mSwipeRefresh.setEnabled(false);

        mMoreView = root.findViewById(R.id.layout_more);
        mMoreView.setVisibility(View.GONE);
        mEmptyView = root.findViewById(R.id.layout_empty);
        mEmptyView.setVisibility(View.GONE);
        mLoadingView = root.findViewById(R.id.layout_loading);

        mRecyclerView = (RecyclerView) root.findViewById(R.id.recycler_view);
        initRecyclerView();
    }

    /**
     * 初始化自定义属性
     */
    private void initAttrs(AttributeSet attrs) {
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.RecyclerList);
        mClipToPadding = array.getBoolean(R.styleable.RecyclerList_clipToPadding, false);
        mPadding = array.getInt(R.styleable.RecyclerList_recyclerPadding, -1);
        mPaddingTop = (int) array.getDimension(R.styleable.RecyclerList_recyclerPaddingTop, 0.0f);
        mPaddingBottom = (int) array.getDimension(R.styleable.RecyclerList_recyclerPaddingBottom,
                0.0f);
        mPaddingLeft = (int) array.getDimension(R.styleable.RecyclerList_recyclerPaddingLeft, 0.0f);
        mPaddingRight = (int) array.getDimension(R.styleable.RecyclerList_recyclerPaddingRight,
                0.0f);
        array.recycle();
    }

    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView() {
        mRecyclerView.setClipChildren(false);
        mRecyclerView.setClipToPadding(mClipToPadding);
        if (mPadding != -1f) {
            mRecyclerView.setPadding(mPadding, mPadding, mPadding, mPadding);
        } else {
            mRecyclerView.setPadding(mPaddingLeft, mPaddingTop, mPaddingRight, mPaddingBottom);
        }

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(getContext(), DEFAULT_SPAN_COUNT);
        mLayoutManager.setSpanSizeLookup(mDefaultSpanSizeLookup);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(mDefaultItemDecoration);
        mRecyclerView.addOnScrollListener(mInternalOnScrollListener);
        mRecyclerView.addOnItemTouchListener(new OnRecyclerItemTouchListener());

        mItemTouchHelper = new ItemTouchHelper(mItemTouchCallback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    private GridLayoutManager.SpanSizeLookup mDefaultSpanSizeLookup = new GridLayoutManager
            .SpanSizeLookup() {
        @Override
        public int getSpanSize(int position) {
            if (mAdapter != null) {
                return mAdapter.getSpanSize(position);
            }
            return DEFAULT_SPAN_COUNT;
        }
    };

    private RecyclerView.ItemDecoration mDefaultItemDecoration = new RecyclerView.ItemDecoration(){
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView
                .State state) {
            int childAdapterPosition = parent.getChildAdapterPosition(view);
            GridLayoutManager.SpanSizeLookup spanSizeLookup = mLayoutManager
                    .getSpanSizeLookup();
            int spanIndex = spanSizeLookup.getSpanIndex(childAdapterPosition, mLayoutManager
                    .getSpanCount());
            int spanSize = spanSizeLookup.getSpanSize(childAdapterPosition);

            int bottom = getResources().getDimensionPixelOffset(R.dimen.size_30);
            int padding = getResources().getDimensionPixelOffset(R.dimen.size_40);
            int divider = getResources().getDimensionPixelOffset(R.dimen.size_10);
            if (spanSize == mLayoutManager.getSpanCount()) {
                outRect.set(0, 0, 0, bottom);
            } else {
                if (spanIndex == 0) {
                    outRect.set(padding, 0, divider, bottom);
                } else if (spanIndex + spanSize == mLayoutManager.getSpanCount()) {
                    outRect.set(divider, 0, padding, bottom);
                } else {
                    outRect.set(divider, 0, divider, bottom);
                }
            }
        }
    };

    private ItemTouchHelper.Callback mItemTouchCallback = new ItemTouchHelper.Callback() {
        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT |
                    ItemTouchHelper.RIGHT;
            int swipeFlags = 0;
//            if (mAdapter.getSpanSize(viewHolder.getAdapterPosition()) == DEFAULT_SPAN_COUNT) {
//                swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
//            }
            return makeMovementFlags(dragFlags, swipeFlags);
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                              RecyclerView.ViewHolder target) {
            int fromPosition = viewHolder.getAdapterPosition();
            int toPosition = target.getAdapterPosition();
            if (fromPosition < toPosition) {
                for (int i = fromPosition; i < toPosition; i++) {
                    Collections.swap(mAdapter.getData(), i, i + 1);
                }
            } else {
                for (int i = fromPosition; i > toPosition; i--) {
                    Collections.swap(mAdapter.getData(), i, i - 1);
                }
            }
            mAdapter.notifyItemMoved(fromPosition, toPosition);
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            mAdapter.getData().remove(viewHolder.getAdapterPosition());
        }

        @Override
        public boolean isLongPressDragEnabled() {
            return false;
        }

        @Override
        public boolean isItemViewSwipeEnabled() {
            return false;
        }
    };

    /**
     * RecyclerView item点击监听
     */
    private class OnRecyclerItemTouchListener extends GestureDetector.SimpleOnGestureListener
            implements RecyclerView.OnItemTouchListener {

        private GestureDetectorCompat mGestureDetector;

        public OnRecyclerItemTouchListener() {
            mGestureDetector = new GestureDetectorCompat(getContext(), this);
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            mGestureDetector.onTouchEvent(e);
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
            mGestureDetector.onTouchEvent(e);
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }

        //点击事件
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            View childView = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
            if (childView != null && mOnItemClickListener != null) {
                RecyclerView.ViewHolder vh = mRecyclerView.getChildViewHolder(childView);
                mOnItemClickListener.onItemClick(mRecyclerView, vh);
            }
            return true;
        }

        //长击事件
//        @Override
//        public void onLongPress(MotionEvent e) {
//            super.onLongPress(e);
//            View childView = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
//            if (childView != null) {
//                RecyclerView.ViewHolder vh = mRecyclerView.getChildViewHolder(childView);
//                if (mAdapter.isLongPressDragEnable(vh.getAdapterPosition())) {
//                    mItemTouchHelper.startDrag(vh);
//                }
//            }
//        }
    }

    private RecyclerView.OnScrollListener mInternalOnScrollListener = new RecyclerView
            .OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (mExternalOnScrollListener != null) {
                mExternalOnScrollListener.onScrollStateChanged(recyclerView, newState);
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (mExternalOnScrollListener != null) {
                mExternalOnScrollListener.onScrolled(recyclerView, dx, dy);
            }

            handleOnLoadMore();
        }
    };

    /**
     * 处理加载更多
     */
    private void handleOnLoadMore() {
        GridLayoutManager layoutManager = (GridLayoutManager) mRecyclerView.getLayoutManager();
        int visibleChildCount = layoutManager.getChildCount();
        int itemCount = layoutManager.getItemCount();
        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();

        if (((itemCount - lastVisibleItemPosition) <= mItemCountLeftToLoadMore || (itemCount -
                lastVisibleItemPosition) == 0 && itemCount > visibleChildCount) && !isLoadingMore) {

            if (mOnLoadMoreListener != null) {
                isLoadingMore = true;
                mMoreView.setVisibility(View.VISIBLE);
                mOnLoadMoreListener.onLoadMore(itemCount, mItemCountLeftToLoadMore,
                        lastVisibleItemPosition);
            }
        }
    }

    /**
     * Set a new adapter to provide child views on demand.
     */
    public void setAdapter(Adapter adapter) {
        setAdapterInternal(adapter, false, true);
    }

    public void setAdapter(Adapter adapter, boolean compatibleWithPrevious) {
        setAdapterInternal(adapter, compatibleWithPrevious, true);
    }

    public void swapAdapter(Adapter adapter, boolean removeAndRecycleExistingViews) {
        setAdapterInternal(adapter, true, removeAndRecycleExistingViews);
    }

    private void setAdapterInternal(Adapter adapter, boolean compatibleWithPrevious, boolean
            removeAndRecycleExistingViews) {
        if (compatibleWithPrevious) {
            mRecyclerView.swapAdapter(adapter, removeAndRecycleExistingViews);
        } else {
            mRecyclerView.setAdapter(adapter);
        }

        mLoadingView.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        mSwipeRefresh.setRefreshing(false);

        mAdapter = adapter;
        if (mAdapter != null) {
            mAdapter.registerAdapterDataObserver(mAdapterDataObserver);
        }

        mEmptyView.setVisibility(mAdapter != null && mAdapter.getItemCount() > 0 ? View.GONE :
                View.VISIBLE);
    }

    private void notifyAdapterChanged() {
        mLoadingView.setVisibility(View.GONE);
        mMoreView.setVisibility(View.GONE);
        isLoadingMore = false;
        mSwipeRefresh.setRefreshing(false);
        if (mAdapter != null && mAdapter.getItemCount() == 0) {
            mEmptyView.setVisibility(View.VISIBLE);
        } else {
            mEmptyView.setVisibility(View.GONE);
        }
    }

    private RecyclerView.AdapterDataObserver mAdapterDataObserver = new RecyclerView
            .AdapterDataObserver() {
        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            super.onItemRangeChanged(positionStart, itemCount);
            notifyAdapterChanged();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);
            notifyAdapterChanged();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);
            notifyAdapterChanged();
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            super.onItemRangeMoved(fromPosition, toPosition, itemCount);
            notifyAdapterChanged();
        }

        @Override
        public void onChanged() {
            super.onChanged();
            notifyAdapterChanged();
        }
    };

    /**
     * 设置刷新监听
     */
    public void setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener listener) {
        if (listener != null) {
            mSwipeRefresh.setEnabled(true);
            mSwipeRefresh.setOnRefreshListener(listener);
        }
    }

    /**
     * 是否正在刷新
     */
    public boolean isRefreshing() {
        return mSwipeRefresh.isRefreshing();
    }

    /**
     * 刷新完成
     *
     * @param refreshing
     */
    public void setRefreshing(boolean refreshing) {
        mSwipeRefresh.setRefreshing(refreshing);
    }

    /**
     * Set the colors for the SwipeRefreshLayout states
     */
    public void setRefreshingColorResources(@ColorRes int colRes1, @ColorRes int colRes2,
                                            @ColorRes int colRes3, @ColorRes int colRes4) {
        mSwipeRefresh.setColorSchemeResources(colRes1, colRes2, colRes3, colRes4);
    }

    /**
     * Set the colors for the SwipeRefreshLayout states
     */
    public void setRefreshingColor(int col1, int col2, int col3, int col4) {
        mSwipeRefresh.setColorSchemeColors(col1, col2, col3, col4);
    }

    public void setOnLoadMoreListener(OnLoadMoreListener listener) {
        mOnLoadMoreListener = listener;
    }

    /**
     * 是否正在加载更多
     */
    public boolean isLoadingMore() {
        return isLoadingMore;
    }

    /**
     * 更多加载完成
     */
    public void onLoadCompleted() {
        isLoadingMore = false;
        mMoreView.setVisibility(View.GONE);
    }

    public int getItemCountLeftToLoadMore() {
        return mItemCountLeftToLoadMore;
    }

    public void setItemCountLeftToLoadMore(int count) {
        if (count < 0) mItemCountLeftToLoadMore = count;
    }

    /**
     * 设置RecyclerView滚动事件监听
     *
     * @param listener
     */
    public void setOnScrollListener(RecyclerView.OnScrollListener listener) {
        mExternalOnScrollListener = listener;
    }

    /**
     * 设置RecyclerView item点击事件监听
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    /**
     * Animate a scroll by the given amount of pixels along either axis.
     */
    public void smoothScrollBy(int dx, int dy) {
        mRecyclerView.smoothScrollBy(dx, dy);
    }

    public interface OnLoadMoreListener {
        void onLoadMore(int itemCount, int itemBeforeMoreCount, int maxLastVisiblePosition);
    }

    /**
     * item点击事件回调
     */
    public interface OnItemClickListener {
        void onItemClick(RecyclerView rv, RecyclerView.ViewHolder vh);
    }

    /**
     * RecyclerView Adapter基类
     */
    public static abstract class Adapter<T, VH extends RecyclerView.ViewHolder> extends
            RecyclerView.Adapter {

        private final int defaultItemBottomPadding = Constants.DEFAULT_ITEM_PADDING;

        protected List<T> mData = new ArrayList<>();

        public List<T> getData() {
            return mData;
        }

        /**
         * 设置数据源
         */
        public void setData(List<T> data) {
            mData.clear();
            if (data != null) {
                mData = data;
            }
            notifyDataSetChanged();
        }

        /**
         * 添加数据
         */
        public void addData(List<T> data) {
            if (data != null) {
                int startIndex = mData.size();
                mData.addAll(startIndex, data);
                notifyItemRangeInserted(startIndex, data.size());
            }
        }

        /**
         * 删除position位置数据
         */
        public void remove(int position) {
            mData.remove(position);
            notifyItemRemoved(position);
        }

        /**
         * 清除Adapter所有数据
         */
        public void clear() {
            int size = mData.size();
            mData.clear();
            notifyItemRangeRemoved(0, size);
        }

        @Override
        public int getItemCount() {
            if (mData != null) {
                return mData.size();
            }
            return 0;
        }

        //获取第position个item的span
        public int getSpanSize(int position) {
            return DEFAULT_SPAN_COUNT;
        }

        //获取item的BottomPadding
        public int getItemBottomPadding(int position){
            return defaultItemBottomPadding;
        }

        //获取第position个item数据
        public T getItem(int position) {
            if (position < 0 || position >= mData.size()) return null;

            return mData.get(position);
        }

        public boolean isLongPressDragEnable(int position) {
            return false;
        }
    }
}
