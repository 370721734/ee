package com.jarhero790.eub.message.souye;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.jarhero790.eub.R;
import com.jarhero790.eub.message.message.PrivateJiuBaoActivity;
import com.jarhero790.eub.utils.CommonUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserJiuBaoActivity extends AppCompatActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.rl1)
    RelativeLayout rl1;
    @BindView(R.id.rl2)
    RelativeLayout rl2;
    @BindView(R.id.rl3)
    RelativeLayout rl3;
    @BindView(R.id.rl4)
    RelativeLayout rl4;
    @BindView(R.id.rl5)
    RelativeLayout rl5;

    @BindView(R.id.rl7)
    RelativeLayout rl7;

    private String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_jiu_bao);
        ButterKnife.bind(this);
        CommonUtil.setStatusBarTransparent(this);
        Intent intent = getIntent();
        userid = intent.getStringExtra("userid");
    }

    @OnClick({R.id.back, R.id.rl1, R.id.rl2, R.id.rl3, R.id.rl4, R.id.rl5,  R.id.rl7,R.id.rl8,R.id.rl9,R.id.r20,R.id.r21,R.id.r22})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.rl1:
                startActivity(new Intent(this, PrivateJiuBaoActivity.class)
                        .putExtra("type", "垃圾广告、售卖假货等")
                        .putExtra("userid", userid));
                break;
            case R.id.rl2:
                startActivity(new Intent(this, PrivateJiuBaoActivity.class).putExtra("type", "色情低俗").putExtra("userid", userid));
                break;
            case R.id.rl3:
                startActivity(new Intent(this, PrivateJiuBaoActivity.class).putExtra("type", "违法犯罪").putExtra("userid", userid));
                break;
            case R.id.rl4:
                startActivity(new Intent(this, PrivateJiuBaoActivity.class).putExtra("type", "政治敏感").putExtra("userid", userid));
                break;
            case R.id.rl5:
                startActivity(new Intent(this, PrivateJiuBaoActivity.class).putExtra("type", "造谣传谣、涉嫌欺诈").putExtra("userid", userid));
                break;
//            case R.id.rl6:
//                startActivity(new Intent(this, PrivateJiuBaoActivity.class).putExtra("type", "造谣传谣，涉嫌欺诈").putExtra("userid", userid));
//                break;
            case R.id.rl7:
                startActivity(new Intent(this, PrivateJiuBaoActivity.class).putExtra("type", "冒充官方").putExtra("userid", userid));
                break;
            case R.id.rl8:
                startActivity(new Intent(this, PrivateJiuBaoActivity.class).putExtra("type", "头像、昵称、签名违规").putExtra("userid", userid));
                break;
            case R.id.rl9:
                startActivity(new Intent(this, PrivateJiuBaoActivity.class).putExtra("type", "盗用TA人作品").putExtra("userid", userid));
                break;
            case R.id.r20:
                startActivity(new Intent(this, PrivateJiuBaoActivity.class).putExtra("type", "侵犯权益").putExtra("userid", userid));
                break;
            case R.id.r21:
                startActivity(new Intent(this, PrivateJiuBaoActivity.class).putExtra("type", "疑似自我伤害").putExtra("userid", userid));
                break;
            case R.id.r22:
                startActivity(new Intent(this, PrivateJiuBaoActivity.class).putExtra("type", "侮辱谩骂").putExtra("userid", userid));
                break;
        }
    }
}
