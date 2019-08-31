package com.jarhero790.eub.message.my;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jarhero790.eub.R;
import com.jarhero790.eub.api.Api;
import com.jarhero790.eub.message.adapter.CalendarAdapter;
import com.jarhero790.eub.message.adapter.QianDaoAdapter;
import com.jarhero790.eub.message.bean.PinLenBean;
import com.jarhero790.eub.message.contract.QianDaoContract;
import com.jarhero790.eub.message.model.QianModel;
import com.jarhero790.eub.message.net.RetrofitManager;
import com.jarhero790.eub.message.presenter.QianPr;
import com.jarhero790.eub.utils.AppUtils;
import com.jarhero790.eub.utils.CommonUtil;
import com.jarhero790.eub.utils.SharePreferenceUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

//用户任务中心
public class QianDaoActivity extends AppCompatActivity implements QianDaoContract.View {

    @BindView(R.id.ivback)
    ImageView ivback;
    @BindView(R.id.tv_jin)
    TextView tvJin;
    @BindView(R.id.tv_qiandao)
    TextView tvQiandao;
    @BindView(R.id.time)
    GridView gv;
    @BindView(R.id.iv_one)
    ImageView ivOne;
    @BindView(R.id.iv_two)
    ImageView ivTwo;
    @BindView(R.id.iv_three)
    ImageView ivThree;
    @BindView(R.id.iv_four)
    ImageView ivFour;
    @BindView(R.id.iv_five)
    ImageView ivFive;
    @BindView(R.id.iv_six)
    ImageView ivSix;
    @BindView(R.id.iv_serven)
    ImageView ivServen;
    @BindView(R.id.recycler)
    RecyclerView recycler;

    QianDaoAdapter qianDaoAdapter;
    @BindView(R.id.tv_pinglun_text)
    TextView tvPinglunText;
    @BindView(R.id.iv_icon)
    ImageView ivIcon;
    @BindView(R.id.tv_zhanli)
    TextView tvZhanli;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.tvview_text)
    TextView tvviewText;
    @BindView(R.id.tvview_text1)
    TextView tvviewText1;
    @BindView(R.id.tv_lin)
    TextView tvLin;
    @BindView(R.id.tv_pinglun_text2)
    TextView tvPinglunText2;
    @BindView(R.id.iv_icon2)
    ImageView ivIcon2;
    @BindView(R.id.tv_zhanli2)
    TextView tvZhanli2;
    @BindView(R.id.view2)
    View view2;
    @BindView(R.id.tvview_text2)
    TextView tvviewText2;
    @BindView(R.id.tvview_text12)
    TextView tvviewText12;
    @BindView(R.id.tv_lin2)
    TextView tvLin2;
    @BindView(R.id.tv_pinglun_text3)
    TextView tvPinglunText3;
    @BindView(R.id.iv_icon3)
    ImageView ivIcon3;
    @BindView(R.id.tv_zhanli3)
    TextView tvZhanli3;
    @BindView(R.id.view3)
    View view3;
    @BindView(R.id.tvview_text3)
    TextView tvviewText3;
    @BindView(R.id.tvview_text13)
    TextView tvviewText13;
    @BindView(R.id.tv_lin3)
    TextView tvLin3;
    @BindView(R.id.tv_pinglun_text4)
    TextView tvPinglunText4;
    @BindView(R.id.iv_icon4)
    ImageView ivIcon4;
    @BindView(R.id.tv_zhanli4)
    TextView tvZhanli4;
    @BindView(R.id.view4)
    View view4;
    @BindView(R.id.tvview_text4)
    TextView tvviewText4;
    @BindView(R.id.tvview_text14)
    TextView tvviewText14;
    @BindView(R.id.tv_lin4)
    TextView tvLin4;


    List<Integer> list = new ArrayList<>();//当前月的数
    Calendar calendar;
    CalendarAdapter calendarAdapter;
    int year, month, day;

    List<Integer> signdatelist = new ArrayList<>();//已签到日期

    private int type_id1;//
    private int type_id2;//
    private int type_id3;//
    private int type_id4;//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qian_dao);
        ButterKnife.bind(this);
        CommonUtil.setStatusBarTransparent(this);
        calendar = Calendar.getInstance();
//        year=calendar.get(Calendar.YEAR);
//
//        month=calendar.get(Calendar.MONTH);

        day = calendar.get(Calendar.DAY_OF_MONTH);
//        Log.e("-----day-",year+"  "+month+"  "+day);
        Intent intent = getIntent();
        tvJin.setText("我的金币:" + intent.getStringExtra("money"));
        String signtime = intent.getStringExtra("signtime");
        if (signtime == null || signtime.length() == 0 || signtime.equals("null")) {
            tvQiandao.setText("签到");
            Log.e("----------11", "time null");
        } else {
            //判断是否是今天才行
            if (signtime.length() > 9) {
                if (signtime.substring(8, 10).equals(day + "")) {
                    tvQiandao.setText("已签到");//如何保存状态
                    Log.e("----------22", signtime);
                } else {
                    tvQiandao.setText("签到");
                    Log.e("----------33", signtime);
                }
            }

        }

        //刷新
        initUi();
        pinlen();
//        QianPr pr = QianPr.newInstance();
//        pr.getpinle();
//        QianPr.newInstance().getpinle();


        //有了
//        QianPr pr=new QianPr(this);
//        pr.getpinlen();

        //获取当前的年月日后，再判断当前的天数？
        initDate();

    }

    private void initUi() {

    }

    private void initDate() {


        int currentMonthLastDay = getCurrentMonthLastDay();
        Log.e("----------m", "" + currentMonthLastDay);

        for (int i = 0; i < 9 - (currentMonthLastDay - 27); i++) {
            list.add(0);
        }
        for (int i = 0; i < currentMonthLastDay; i++) {
            list.add(i + 1);
        }


        calendarAdapter = new CalendarAdapter(this, list);
        gv.setAdapter(calendarAdapter);
    }

    /**
     * 取得当月天数
     */
    public int getCurrentMonthLastDay() {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.DATE, 1);//把日期设置为当月第一天
        a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }


    @OnClick({R.id.ivback, R.id.tv_qiandao, R.id.tv_lin, R.id.tv_lin2, R.id.tv_lin3, R.id.tv_lin4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivback:
                finish();
                break;
            case R.id.tv_qiandao:
                qiandao();
                break;
            case R.id.tv_lin:
                linindex(type_id1);
                break;
            case R.id.tv_lin2:
                linindex(type_id2);
                break;
            case R.id.tv_lin3:
                linindex(type_id3);
                break;
            case R.id.tv_lin4:
                linindex(type_id4);
                break;


        }
    }

    private void linindex(int type_id) {
        RetrofitManager.getInstance().getDataServer().getreward(SharePreferenceUtil.getToken(AppUtils.getContext()), type_id)
                .enqueue(new retrofit2.Callback<ResponseBody>() {
                    @Override
                    public void onResponse(retrofit2.Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            try {
                                String json = response.body().string();
                                org.json.JSONObject obj = new org.json.JSONObject(json);
                                int code = obj.optInt("code");
                                String msg = obj.optString("msg");
                                String data = obj.optString("data");
                                if (code == 200) {
                                    Log.e("---------data=>", data);
                                    if (type_id == type_id1) {
                                        tvLin.setText("已领取");
                                    }
                                    if (type_id == type_id2) {
                                        tvLin2.setText("已领取");
                                    }
                                    if (type_id == type_id3) {
                                        tvLin3.setText("已领取");
                                    }
                                    if (type_id == type_id4) {
                                        tvLin4.setText("已领取");
                                    }

                                    Toast.makeText(QianDaoActivity.this, msg, Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(QianDaoActivity.this, msg, Toast.LENGTH_SHORT).show();
                                }


                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {

                    }
                });
    }

    private void qiandao() {
        RetrofitManager.getInstance().getDataServer().qiandao(SharePreferenceUtil.getToken(AppUtils.getContext()))
                .enqueue(new retrofit2.Callback<ResponseBody>() {
                    @Override
                    public void onResponse(retrofit2.Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            try {
                                String json = response.body().string();
                                org.json.JSONObject object = new org.json.JSONObject(json);
                                int code = object.optInt("code");
                                String msg = object.optString("msg");
                                String data = object.optString("data");
                                if (code == 200) {
//                                    if (!data.equals("null") && data.length()==0){
//
//                                    }
                                    if (data == null || data.length() == 0 || data.equals("") || data.equals("null")) {
                                        Log.e("-----------", "kou");
                                    } else {
                                        Log.e("-----------", "yes");
                                        tvJin.setText("我的金币:" + data);
                                    }

                                    tvQiandao.setText("已签到");//如何保存状态
                                    pinlen();


                                    //set image
                                    calendarAdapter.setNumber(day);
                                    Log.e("-----------1", "" + day);
                                    calendarAdapter.notifyDataSetChanged();
                                    Toast.makeText(QianDaoActivity.this, msg, Toast.LENGTH_SHORT).show();
                                } else {
                                    tvQiandao.setText("已签到");
                                    if (data == null || data.length() == 0 || data.equals("") || data.equals("null")) {
                                        Log.e("-----------", "kou");
                                    } else {
                                        Log.e("-----------", "yes");
                                        tvJin.setText("我的金币:" + data);
                                    }

                                    //set image
                                    calendarAdapter.setNumber(day);
                                    Log.e("-----------1", "" + day);
                                    calendarAdapter.notifyDataSetChanged();
                                    Toast.makeText(QianDaoActivity.this, msg, Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {

                    }
                });
    }


//    @Override
//    public void getpin(PinLenBean bean) {
//
//
//        Log.e("-----00",bean.getCode()+" "+bean.getData().getTask_type().get(0).getId());
//    }


    //个人签到页
    public void pinlen() {
        //通过RequestBody.create 创建requestBody对象
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("token", SharePreferenceUtil.getToken(AppUtils.getContext()))
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(Api.HOST + "web/index/sign_in").post(requestBody).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
//                Log.e("注册异常", e.getMessage());
//                Toast.makeText(QianDaoActivity.this, "异常" + e.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                try {
//                    Log.e("-----1:", result);//{"code":200,"data":{"msgId":"19081613443425770"},"msg":"\u77ed\u4fe1\u5df2\u53d1\u9001\uff0c\u8bf7\u6ce8\u610f\u67e5\u6536"}
                    JSONObject object = JSONObject.parseObject(result);
                    PinLenBean bean = JSON.toJavaObject(object, PinLenBean.class);
//                    Log.e("---------2", bean.getCode() + " " + bean.getData().getTask_type().get(0).getId());
                    if (bean.getCode() == 200) {
//                        qianDaoAdapter = new QianDaoAdapter(QianDaoActivity.this, bean.getData().getTask_type(), myclick);
                        //设置日历
                        //签到的日期
                        List<String> signlist = bean.getData().getSign();
                        for (int i = 0; i < signlist.size(); i++) {
//                            Log.e("----------si", signlist.get(i)+"  "+signlist.get(i).substring(8,10));
                            if (signlist.get(i).length() > 9) {
                                signdatelist.add(Integer.parseInt(signlist.get(i).substring(8, 10)));
                            }
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (signdatelist.size() > 0) {
//                                    Log.e("---------1", signdatelist.get(0) + "");
                                    calendarAdapter.setNubetwo(signdatelist);
                                    calendarAdapter.notifyDataSetChanged();
                                }
                            }
                        });


//                        else {
//                            Log.e("---------2",signdatelist.size()+"");
//                        }

                        //连续签到      ok
                        org.json.JSONObject obj = new org.json.JSONObject(result);
                        int code = obj.optInt("code");
                        if (code == 200) {
                            org.json.JSONObject data = obj.optJSONObject("data");
                            org.json.JSONObject contin = data.optJSONObject("continuity_sign");
                            String h1 = contin.optString("1t");
                            String h2 = contin.optString("2t");
                            String h3 = contin.optString("3t");
                            String h4 = contin.optString("4t");
                            String h5 = contin.optString("5t");
                            String h6 = contin.optString("6t");
                            String h7 = contin.optString("7t");
//                            Log.e("---------h:", h1 + " " + h2 + " " + h3 + " " + h4 + " " + h5 + " " + h6 + " " + h7);// "continuity_sign":{"1t":"2019-08-19 13:25:41","2t":null,"3t":null,"4t":null,"5t":null,"6t":null,"7t":null}
                            //有了
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (h7 != null && h7.length() > 10 && h6 != null && h6.length() > 10 && h5 != null && h5.length() > 10 && h4 != null && h4.length() > 10 && h3 != null && h3.length() > 10 && h2 != null && h2.length() > 10 && h1 != null && h1.length() > 10) {
                                        ivServen.setImageDrawable(getResources().getDrawable(R.mipmap.ok_icon));
                                        ivSix.setImageDrawable(getResources().getDrawable(R.mipmap.ok_icon));
                                        ivFive.setImageDrawable(getResources().getDrawable(R.mipmap.ok_icon));
                                        ivFour.setImageDrawable(getResources().getDrawable(R.mipmap.ok_icon));
                                        ivThree.setImageDrawable(getResources().getDrawable(R.mipmap.ok_icon));
                                        ivTwo.setImageDrawable(getResources().getDrawable(R.mipmap.ok_icon));
                                        ivOne.setImageDrawable(getResources().getDrawable(R.mipmap.ok_icon));
                                    } else if (h6 != null && h6.length() > 10 && h5 != null && h5.length() > 10 && h4 != null && h4.length() > 10 && h3 != null && h3.length() > 10 && h2 != null && h2.length() > 10 && h1 != null && h1.length() > 10) {
                                        ivSix.setImageDrawable(getResources().getDrawable(R.mipmap.ok_icon));
                                        ivFive.setImageDrawable(getResources().getDrawable(R.mipmap.ok_icon));
                                        ivFour.setImageDrawable(getResources().getDrawable(R.mipmap.ok_icon));
                                        ivThree.setImageDrawable(getResources().getDrawable(R.mipmap.ok_icon));
                                        ivTwo.setImageDrawable(getResources().getDrawable(R.mipmap.ok_icon));
                                        ivOne.setImageDrawable(getResources().getDrawable(R.mipmap.ok_icon));
                                    } else if (h5 != null && h5.length() > 10 && h4 != null && h4.length() > 10 && h3 != null && h3.length() > 10 && h2 != null && h2.length() > 10 && h1 != null && h1.length() > 10) {
                                        ivFive.setImageDrawable(getResources().getDrawable(R.mipmap.ok_icon));
                                        ivFour.setImageDrawable(getResources().getDrawable(R.mipmap.ok_icon));
                                        ivThree.setImageDrawable(getResources().getDrawable(R.mipmap.ok_icon));
                                        ivTwo.setImageDrawable(getResources().getDrawable(R.mipmap.ok_icon));
                                        ivOne.setImageDrawable(getResources().getDrawable(R.mipmap.ok_icon));
                                    } else if (h4 != null && h4.length() > 10 && h3 != null && h3.length() > 10 && h2 != null && h2.length() > 10 && h1 != null && h1.length() > 10) {
                                        ivFour.setImageDrawable(getResources().getDrawable(R.mipmap.ok_icon));
                                        ivThree.setImageDrawable(getResources().getDrawable(R.mipmap.ok_icon));
                                        ivTwo.setImageDrawable(getResources().getDrawable(R.mipmap.ok_icon));
                                        ivOne.setImageDrawable(getResources().getDrawable(R.mipmap.ok_icon));
                                    } else if (h3 != null && h3.length() > 10 && h2 != null && h2.length() > 10 && h1 != null && h1.length() > 10) {
                                        ivThree.setImageDrawable(getResources().getDrawable(R.mipmap.ok_icon));
                                        ivTwo.setImageDrawable(getResources().getDrawable(R.mipmap.ok_icon));
                                        ivOne.setImageDrawable(getResources().getDrawable(R.mipmap.ok_icon));
                                    } else if (h2 != null && h2.length() > 10 && h1 != null && h1.length() > 10) {
                                        ivTwo.setImageDrawable(getResources().getDrawable(R.mipmap.ok_icon));
                                        ivOne.setImageDrawable(getResources().getDrawable(R.mipmap.ok_icon));
                                    } else if (h1 != null && h1.length() > 10) {
                                        ivOne.setImageDrawable(getResources().getDrawable(R.mipmap.ok_icon));
                                    }
                                }
                            });


                        }


                        if (bean.getData().getTask_type().size() == 4) {
                            String t1 = "" + bean.getData().getTask_type().get(0).getMoney() + "";
                            String t2 = "" + bean.getData().getTask_type().get(1).getMoney() + "";
                            String t3 = "" + bean.getData().getTask_type().get(2).getMoney() + "";
                            String t4 = "" + bean.getData().getTask_type().get(3).getMoney() + "";

                            type_id1 = bean.getData().getTask_type().get(0).getType_id();
                            type_id2 = bean.getData().getTask_type().get(1).getType_id();
                            type_id3 = bean.getData().getTask_type().get(2).getType_id();
                            type_id4 = bean.getData().getTask_type().get(3).getType_id();
                            Log.e("--------ty-", type_id1 + "  " + type_id4);


                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    tvZhanli.setText(t1);
                                    tvZhanli2.setText(t2);
                                    tvZhanli3.setText(t3);
                                    tvZhanli4.setText(t4);
//                                    Log.e("------------44", bean.getData().getShare() + "  " + bean.getData().getComment_task() + "  " + bean.getData().getTask_type().get(3).getMoney());
                                    tvviewText.setText(bean.getData().getComment() == 1 ? "1" : "0");
                                    tvviewText2.setText(bean.getData().getShare() == 1 ? "1" : "0");
                                    tvviewText3.setText(bean.getData().getVideo() == 1 ? "1" : "0");
                                    tvviewText4.setText(bean.getData().getGive() == 1 ? "1" : "0");

//                                    Log.e("------------44", bean.getData().getShare() + "  " + bean.getData().getComment_task() + "  " + bean.getData().getTask_type().get(3).getMoney());

                                    tvLin.setText(bean.getData().getComment_task() == 1 ? "已领取" : "领取");
                                    tvLin2.setText(bean.getData().getShare_task() == 1 ? "已领取" : "领取");
                                    tvLin3.setText(bean.getData().getVideo_task() == 1 ? "已领取" : "领取");
                                    tvLin4.setText(bean.getData().getGive_task() == 1 ? "已领取" : "领取");

                                }
                            });

                        }

                    }

//                    int code = jsonObject.optInt("code");
//                    String msg = (String) jsonObject.get("msg");
//                    Log.e("注册结果msg值", msg);
//                    if (code == 200) {
////                        JSONObject data = jsonObject.optJSONObject("data");
////                        msgid=data.optString("msgId");
////                                Log.e("----------2:",msgid);//ok
//
//                        Toast.makeText(QianDaoActivity.this, msg, Toast.LENGTH_LONG).show();
//                    } else {
//                        Toast.makeText(QianDaoActivity.this, msg, Toast.LENGTH_LONG).show();
//                    }
                } catch (Exception e) {

                }

//                Log.e("注册结果", result);
                //Toast.makeText(RegisterByUsernameActivity.this,response.body().string(),Toast.LENGTH_LONG).show();
            }
        });
    }


    //因为数据写固定样式，所以不用适配，并相关不要
//    QianDaoAdapter.Myclick myclick = new QianDaoAdapter.Myclick() {
//        @Override
//        public void myClick(int position, View view) {
//            Log.e("------------c", position + "");
//        }
//    };

    @Override
    public void getpinlen(PinLenBean bean) {
        if (bean != null) {//有了
            Log.e("------------d", bean.getCode() + " " + bean.getData().getTask_type().get(0).getId());
        }

    }

    @Override
    public void onProgress() {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}
