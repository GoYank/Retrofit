package com.example.gyk.chatim.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.gyk.chatim.R;
import com.example.gyk.chatim.adapter.LoadMoreAdapter;
import com.example.gyk.chatim.base.BaseFragment;
import com.example.gyk.chatim.base.BaseParameter;
import com.example.gyk.chatim.bean.MessBean;
import com.example.gyk.chatim.https.HttpRxListener;
import com.example.gyk.chatim.https.RtRxOkHttp;
import com.example.gyk.chatim.utils.EndlessRecyclerOnScrollListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Observable;

/**
 * Author : Gyk
 * CreateBy : 2018/11/20/9:21
 * PackageName : com.example.gyk.chatim.fragment
 * Describe : TODO
 **/
public class LinkManFragment extends BaseFragment implements HttpRxListener {

    @BindView(R.id.rl_linkman)
    RecyclerView rl_linkman;

    private int page = 1;
    private LoadMoreAdapter loadMoreAdapter;
    private List<MessBean.ResultBean> data = new ArrayList<>();
    @Override
    public View initView() {
        return View.inflate(getContext(), R.layout.fragment_linkman, null);
    }

    @Override
    public void initData() {
        getDataNet();
        initRecy();
    }

    private void initRecy(){
        loadMoreAdapter = new LoadMoreAdapter(data);
        rl_linkman.setLayoutManager(new LinearLayoutManager(getContext()));
        rl_linkman.setAdapter(loadMoreAdapter);
        rl_linkman.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                loadMoreAdapter.setLoadState(loadMoreAdapter.LOADING);
                if(data.size()>=10){
                    page++;
                    getDataNet();
//                    loadMoreAdapter.setLoadState(loadMoreAdapter.LOADING_COMPLETE);
                }else {
                    loadMoreAdapter.setLoadState(loadMoreAdapter.LOADING_END);
                }
            }
        });
    }

    private void getDataNet(){
        BaseParameter hashMap = new BaseParameter();
        Observable<MessBean> dataNet = RtRxOkHttp.getApiService().getMessageNet(hashMap);
        hashMap.put("chaid",0+"");
        hashMap.put("page",page+"");
        hashMap.put("pagesize",10+"");
        RtRxOkHttp.getInstance().createRtRx(getActivity(),dataNet,this,1);
    }


    @Override
    public void httpResponse(Object info, boolean isSusccess, int sort) {
        if (isSusccess){
            switch (sort){
                case 1:
                    MessBean messBean = (MessBean)info;
                    if (messBean.getCode()==200){
                        for (int i=0;i<messBean.getResult().size();i++){
                            data.add(messBean.getResult().get(i));
                        }
                        loadMoreAdapter.notifyDataSetChanged();
                    }
                    break;
            }
        }
    }





    public static LinkManFragment newInstance(String arg) {
        LinkManFragment fragment = new LinkManFragment();
        Bundle args = new Bundle();
        args.putString("Tablayout", arg);
        fragment.setArguments(args);
        return fragment;
    }
}
