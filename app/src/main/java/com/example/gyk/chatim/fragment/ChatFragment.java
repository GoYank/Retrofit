package com.example.gyk.chatim.fragment;

import android.os.Bundle;
import android.view.View;

import com.example.gyk.chatim.R;
import com.example.gyk.chatim.base.BaseFragment;

/**
 * Author : Gyk
 * CreateBy : 2018/11/19/10:54
 * PackageName : com.example.gyk.chatim.fragment
 * Describe : TODO
 **/
public class ChatFragment extends BaseFragment {
    @Override
    public View initView() {
        return View.inflate(getContext(), R.layout.fragment_chat,null);
    }

    @Override
    public void initData() {

    }

    public static ChatFragment newInstance(String arg) {
        ChatFragment fragment = new ChatFragment();
        Bundle args = new Bundle();
        args.putString("Tablayout",arg);
        fragment.setArguments(args);
        return fragment;
    }
}
