package com.jarhero790.eub.message.my;

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


    List<Integer> list=new ArrayList<>();
    Calendar calendar;
    CalendarAdapter calendarAdapter;
    int year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qian_dao);
        ButterKnife.bind(this);
        CommonUtil.setStatusBarTransparent(this);
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

    private void initDate() {
        calendar=Calendar.getInstance();
        year=calendar.get(Calendar.YEAR);

        month=calendar.get(Calendar.MONTH);

        day=calendar.get(Calendar.DAY_OF_MONTH);

        int currentMonthLastDay = getCurrentMonthLastDay();
        Log.e("----------m",""+currentMonthLastDay);

        for (int i=0;i<9-(currentMonthLastDay-27);i++){
            list.add(0);
        }
        for (int i = 0; i < currentMonthLastDay; i++) {
            list.add(i+1);
        }


        calendarAdapter=new CalendarAdapter(this,list);
        gv.setAdapter(calendarAdapter);
    }

    /**
     * 取得当月天数
     * */
    public  int getCurrentMonthLastDay()
    {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.DATE, 1);//把日期设置为当月第一天
        a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }


    @OnClick({R.id.ivback, R.id.tv_qiandao})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivback:
                finish();
                break;
            case R.id.tv_qiandao:
                qiandao();

                break;
        }
    }

    private void qiandao() {
        RetrofitManager.getInstance().getDataServer().qiandao(SharePreferenceUtil.getToken(AppUtils.getContext()))
                .enqueue(new retrofit2.Callback<ResponseBody>() {
                    @Override
                    public void onResponse(retrofit2.Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            try {
                                String json=response.body().string();
                                org.json.JSONObject object=new org.json.JSONObject(json);
                                int code=object.optInt("code");
                                String msg=object.optString("msg");
                                String data=object.optString("data");
                                if (code==200){
                                    tvJin.setText("我的金币:"+data);
                                    tvQiandao.setText("已签到");//如何保存状态





                                    //set image
                                    calendarAdapter.setNumber(day);
                                    Log.e("-----------1",""+day);
                                    calendarAdapter.notifyDataSetChanged();
                                }else {
                                    tvQiandao.setText("已签到");
                                    tvJin.setText("我的金币:"+data);

                                    //set image
                                    calendarAdapter.setNumber(day);
                                    Log.e("-----------1",""+day);
                                    calendarAdapter.notifyDataSetChanged();
                                    Toast.makeText(QianDaoActivity.this,msg,Toast.LENGTH_SHORT).show();
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
                Log.e("注册异常", e.getMessage());
                Toast.makeText(QianDaoActivity.this, "异常" + e.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                try {
                    Log.e("-----1:", result);//{"code":200,"data":{"msgId":"19081613443425770"},"msg":"\u77ed\u4fe1\u5df2\u53d1\u9001\uff0c\u8bf7\u6ce8\u610f\u67e5\u6536"}
                    JSONObject object = JSONObject.parseObject(result);
                    PinLenBean bean = JSON.toJavaObject(object, PinLenBean.class);

                    Log.e("---------2", bean.getCode() + " " + bean.getData().getTask_type().get(0).getId());

                    if (bean.getCode() == 200) {
//                        qianDaoAdapter = new QianDaoAdapter(QianDaoActivity.this, bean.getData().getTask_type(), myclick);


                        if (bean.getData().getTask_type().size()==4){
                            String t1=""+bean.getData().getTask_type().get(0).getMoney()+"";
                            String t2=""+bean.getData().getTask_type().get(1).getMoney()+"";
                            String t3=""+bean.getData().getTask_type().get(2).getMoney()+"";
                            String t4=""+bean.getData().getTask_type().get(3).getMoney()+"";
                            Log.e("---------",t1+t2+t3+t4);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    tvZhanli.setText(t1);
                                    tvZhanli2.setText(t2);
                                    tvZhanli3.setText(t3);
                                    tvZhanli4.setText(t4);
                                    Log.e("------------44",bean.getData().getShare()+"  "+bean.getData().getComment_task()+"  "+bean.getData().getTask_type().get(3).getMoney());
                                    tvviewText.setText(bean.getData().getComment()==1?"1":"0");
                                    tvviewText2.setText(bean.getData().getShare()==1?"1":"0");
                                    tvviewText3.setText(bean.getData().getVideo()==1?"1":"0");
                                    tvviewText4.setText(bean.getData().getGive()==1?"1":"0");

                                    Log.e("------------44",bean.getData().getShare()+"  "+bean.getData().getComment_task()+"  "+bean.getData().getTask_type().get(3).getMoney());

                                    tvLin.setText(bean.getData().getComment_task()==1?"已领取":"领取");
                                    tvLin2.setText(bean.getData().getShare_task()==1?"已领取":"领取");
                                    tvLin3.setText(bean.getData().getVideo_task()==1?"已领取":"领取");
                                    tvLin4.setText(bean.getData().getGive_task()==1?"已领取":"领取");

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
        if (bean!=null){//有了
            Log.e("------------d", bean.getCode() + " "+bean.getData().getTask_type().get(0).getId());
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
