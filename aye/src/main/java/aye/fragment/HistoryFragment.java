package aye.fragment;

import android.support.design.widget.FloatingActionButton;
import android.view.View;

import aye.util.ToastUtils;
import aye.view.widget.DatePicker;
import reid.aye.R;
import reid.recycler.RecyclerList;

/**
 * Created by reid on 2016/12/4.
 */

public class HistoryFragment extends BaseFragment {

    public static HistoryFragment newInstance() {
        return new HistoryFragment();
    }

    private DatePicker mDatePicker;
    private RecyclerList mRecyclerList;
    private FloatingActionButton mActionButton;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_history;
    }

    @Override
    protected void initView(View root) {
        mDatePicker = (DatePicker) root.findViewById(R.id.history_picker);
        mRecyclerList = (RecyclerList) root.findViewById(R.id.history_recycler);
        mActionButton = (FloatingActionButton) root.findViewById(R.id.history_action);

        mDatePicker.setOnDateChangeListener((y, m, d) -> {
            ToastUtils.show("date change " + y + " - " + m + " - " + d);
        });

        mActionButton.setOnClickListener((v)->{
            ToastUtils.show("click Action Button");
        });
    }


}
