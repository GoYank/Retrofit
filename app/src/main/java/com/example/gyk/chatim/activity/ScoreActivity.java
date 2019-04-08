package com.example.gyk.chatim.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gyk.chatim.MainActivity;
import com.example.gyk.chatim.MyApplication;
import com.example.gyk.chatim.R;
import com.example.gyk.chatim.base.BaseActivity;
import com.example.gyk.chatim.bean.AnswerData;
import com.example.gyk.chatim.bean.QuestionBank;
import com.example.gyk.chatim.fragment.MyFragment;
import com.example.gyk.chatim.greendao.AnswerDataDao;
import com.example.gyk.chatim.greendao.DaoSession;
import com.example.gyk.chatim.greendao.QuestionBankDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ScoreActivity extends BaseActivity {
    @BindView(R.id.tv_score)
    TextView tv_score;
    List<QuestionBank> questionBank = new ArrayList<>();
    List<AnswerData> answerDatas = new ArrayList<>();
    int score = 0;

    @Override
    public void initView() {
        setContentView(R.layout.activity_score);
    }

    @Override
    public void initData() {
        answerDatas = queryAnswerData();
        for (int i = 0; i < answerDatas.size(); i++) {
            if (answerDatas.get(i).getAnswer().equals(queryListByMessage(answerDatas.get(i).getTopicNum()).get(0).getAnswer())){
                score =score+2;
            }
        }
        tv_score.setText(  "您的得分为："+score);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK) {
            startActivity(new Intent(this,MainActivity.class));
        }
        return true;
    }
    @OnClick({R.id.IvBack})
    public void onViewClicked(View v){
        switch (v.getId()){
            case R.id.IvBack:
                startActivity(new Intent(this,MainActivity.class));
                break;
        }
    }

    @Override
    public void addActivity() {
        MyApplication.getInstance().addActivity(this);
    }

    public List<QuestionBank> queryListByMessage(int id) {
        DaoSession daoSession = ((MyApplication)getApplication()).getDaoSession();
        QueryBuilder<QuestionBank> qb = daoSession.queryBuilder(QuestionBank.class);
        QueryBuilder<QuestionBank> studentQueryBuilder = qb.where(QuestionBankDao.Properties.Id.eq(id)).orderAsc(QuestionBankDao.Properties.Id);
        List<QuestionBank> answerList = studentQueryBuilder.list(); //查出当前对应的数据
        return answerList;
    }

    public List<AnswerData> queryAnswerData() {
        List<AnswerData> answerData = new ArrayList<>();
        DaoSession daoSession = ((MyApplication) getApplication()).getDaoSession();
        answerData = daoSession.loadAll(AnswerData.class);
        return answerData;
    }


}
