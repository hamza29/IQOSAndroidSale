package com.example.iqos.LoginModule;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.iqos.MainActivity;
import com.example.iqos.R;
import com.example.iqos.databinding.ActivityLoginBinding;

public class ActivityLogin extends AppCompatActivity {

    ActivityLoginBinding mBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);


        mBinding.ivLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityLogin.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }
}