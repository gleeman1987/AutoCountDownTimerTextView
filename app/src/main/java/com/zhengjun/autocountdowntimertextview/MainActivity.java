package com.zhengjun.autocountdowntimertextview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.actv)
    AutoCountDownTextView actv;
    @Bind(R.id.tv)
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        actv.setOnCountDownStartClickListener(new OnCountDownStartClickListener() {
            @Override
            public void onStartClick() {
                Toast.makeText(MainActivity.this, "onStartClick", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCount(long l) {
                tv.setText(""+l);
            }

            @Override
            public void onCountFinished() {
                tv.setText("onCountFinished");
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        actv.setState();
    }
}
