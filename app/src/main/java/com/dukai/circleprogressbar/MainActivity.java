package com.dukai.circleprogressbar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    int currentCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final CircleDotProgressBar circleDotProgressBar = findViewById(R.id.CircleProgressBar);
        final int dotCount = circleDotProgressBar.getDotCount();

        circleDotProgressBar.setCurrentCount(currentCount);

        findViewById(R.id.increase).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentCount == dotCount) {
                    return;
                }
                currentCount++;
                circleDotProgressBar.setCurrentCount(currentCount);

            }
        });

        findViewById(R.id.decrease).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentCount == 0) {
                    return;
                }
                currentCount--;
                circleDotProgressBar.setCurrentCount(currentCount);
            }
        });
    }
}
