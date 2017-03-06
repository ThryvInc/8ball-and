package com.rndapp.ellseightball;

import android.content.Context;
import android.hardware.SensorManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;


public class EightballActivity extends ActionBarActivity implements ShakingCallback {
    protected ShakingModel mShakingDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eightball);
    }

    @Override
    protected void onResume() {
        super.onResume();

        SensorManager manager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        mShakingDetector = new ShakingModel(this, manager);
    }

    @Override
    public void didStartShaking() {
        AnimationSet set = new AnimationSet(this, null);
        set.setFillEnabled(true);
        set.setFillAfter(true);
        set.setDuration(300);

        TranslateAnimation angleUp = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_PARENT, 0f,
                TranslateAnimation.RELATIVE_TO_PARENT, .1f,
                TranslateAnimation.RELATIVE_TO_PARENT, 0f,
                TranslateAnimation.RELATIVE_TO_PARENT, -.1f
        );
        RotateAnimation rotateByTwenty = new RotateAnimation(0f, 20f);
        AlphaAnimation fadeOut = new AlphaAnimation(1,0);

        set.addAnimation(angleUp);
        set.addAnimation(rotateByTwenty);
        set.addAnimation(fadeOut);

        View textView = findViewById(R.id.textView);
        textView.startAnimation(set);
    }

    @Override
    public void didStopShaking() {
        AnimationSet set = new AnimationSet(this, null);
        set.setFillEnabled(true);
        set.setFillAfter(true);
        set.setDuration(1000);

        TranslateAnimation angleUp = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_PARENT, -.1f,
                TranslateAnimation.RELATIVE_TO_PARENT, 0f,
                TranslateAnimation.RELATIVE_TO_PARENT, .1f,
                TranslateAnimation.RELATIVE_TO_PARENT, 0f
        );
        RotateAnimation rotateByTwenty = new RotateAnimation(-20f, 0f);
        AlphaAnimation fadeOut = new AlphaAnimation(0,1);

        set.addAnimation(angleUp);
        set.addAnimation(rotateByTwenty);
        set.addAnimation(fadeOut);

        TextView textView = (TextView)findViewById(R.id.textView);
        textView.startAnimation(set);

        String[] stringArray = getResources().getStringArray(R.array.affirmations);
        textView.setText(stringArray[new Random().nextInt(stringArray.length)]);
    }
}
