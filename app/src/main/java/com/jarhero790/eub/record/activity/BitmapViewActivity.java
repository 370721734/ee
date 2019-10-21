package com.jarhero790.eub.record.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.jarhero790.eub.R;
import com.jarhero790.eub.record.bean.BitmapBean;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

//没用
public class BitmapViewActivity extends AppCompatActivity {

    @BindView(R.id.iv_video_image)
    ImageView ivVideoImage;
    private Bitmap bitmaped;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap_view);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        if (bitmaped!=null){
            ivVideoImage.setImageBitmap(bitmaped);
            Log.e("----------","来了没有3");
        }else {
            Log.e("----------","来了没有4");
        }

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void bitee(BitmapBean bitmapBean){
        if (bitmapBean!=null){
            bitmaped=bitmapBean.getBitmap();
            Log.e("----------","来了没有");
        }

        if (ivVideoImage!=null){
            ivVideoImage.setImageBitmap(bitmaped);
            Log.e("----------","来了没有2");
        }



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
