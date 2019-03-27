package com.example.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button mButtontrue;//正确
    private Button mButtonflase;//错误
    private Button mButtonNext;//下一题
    private Button mButtonAnswer;//查看答案
    private TextView mQuestiontv;//题目
    private  int mQuestionIdex=0;//索引
    private  Question[] mQuestions=new Question[]{
            new Question(R.string.Q1,false),
            new Question(R.string.Q2,true),
            new Question(R.string.Q3,true),
            new Question(R.string.Q4,true),
            new Question(R.string.Q5,true),
            new Question(R.string.Q6,true),
            new Question(R.string.Q7,true),
            new Question(R.string.Q8,true)
    };

    private static  final String TAG="MainActivity";//日志来源
    private static final String KEY_INEDX="index";//索引
    private static final int RESULET_CODE_ANSWER=10;//请求代码（answeractivity返回）
    private TranslateAnimation mTranslateAnimation;

    //更新题目
    private void UpdateQusetion(){
        int i=mQuestions[mQuestionIdex].getTextId();
        mQuestiontv.setText(i);
        /*mTranslateAnimation=new TranslateAnimation(-20,20,0,0);
        mTranslateAnimation.setDuration(100);
        mTranslateAnimation.setRepeatCount(5);
        mTranslateAnimation.setRepeatMode(Animation.REVERSE);
        mQuestiontv.startAnimation(mTranslateAnimation);*/
        Animation set=AnimationUtils.loadAnimation(this,R.anim.animation_list);
        mQuestiontv.startAnimation(set);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(savedInstanceState!=null){
            mQuestionIdex=savedInstanceState.getInt(KEY_INEDX,0);
        }
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Bundle: 恢复状态");
        setContentView(R.layout.activity_main);
        mButtontrue=findViewById(R.id.btntrue);
        mButtontrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkQuestion(true);
            }
        });
        mButtonflase=findViewById(R.id.btnfalse);
        mButtonflase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkQuestion(false);
            }
        });
        mQuestiontv=findViewById(R.id.tv_qusetion);
        UpdateQusetion();

        mButtonNext=findViewById(R.id.btn_next);
        mButtonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mQuestionIdex=(mQuestionIdex+1)%mQuestions.length;//防止循环溢出
                UpdateQusetion();//更新问题
                mButtonNext.setEnabled(false);//恢复下一题按钮不可用状态
                if(mQuestionIdex==mQuestions.length-1){
                    Toast.makeText(MainActivity.this,R.string.last,Toast.LENGTH_SHORT).show();
                    mButtonNext.setText(R.string.settext);
                    upBtnDrable(R.drawable.ic_set);
                }
                if(mQuestionIdex==0){
                    mButtonNext.setText(R.string.nexttext);
                    upBtnDrable(R.drawable.ic_next);
                }
            }
        });
        mButtonAnswer=findViewById(R.id.btn_answer);
        mButtonAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            //Intent intent=new Intent(MainActivity.this,AnswerActivity.class);
            //startActivity(intent);
            public void onClick(View v) {
                String temp;
                if(mQuestions[mQuestionIdex].isAnswer()){
                    temp="正确";
                }else{
                    temp="错误";
                }
                Intent intent=new Intent(MainActivity.this,AnswerActivity.class);
                intent.putExtra("mes",temp);
                startActivityForResult(intent,RESULET_CODE_ANSWER);//需要返回值的activity启动方法
               /* if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(MainActivity.this,new String []{Manifest.permission.CALL_PHONE},1);
                }
                //Activityd之间的跳转是显示调用
                //下面这种调用系统自带类的跳转是隐式调用
                Intent intent=new Intent(Intent.ACTION_CALL, Uri.parse("tel:15815089832"));
                startActivity(intent);*/
            }
        });
    }


    private void checkQuestion(boolean userAnswer){
        //取出对应题目的正确答案
        boolean trueAnswer=mQuestions[mQuestionIdex].isAnswer();
        int message;
        if(userAnswer==trueAnswer){
            message=R.string.yes;
            mButtonNext.setEnabled(true);
        }else{
            message=R.string.no;
            mButtonNext.setEnabled(false);
        }
        Toast.makeText(MainActivity.this,message,Toast.LENGTH_SHORT).show();
    }

    //更改按钮图片
    private void upBtnDrable(int imageID){
        Drawable d=getDrawable(imageID);//获取图片id
        d.setBounds(0,0,d.getMinimumWidth(),d.getMinimumHeight());//设置图片参数
        mButtonNext.setCompoundDrawables(null,null,d,null);//设置按钮里的图片
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: 界面可见");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: 前台显示");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: 界面离开前台");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: 界面不可见");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: 销毁"+TAG);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart: 我又回来了");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState: outState保存状态");
        outState.putInt(KEY_INEDX,mQuestionIdex);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {//请求代码，返回代码，返回intent对象
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RESULET_CODE_ANSWER&&resultCode== Activity.RESULT_OK){
           String temp=data.getStringExtra("answer_shown");
            Toast.makeText(MainActivity.this,temp,Toast.LENGTH_SHORT).show();
        }
    }


}
