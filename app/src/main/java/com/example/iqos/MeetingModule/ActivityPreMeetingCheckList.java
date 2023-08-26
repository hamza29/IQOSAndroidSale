package com.example.iqos.MeetingModule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.example.iqos.AppointmentsModule.ActivityBookAppointment;
import com.example.iqos.AppointmentsModule.AppointmentAdapter;
import com.example.iqos.databinding.ActivityLeadsDetailBinding;
import com.example.iqos.databinding.ActivityPreMeetingCheckListBinding;

import java.util.ArrayList;

public class ActivityPreMeetingCheckList extends AppCompatActivity {

    ActivityPreMeetingCheckListBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityPreMeetingCheckListBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);


        premeetings();


    }

    private void premeetings() {

        ArrayList<String> premeetings = new ArrayList<>();

        premeetings.add("");
        premeetings.add("");
        premeetings.add("");

        PreMeetingAdapter preMeetingAdapter;
        mBinding.rvMeeting.setLayoutManager(new LinearLayoutManager(ActivityPreMeetingCheckList.this, LinearLayoutManager.VERTICAL, false));
        preMeetingAdapter = new PreMeetingAdapter(ActivityPreMeetingCheckList.this, premeetings);
        mBinding.rvMeeting.setAdapter(preMeetingAdapter);
    }


}