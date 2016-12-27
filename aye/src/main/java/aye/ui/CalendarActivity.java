package aye.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;

import reid.aye.R;

public class CalendarActivity extends BaseActivity {

    private MaterialCalendarView mCalendarView;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        setTitle("请选择日期");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setHomeButtonEnabled(true);


        mCalendarView = (MaterialCalendarView) findViewById(R.id.calendarView);
        mCalendarView.setSelectedDate(Calendar.getInstance());
        mButton = (Button) findViewById(R.id.btn_ok);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(mCalendarView.getSelectedDate());
                finish();
            }
        });
    }

}
