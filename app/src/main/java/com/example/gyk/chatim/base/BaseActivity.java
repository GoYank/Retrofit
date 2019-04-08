package com.example.gyk.chatim.base;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.example.gyk.chatim.MyApplication;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import butterknife.ButterKnife;

/**
 * Author : Gyk
 * CreateBy : 2018/11/16/13:52
 * PackageName : com.example.gyk.chatim.base
 * Describe : TODO
 **/
public  abstract  class BaseActivity extends FragmentActivity {
    public Context context;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        initView();
        ButterKnife.bind(this);
        initData();
        addActivity();

    }


    public abstract void initView();
    public abstract void initData();
    public abstract void addActivity();


    public void setImg(SimpleDraweeView mImg, String url) {
        Uri uri = Uri.parse(url);
        ImageRequest request = null;
        request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setResizeOptions(new ResizeOptions(MyApplication.width - 200, (int) ((MyApplication.width - 200) * 0.4)))
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setOldController(mImg.getController())
                .setControllerListener(new BaseControllerListener<ImageInfo>())
                .setImageRequest(request).build();
        mImg.setController(controller);

    }
    public void toastMsg(View parent, String content) {
        Snackbar.make(parent, content, Snackbar.LENGTH_SHORT).show();
    }

    public boolean setPermission(View view) {
        for (int i = 0; i < PERMISSIONS_STORAGE.length; i++) {
            //检查权限
            int j = ContextCompat.checkSelfPermission(MyApplication.getInstance(), PERMISSIONS_STORAGE[i]);
            if (j != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, 321);
                toastMsg(view, "请手动开启访问权限");
                return false;
            }
        }

        return true;
    }

    public boolean mStateEnable;

    @Override
    protected void onStart() {
        super.onStart();
        mStateEnable = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mStateEnable = true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mStateEnable = false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        mStateEnable = false;
    }
    public boolean isStateEnable(){
        return mStateEnable;
    }
}
