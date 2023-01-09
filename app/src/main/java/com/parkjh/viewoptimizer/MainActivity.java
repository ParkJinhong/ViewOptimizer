package com.parkjh.viewoptimizer;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Context mContext;
    private ViewOptimizer mViewOptimizer;

    private View mP1;
    private View mP2;
    private View mP3;
    private View mP4;

    private View mXY;

    private TextView mT1;
    private TextView mT2;
    private TextView mT3;
    private TextView mT4;

    private List<View> opimizerViews;

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

        mT1 = findViewById(R.id.text1);
        mT2 = findViewById(R.id.text2);
        mT3 = findViewById(R.id.text3);
        mT4 = findViewById(R.id.text4);

        opimizerViews = Arrays.asList(mP1, mP2, mP3, mP4, mXY, mT1, mT2, mT3, mT4);
        mViewOptimizer.optimize(opimizerViews);
    }
}