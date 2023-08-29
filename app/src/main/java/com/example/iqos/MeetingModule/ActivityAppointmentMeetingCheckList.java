package com.example.iqos.MeetingModule;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.iqos.databinding.ActivityAppointmentMeetingCheckListBinding;
import com.example.iqos.databinding.ActivityPreMeetingCheckListBinding;

import java.util.ArrayList;

public class ActivityAppointmentMeetingCheckList extends AppCompatActivity {

    ActivityAppointmentMeetingCheckListBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityAppointmentMeetingCheckListBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);


        premeetings();


    }

    private void premeetings() {

        ArrayList<String> premeetings = new ArrayList<>();

        premeetings.add("");
        premeetings.add("");
        premeetings.add("");

//        PreMeetingAdapter preMeetingAdapter;
//        mBinding.rvMeeting.setLayoutManager(new LinearLayoutManager(ActivityAppointmentMeetingCheckList.this, LinearLayoutManager.VERTICAL, false));
//        preMeetingAdapter = new PreMeetingAdapter(ActivityAppointmentMeetingCheckList.this, premeetings);
//        mBinding.rvMeeting.setAdapter(preMeetingAdapter);
    }


}