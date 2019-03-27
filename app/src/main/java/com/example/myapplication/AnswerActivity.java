package com.example.myapplication;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;

import static com.example.myapplication.R.drawable.animation_frame;

public class AnswerActivity extends AppCompatActivity {
    private TextView mAnswerTextView;
    private ImageView mImageView;
    private static final String EXTRE_ANSWER_SHOWS="answer_shown";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_answer);
        mAnswerTextView=findViewById(R.id.answer_tv);
        mImageView=findViewById(R.id.imageView);
        Intent data=getIntent();
        String answer=data.getStringExtra("msg");
        mAnswerTextView.setText(answer);
        data.putExtra(EXTRE_ANSWER_SHOWS,"您已查看答案");
        setResult(Activity.RESULT_OK,data);//子类返回父类代码和intent对象（包含返回数据）
        Animator set= AnimatorInflater.loadAnimator(this,R.animator.animator_set);
        set.setTarget(mImageView);
        set.start();
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                //TODO 动画开始时
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //TODO 动画结束时
                //数值差值器
                ValueAnimator moneyAnimator=ValueAnimator.ofFloat(0f,12612.36f);
                moneyAnimator.setInterpolator(new LinearInterpolator());
                moneyAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float money= (float) animation.getAnimatedValue();
                        mAnswerTextView.setText(String.format("%.2f",money));
                    }
                });
                //moneyAnimator.setDuration(5000);
                //moneyAnimator.start();

                //颜色差值器
                int StartColor= Color.parseColor("#ffdead");
                int EndColor= Color.parseColor("#ffdead");
                ValueAnimator colorAnimator=ValueAnimator.ofArgb(StartColor,EndColor);
                colorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int color= (int) animation.getAnimatedValue();
                        mAnswerTextView.setTextColor(color);
                    }
                });
                AnimatorSet Set=new AnimatorSet();
                Set.playTogether(moneyAnimator,colorAnimator);
                Set.setDuration(3000);
                Set.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                //TODO 动画取消时
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                //TODO 动画重复时
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
       /* mImageView.setImageResource(R.drawable.animation_frame);
        AnimationDrawable animationDrawable= (AnimationDrawable) mImageView.getDrawable();
        animationDrawable.start();*/

    }
}
