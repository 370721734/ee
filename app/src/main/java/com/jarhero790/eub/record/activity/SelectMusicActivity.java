package com.jarhero790.eub.record.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jarhero790.eub.R;
import com.jarhero790.eub.bean.Video;
import com.jarhero790.eub.message.souye.AdViewPager;
import com.jarhero790.eub.record.fragment.MusicSingFragment;
import com.jarhero790.eub.record.fragment.MusicZuangFragment;
import com.jarhero790.eub.record.view.NoScrollViewPager;
import com.jarhero790.eub.utils.CommonUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectMusicActivity extends AppCompatActivity {

    @BindView(R.id.m_ad_pager)
    AdViewPager mAdPager;
    @BindView(R.id.layout_ad_indicator)
    LinearLayout layoutAdIndicator;
    @BindView(R.id.iv_bot1)
    View ivBot1;
    @BindView(R.id.iv_bot2)
    View ivBot2;
    @BindView(R.id.vp)
    NoScrollViewPager vp;

    //广告的上一个显示下标
    private int lastShowIndex;
    List<Video> lunBoTuList = new ArrayList<>();//轮播图

    int[] tu = new int[]{R.mipmap.music_1, R.mipmap.music_2, R.mipmap.music_3, R.mipmap.music_4, R.mipmap.music_5, R.mipmap.music_6};

    private String[] titles = {"专", "单"};
    private VpAdapter adapter;
    private MusicZuangFragment musicZuangFragment;
    private MusicSingFragment musicSingFragment;

    private FragmentManager manager;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_music);
        ButterKnife.bind(this);
        CommonUtil.setStatusBarTransparent(this);
        adapter = new VpAdapter(getSupportFragmentManager());
        vp.setAdapter(adapter);
        vp.setScroll(false);


        //轮播图

        initAd();

//        manager=getSupportFragmentManager();
//        transaction=manager.beginTransaction();
//
//
//        musicZuangFragment=new MusicZuangFragment();
//        musicZuangFragment.setMusicString(new MusicZuangFragment.MusicString() {
//            @Override
//            public void Clicklinener(int position, String url) {
//                Log.e("-------------","selectmusic"+position+"  "+url);
//            }
//        });
    }


    private void initAd() {
        mAdPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position != lastShowIndex) {
                    if (layoutAdIndicator == null) {
                        return;
                    }
                    layoutAdIndicator.getChildAt(position).setSelected(true);
                    layoutAdIndicator.getChildAt(lastShowIndex).setSelected(false);
                    lastShowIndex = position;

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
//        loadAd();
        loadTu();

    }

    private void loadTu() {
        layoutAdIndicator.removeAllViews();
        for (int i = 0; i < tu.length; i++) {
//            ImageView iv = new ImageView(this);
            TextView tv=new TextView(SelectMusicActivity.this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
            tv.setBackgroundDrawable(getResources().getDrawable(R.drawable.ad_indicator_drawable));
            lp.setMargins(5, 10, 5, 10);
            tv.setLayoutParams(lp);
            layoutAdIndicator.addView(tv);
        }
        mAdPager.setAdapter(new LunBoTuAdapter());
        mAdPager.startLoop();
    }

    @OnClick({R.id.back, R.id.ll1, R.id.ll2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.ll1:
                ivBot1.setVisibility(View.VISIBLE);
                ivBot2.setVisibility(View.INVISIBLE);
                vp.setCurrentItem(0);
                break;
            case R.id.ll2:
                ivBot1.setVisibility(View.INVISIBLE);
                ivBot2.setVisibility(View.VISIBLE);
                vp.setCurrentItem(1);
                break;
        }
    }


    public class VpAdapter extends FragmentPagerAdapter {

        public VpAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    if (musicZuangFragment == null) {
                        musicZuangFragment = new MusicZuangFragment();


                        musicZuangFragment.setMusicString(new MusicZuangFragment.MusicString() {
                            @Override
                            public void Clicklinener(int position, String url,String mid) {
                                Log.e("-------------1","selectmusic"+position+"  "+url);

                                Intent intent=getIntent();
                                intent.putExtra("music",url);
                                intent.putExtra("mid",mid);
                                setResult(RESULT_OK,intent);
                                finish();
//                                MediaPlayUtil.getInstance().start(url);
                            }
                        });

                        return musicZuangFragment;
                    }
                    break;
                case 1:
                    if (musicSingFragment == null) {
                        musicSingFragment = new MusicSingFragment();

                        musicSingFragment.setMusicString(new MusicSingFragment.MusicString() {
                            @Override
                            public void Clicklinener(int position, String url,String mid) {
//                                Log.e("-------------1","selectmusic"+position+"  "+url);

                                Intent intent=getIntent();
                                intent.putExtra("music",url);
                                intent.putExtra("mid",mid);
                                setResult(RESULT_OK,intent);
                                finish();
                            }
                        });
                        return musicSingFragment;
                    }
                    break;

                default:
                    return null;
            }

            return null;
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }


//    private void loadAd() {
//        RetrofitManager.getInstance().getDataServer().getlist_lunbo2("1").enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                if (response.isSuccessful()) {
//                    String json = null;
//                    try {
//                        json = response.body().string();
////                        Log.e("ad：", json);
//                        JSONObject object = new JSONObject(json);
//                        String success = object.optString("success");
//
//                        lunBoTuList.clear();
//                        if (success.equals("true")) {
//                            JSONArray jsonArray = object.optJSONArray("rows");
//                            for (int i = 0; i < jsonArray.length(); i++) {
//                                JSONObject itemobj = jsonArray.optJSONObject(i);
//                                String url = itemobj.optString("Url");
//                                String href = itemobj.optString("Href");
//                                Log.e("adfd", url + "," + href);
//                                lunBoTuList.add(new LunBoTu(url, href));
//                            }
////                            Log.e("adfs", "," + lunBoTuList.size());
//                            layoutAdIndicator.removeAllViews();
//                            if (lunBoTuList != null && lunBoTuList.size() > 0) {
//                                String[] urls = new String[lunBoTuList.size()];
//                                int count = urls.length;
//                                int itemWidth = (screenWidth - (count + 1) * 10) / count;
////                                Log.e("-----as", count + "");
//
//                                for (int i = 0; i < count; i++) {
//                                    urls[i] = lunBoTuList.get(i).getUrl();
//                                    ImageView iv = new ImageView(getActivity());
//                                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
//                                    //圆点
////                                    iv.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.ad_indicator_drawable));
////                                    lp.setMargins(5, 10, 5, 10);
////
//                                    iv.setLayoutParams(lp);
//                                    layoutAdIndicator.addView(iv);
//                                }
//                                mAdPager.setAdapter(new LunBoAdapter(urls, lunBoTuList));
//                                mAdPager.startLoop();
//
//                            }
//
//                        }
//
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//            }
//        });
//
//
//    }

//    class LunBoAdapter extends ImagePagerAdapter {
//        private List<LunBoTu> mlist;
//
//        public LunBoAdapter(String[] images, List<LunBoTu> mlist) {
//            super(images, getActivity());
//            this.mlist = mlist;
//        }
//
//
//        @Override
//        protected void onItemShow(int position, View itemView) {
//            ((ImageView) itemView).setScaleType(ImageView.ScaleType.FIT_XY);
//            itemView.setTag(R.id.m_ad_pager, mlist.get(position).getHref());//放入的是网页地址
////            Log.e("aaaaaaaa", mlist.get(position).getUrl());//图片地址
//            itemView.setOnClickListener(onAdClick);
//        }
//    }


    class LunBoTuAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return tu.length;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            ImageView imageView = new ImageView(SelectMusicActivity.this);
            imageView.setImageResource(tu[position]);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((ImageView) object);
        }
    }


}