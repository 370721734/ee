package com.jarhero790.eub.message.my;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.google.gson.Gson;
import com.jarhero790.eub.R;
import com.jarhero790.eub.message.bean.JsonBean;
import com.jarhero790.eub.utils.CommonUtil;
import com.jarhero790.eub.utils.GetJsonDataUtil;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

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

    private List<JsonBean> options1Items = new ArrayList<>();

    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();

    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();

    private Thread thread;

    private static final int MSG_LOAD_DATA = 0x0001;

    private static final int MSG_LOAD_SUCCESS = 0x0002;

    private static final int MSG_LOAD_FAILED = 0x0003;


    private static boolean isLoaded = false;
    @SuppressLint("HandlerLeak")

    private Handler mHandler = new Handler() {

        public void handleMessage(Message msg) {

            switch (msg.what) {

                case MSG_LOAD_DATA:

                    if (thread == null) {//如果已创建就不再重新创建子线程了



                        Toast.makeText(SettingActivity.this, "Begin Parse Data", Toast.LENGTH_SHORT).show();

                        thread = new Thread(new Runnable() {

                            @Override

                            public void run() {

                                // 子线程中解析省市区数据

                                initJsonData();

                            }

                        });

                        thread.start();

                    }

                    break;



                case MSG_LOAD_SUCCESS:

                    Toast.makeText(SettingActivity.this, "Parse Succeed", Toast.LENGTH_SHORT).show();

                    isLoaded = true;

                    break;



                case MSG_LOAD_FAILED:

                    Toast.makeText(SettingActivity.this, "Parse Failed", Toast.LENGTH_SHORT).show();

                    break;

            }

        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        CommonUtil.setStatusBarTransparent(SettingActivity.this);
        mHandler.sendEmptyMessage(MSG_LOAD_DATA);
        Intent intent = getIntent();

        String name = intent.getStringExtra("name");
        String sign = intent.getStringExtra("sign");
        String sex = intent.getStringExtra("sex");
        Log.e("--------", name + sign + sex);

        if (name != null)
            tvName.setText(name);

        if (sign != null)
            tvSign.setText(sign);


        if (sex != null) {
            if (sex.equals("1")) {
                rgroup.check(R.id.man);
            } else if (sex.equals("2")) {
                rgroup.check(R.id.woman);
            } else {
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

                showPickerView();

                break;
            case R.id.tv_exit:
                break;
            case R.id.tv_xieyi:
                startActivity(new Intent(SettingActivity.this, XieYiActivity.class));
                break;
        }
    }

    private void showPickerView() {

        OptionsPickerView pvOptions = new OptionsPickerBuilder(SettingActivity.this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置

                String opt1tx = options1Items.size() > 0 ?

                        options1Items.get(options1).getPickerViewText() : "";


                String opt2tx = options2Items.size() > 0

                        && options2Items.get(options1).size() > 0 ?

                        options2Items.get(options1).get(options2) : "";


                String opt3tx = options2Items.size() > 0

                        && options3Items.get(options1).size() > 0

                        && options3Items.get(options1).get(options2).size() > 0 ?

                        options3Items.get(options1).get(options2).get(options3) : "";


                String tx = opt1tx + opt2tx + opt3tx;

                Toast.makeText(SettingActivity.this, tx, Toast.LENGTH_SHORT).show();
            }
        })

//                .setOptionsSelectChangeListener(new OnOptionsSelectChangeListener() {
//            @Override
//            public void onOptionsSelectChanged(int options1, int options2, int options3) {
//                String str = "options1: " + options1 + "options2: " + options2 + "options3: " + options3;
//                Toast.makeText(SettingActivity.this, str, Toast.LENGTH_SHORT).show();
//            }
//        })
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
//                .setLabels("省", "市", "区")//设置选择的三级单位
//                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
//                .setCyclic(false, false, false)//循环与否
//                .setSelectOptions(1, 1, 1)  //设置默认选中项
//                .setOutSideCancelable(false)//点击外部dismiss default true
//                .isDialog(true)//是否显示为对话框样式
//                .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                .build();

//         if (options1Items.size()>0 && options2Items.size()>0 && options3Items.size()>0){
//
//         }
        pvOptions.setPicker(options1Items, options2Items, options3Items);
        pvOptions.show();
    }



    private void initJsonData() {//解析数据



        /**

         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件

         * 关键逻辑在于循环体

         *

         * */

        String JsonData = new GetJsonDataUtil().getJson(this, "province.json");//获取assets目录下的json文件数据



        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体



        /**

         * 添加省份数据

         *

         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，

         * PickerView会通过getPickerViewText方法获取字符串显示出来。

         */

        options1Items = jsonBean;



        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份

            ArrayList<String> cityList = new ArrayList<>();//该省的城市列表（第二级）

            ArrayList<ArrayList<String>> province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）



            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市

                String cityName = jsonBean.get(i).getCityList().get(c).getName();

                cityList.add(cityName);//添加城市

                ArrayList<String> city_AreaList = new ArrayList<>();//该城市的所有地区列表



                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃

                /*if (jsonBean.get(i).getCityList().get(c).getArea() == null

                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {

                    city_AreaList.add("");

                } else {

                    city_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());

                }*/

                city_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());

                province_AreaList.add(city_AreaList);//添加该省所有地区数据

            }



            /**

             * 添加城市数据

             */

            options2Items.add(cityList);



            /**

             * 添加地区数据

             */

            options3Items.add(province_AreaList);

        }



        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);



    }





    public ArrayList<JsonBean> parseData(String result) {//Gson 解析

        ArrayList<JsonBean> detail = new ArrayList<>();

        try {

            JSONArray data = new JSONArray(result);

            Gson gson = new Gson();

            for (int i = 0; i < data.length(); i++) {

                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);

                detail.add(entity);

            }

        } catch (Exception e) {

            e.printStackTrace();

            mHandler.sendEmptyMessage(MSG_LOAD_FAILED);

        }

        return detail;

    }





    @Override

    protected void onDestroy() {

        super.onDestroy();

        if (mHandler != null) {

            mHandler.removeCallbacksAndMessages(null);

        }

    }
}
