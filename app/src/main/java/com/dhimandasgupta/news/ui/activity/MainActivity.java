package com.dhimandasgupta.news.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.dhimandasgupta.news.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.activity_button).setOnClickListener(this);
        findViewById(R.id.view_pager_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_button :
                startActivity(new Intent(getBaseContext(), SingleExampleActivity.class));
                break;

            case R.id.view_pager_button :
                startActivity(new Intent(getBaseContext(), ViewPagerExampleActivity.class));
                break;
        }
    }
}
