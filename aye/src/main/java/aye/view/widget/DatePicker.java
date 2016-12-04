package aye.view.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;

import reid.aye.R;

/**
 * Created by reid on 2016/12/4.
 */

public class DatePicker extends LinearLayout implements View.OnClickListener {

    private ImageView mNavBefore;
    private ImageView mNavNext;
    private TextView mDateText;

    private Calendar mSelectedDate;
    private int year;
    private int month;
    private int day;

    private OnDateChangeListener onDateChangeListener;

    public DatePicker(Context context) {
        this(context, null);
    }

    public DatePicker(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DatePicker(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View.inflate(getContext(), R.layout.view_date_picker, this);
        mDateText = (TextView) findViewById(R.id.date_text);
        mNavBefore = (ImageView) findViewById(R.id.nav_before);
        mNavNext = (ImageView) findViewById(R.id.nav_next);
        mNavBefore.setOnClickListener(this);
        mNavNext.setOnClickListener(this);

        mSelectedDate = Calendar.getInstance();
        setSelectedDate();
    }

    /**
     * 设置日期显示
     */
    private void setDateText() {
        StringBuilder sb = new StringBuilder();
        sb.append(year).append("年").append(month).append("月").append(day).append("日");
        mDateText.setText(sb.toString());
    }

    /**
     * 重新赋值年月日,并对外通知日期变更
     */
    private void setSelectedDate() {
        int year = mSelectedDate.get(Calendar.YEAR);
        int month = mSelectedDate.get(Calendar.MONTH) + 1;
        int day = mSelectedDate.get(Calendar.DAY_OF_MONTH);
        if (this.year != year || this.month != month || this.day != day) {
            this.year = year;
            this.month = month;
            this.day = day;

            setDateText();
            if (onDateChangeListener != null) {
                onDateChangeListener.onDateChange(year, month, day);
            }
        }
    }

    /**
     * 获取当前选中的日期
     */
    public Calendar getSelectedCalendar() {
        return mSelectedDate;
    }

    /**
     * 获取当前选中的年
     */
    public int getSelectedYear() {
        return year;
    }

    /**
     * 获取当前选中的月
     */
    public int getSelectedMonth() {
        return month;
    }

    /**
     * 获取当前选中的日
     */
    public int getSelectedDay() {
        return day;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.nav_before:
                setSelectedDate(-1);
                break;
            case R.id.nav_next:
                setSelectedDate(1);
                break;
            default:
                break;
        }
    }

    /**
     * 设置选中的日期
     *
     * @param number 距离当前选中日期的天数
     */
    private void setSelectedDate(int number) {
        int day = mSelectedDate.get(Calendar.DATE);
        mSelectedDate.set(Calendar.DATE, day + number);
        setSelectedDate();
    }

    public void setOnDateChangeListener(OnDateChangeListener listener) {
        onDateChangeListener = listener;
    }

    public interface OnDateChangeListener {
        void onDateChange(int year, int month, int day);
    }
}
