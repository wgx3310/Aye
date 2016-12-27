package aye.fragment;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

import com.prolificinteractive.materialcalendarview.CalendarDay;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import aye.adapter.RecyclerAdapter;
import aye.model.Block;
import aye.model.DisplayItem;
import aye.net.DataLoader;
import aye.ui.CalendarActivity;
import aye.util.CircularAnimUtils;
import aye.util.ToastUtils;
import aye.view.widget.DatePicker;
import reid.aye.R;
import reid.recycler.RecyclerList;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by reid on 2016/12/4.
 */

public class HistoryFragment extends BaseFragment {

    public static HistoryFragment newInstance() {
        return new HistoryFragment();
    }

    private DatePicker mDatePicker;
    private FloatingActionButton mActionButton;
    private RecyclerList mRecyclerList;
    private RecyclerAdapter mAdapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_history;
    }

    @Override
    protected void initView(View root) {
        mDatePicker = (DatePicker) root.findViewById(R.id.history_picker);
        mRecyclerList = (RecyclerList) root.findViewById(R.id.history_recycler);
        mActionButton = (FloatingActionButton) root.findViewById(R.id.history_action);

        mAdapter = new RecyclerAdapter();
        mRecyclerList.setAdapter(mAdapter);

        mDatePicker.setOnDateChangeListener(new DatePicker.OnDateChangeListener() {
            @Override
            public void onDateChange(int year, int month, int day) {
                loadData(month, day);
            }
        });

        mActionButton.setOnClickListener((v) -> {
            Intent intent = new Intent(getActivity(), CalendarActivity.class);
            CircularAnimUtils.startActivityForResult(getActivity(), intent, 101, v, R.color
                    .primary);
        });

        EventBus.getDefault().register(this);

        int month = mDatePicker.getMonth();
        int day = mDatePicker.getDay();
        loadData(month, day);
    }

    @Subscribe
    public void onEventMainThread(CalendarDay calendarDay) {
        loadData(calendarDay.getMonth(), calendarDay.getDay());
    }

    private void loadData(int month, int day) {

        Observable<Block<DisplayItem>> observable = DataLoader.loadTodayHistory(month, day);
        Subscription subscribe = observable.subscribe(new Action1<Block<DisplayItem>>() {
            @Override
            public void call(Block<DisplayItem> block) {
                mAdapter.setData(block.blocks);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                ToastUtils.show("加载数据失败 ");
                mRecyclerList.setRefreshing(false);
            }
        });
        addSubscription(subscribe);
    }


    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
