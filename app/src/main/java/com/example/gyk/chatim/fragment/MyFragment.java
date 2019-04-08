package com.example.gyk.chatim.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.gyk.chatim.R;
import com.example.gyk.chatim.activity.AnswerActivity;
import com.example.gyk.chatim.base.BaseFragment;

import butterknife.BindView;

/**
 * Author : Gyk
 * CreateBy : 2018/11/20/9:21
 * PackageName : com.example.gyk.chatim.fragment
 * Describe : TODO
 **/
public class MyFragment extends BaseFragment {
    @BindView(R.id.bt)
    Button bt;
    @Override
    public View initView() {
        return View.inflate(getContext(), R.layout.fragment_my,null);
    }

    @Override
    public void initData() {
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AnswerActivity.class));
            }
        });
    }

    public static MyFragment newInstance(String arg) {
        MyFragment fragment = new MyFragment();
        Bundle args = new Bundle();
        args.putString("Tablayout",arg);
        fragment.setArguments(args);
        return fragment;
    }
}
