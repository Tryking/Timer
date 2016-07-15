package com.tryking.EasyList.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.tryking.EasyList.R;
import com.tryking.EasyList.base.BaseActivity;
import com.tryking.EasyList.base.SystemInfo;
import com.tryking.EasyList.utils.ActivityUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PIMSActivity extends BaseActivity {

    @Bind(R.id.head_portrait)
    ImageView headPortrait;
    @Bind(R.id.toolBar)
    Toolbar toolBar;
    @Bind(R.id.tv_nickName)
    TextView tvNickName;
    @Bind(R.id.rl_nickName)
    RelativeLayout rlNickName;
    @Bind(R.id.tv_QQ)
    TextView tvQQ;
    @Bind(R.id.rl_boundQQ)
    RelativeLayout rlBoundQQ;
    @Bind(R.id.tv_Sina)
    TextView tvSina;
    @Bind(R.id.rl_boundSina)
    RelativeLayout rlBoundSina;
    @Bind(R.id.tv_gender)
    TextView tvGender;
    @Bind(R.id.rl_gender)
    RelativeLayout rlGender;
    @Bind(R.id.tv_signature)
    TextView tvSignature;
    @Bind(R.id.rl_signature)
    RelativeLayout rlSignature;
    @Bind(R.id.bt_logout)
    Button btLogout;
    @Bind(R.id.toolBarLayout)
    CollapsingToolbarLayout toolBarLayout;


    @OnClick({R.id.bt_logout})
    void click(View v) {
        switch (v.getId()) {
            case R.id.bt_logout:
                new AlertDialog.Builder(this)
                        .setTitle("退出登陆")
                        .setMessage("是否确认退出当前账号？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SystemInfo.getInstance(getApplicationContext()).logout();
                                startActivity(new Intent(PIMSActivity.this, LoginAndRegisterActivity.class));
                                ActivityUtils.getInstance().killAllActivities();
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pims);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        initToolBar();
        Uri uri = Uri.parse(SystemInfo.getInstance(getApplicationContext()).getPortraitUrl());
        Glide.with(this).load(uri).asBitmap().centerCrop().into(new BitmapImageViewTarget(headPortrait) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), resource);
                roundedBitmapDrawable.setCircular(true);
                headPortrait.setImageDrawable(roundedBitmapDrawable);
            }
        });
        if (SystemInfo.getInstance(getApplicationContext()).getMemberId() == "try") {
            tvNickName.setText("试用账号");
            tvQQ.setText("未绑定");
            tvSina.setText("未绑定");
        } else {
            tvNickName.setText(SystemInfo.getInstance(getApplicationContext()).getAccount());
            tvQQ.setText(SystemInfo.getInstance(getApplicationContext()).getQQName() == "" ? "未绑定" : SystemInfo.getInstance(getApplicationContext()).getQQName());
            tvSina.setText(SystemInfo.getInstance(getApplicationContext()).getSinaName() == "" ? "未绑定" : SystemInfo.getInstance(getApplicationContext()).getSinaName());
        }
    }

    /*
    初始化ToolBar
     */
    private void initToolBar() {
        toolBarLayout.setTitle(getResources().getString(R.string.individual_center));
        toolBarLayout.setExpandedTitleColor(getResources().getColor(R.color.float_transparent));
        toolBarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.white));
        toolBar.setNavigationIcon(R.drawable.ic_action_arrow_left);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PIMSActivity.this.finish();
            }
        });
    }
}
