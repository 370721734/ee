package com.jarhero790.eub.message.my;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectChangeListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.jarhero790.eub.R;
import com.jarhero790.eub.utils.CommonUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends AppCompatActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.save)
    TextView save;
    @BindView(R.id.iv_userimage)
    ImageView ivUserimage;
    @BindView(R.id.iv_pho)
    ImageView ivPho;
    @BindView(R.id.tv_name)
    EditText tvName;
    @BindView(R.id.iv_edit_name)
    ImageView ivEditName;
    @BindView(R.id.tv_sign)
    EditText tvSign;
    @BindView(R.id.iv_edit_sign)
    ImageView ivEditSign;
    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.iv_edit_address)
    ImageView ivEditAddress;
    @BindView(R.id.tv_exit)
    TextView tvExit;
    @BindView(R.id.tv_xieyi)
    TextView tvXieyi;
    @BindView(R.id.man)
    RadioButton man;
    @BindView(R.id.woman)
    RadioButton woman;
    @BindView(R.id.rgroup)
    RadioGroup rgroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        CommonUtil.setStatusBarTransparent(SettingActivity.this);
        Intent intent = getIntent();

        String name = intent.getStringExtra("name");
        String sign = intent.getStringExtra("sign");
        String sex = intent.getStringExtra("sex");
        Log.e("--------", name + sign + sex);

        if (name!=null)
        tvName.setText(name);

        if (sign!=null)
        tvSign.setText(sign);


        if (sex!=null){
            if (sex.equals("1")) {
                rgroup.check(R.id.man);
            }else if (sex.equals("2")){
                rgroup.check(R.id.woman);
            }else {
                rgroup.check(R.id.man);
            }
        }

    }

    @OnClick({R.id.back, R.id.save, R.id.iv_pho, R.id.iv_edit_name, R.id.iv_edit_sign, R.id.iv_edit_address, R.id.tv_exit, R.id.tv_xieyi})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.save:
                break;
            case R.id.iv_pho:
                break;
            case R.id.iv_edit_name:
                break;
            case R.id.iv_edit_sign:
                break;
            case R.id.iv_edit_address:

                OptionsPickerView pickerView=new OptionsPickerBuilder(SettingActivity.this, new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
//                        String tx
                    }
                }).setOptionsSelectChangeListener(new OnOptionsSelectChangeListener() {
                    @Override
                    public void onOptionsSelectChanged(int options1, int options2, int options3) {
                        String str = "options1: " + options1 + "options2: " + options2 + "options3: " + options3;
                        Toast.makeText(SettingActivity.this, str, Toast.LENGTH_SHORT).show();
                    }
                })
                        .setSubmitText("确定")
                        .setCancelText("取消")//取消按钮文字
                        .setTitleText("城市选择")//标题
                        .setSubCalSize(18)//确定和取消文字大小
                        .setTitleSize(20)//标题文字大小
                        .setTitleColor(Color.BLACK)//标题文字颜色
                        .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                        .setCancelColor(Color.BLUE)//取消按钮文字颜色
                        .setTitleBgColor(0xFF333333)//标题背景颜色 Night mode
                        .setBgColor(0xFF000000)//滚轮背景颜色 Night mode
                        .setContentTextSize(18)//滚轮文字大小
//                        .setLinkage(false)//设置是否联动，默认true
                        .setLabels("省", "市", "区")//设置选择的三级单位
                        .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                        .setCyclic(false, false, false)//循环与否
                        .setSelectOptions(1, 1, 1)  //设置默认选中项
                        .setOutSideCancelable(false)//点击外部dismiss default true
                        .isDialog(true)//是否显示为对话框样式
                        .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                        .build();

//                pickerView.setPicker();
                break;
            case R.id.tv_exit:
                break;
            case R.id.tv_xieyi:
                startActivity(new Intent(SettingActivity.this,XieYiActivity.class));
                break;
        }
    }
}
