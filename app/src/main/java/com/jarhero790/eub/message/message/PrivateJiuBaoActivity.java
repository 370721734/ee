package com.jarhero790.eub.message.message;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jarhero790.eub.R;
import com.jarhero790.eub.message.net.RetrofitManager;
import com.jarhero790.eub.utils.AppUtils;
import com.jarhero790.eub.utils.CommonUtil;
import com.jarhero790.eub.utils.SharePreferenceUtil;

import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrivateJiuBaoActivity extends AppCompatActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.submit)
    TextView submit;
    @BindView(R.id.et_title)
    EditText etTitle;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.tu)
    LinearLayout tu;

    private String userid;
    private String type;
    private String content;
    private String img1;
    private String img2;
    private String img3;
    private String img4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_jiu_bao);
        ButterKnife.bind(this);
        CommonUtil.setStatusBarTransparent(this);
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        userid = intent.getStringExtra("userid");
        etTitle.setText(type);

    }

    @OnClick({R.id.back, R.id.submit, R.id.tu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.submit:
                type = etTitle.getText().toString();
                if (TextUtils.isEmpty(type)) {
                    Toast.makeText(this, "举报理由不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                content = etContent.getText().toString();
                if (TextUtils.isEmpty(content)) {
                    Toast.makeText(this, "举报内容不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }


                RetrofitManager.getInstance().getDataServer().report(SharePreferenceUtil.getToken(AppUtils.getContext()), userid, type, content, img1, img2, img3, img4)
                        .enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.isSuccessful()) {
                                    try {
                                        String json = response.body().string();

                                        Log.e("-----------", json);
                                        JSONObject object = new JSONObject(json);
                                        int code = object.optInt("code");
                                        String msg = object.optString("msg");
                                        if (code == 200) {
                                            Toast.makeText(PrivateJiuBaoActivity.this, msg, Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(PrivateJiuBaoActivity.this, msg, Toast.LENGTH_SHORT).show();
                                        }

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {

                            }
                        });


                break;
            case R.id.tu:
                break;
        }
    }
}
