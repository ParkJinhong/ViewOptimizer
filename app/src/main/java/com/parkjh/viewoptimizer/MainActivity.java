package com.parkjh.viewoptimizer;

import android.content.Context;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private Context mContext;
    private ViewOptimizer mViewOptimizer;

    private View mP1;
    private View mP2;
    private View mP3;
    private View mP4;

    private View mXY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this.getApplicationContext();
        mViewOptimizer = new ViewOptimizer(mContext, 360);

        mP1 = findViewById(R.id.ellipse_7);
        mP2 = findViewById(R.id.ellipse_8);
        mP3 = findViewById(R.id.ellipse_9);
        mP4 = findViewById(R.id.ellipse_10);

        mXY = findViewById(R.id.xy_1);

        mViewOptimizer.optimize(mP1);
        mViewOptimizer.optimize(mP2);
        mViewOptimizer.optimize(mP3);
        mViewOptimizer.optimize(mP4);
        mViewOptimizer.optimize(mXY);
    }
}