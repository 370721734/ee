package com.jarhero790.eub.message.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jarhero790.eub.message.bean.SearchBean;

import java.util.ArrayList;
import java.util.List;

import me.drakeet.multitype.MultiTypeAdapter;
import silladus.basic.adapter.page.BasePage;
import silladus.basic.adapter.page.ListPagerAdapter;


/**
 * Created by silladus on 2018/6/12/0012.
 * GitHub: https://github.com/silladus
 * Description:
 */
public class MenuPagerAdapter
        extends ListPagerAdapter<SearchBean.DataBean.VisitBean, RecyclerView, MultiTypeAdapter>
        implements OnItemClickListener {

    @NonNull
    @Override
    protected BasePage<SearchBean.DataBean.VisitBean, RecyclerView, MultiTypeAdapter> initPageLayout(int index, Context context) {
        MenuPage page = new MenuPage(getReqCode()[index], index, context) {
            @Override
            public void initData(int reqCode, int partIndex, int pagerIndex) {
                receiveData(pageData.get(pagerIndex), "暂无数据");
            }
        };
        page.setOnItemClickListener(this);
        return page;
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private List<List<SearchBean.DataBean.VisitBean>> pageData;

    public static int calculatePageCount(List<SearchBean.DataBean.VisitBean> entry) {
        int count = entry.size() / 6;
        int sub = entry.size() % 6;
        if (sub != 0) {
            count++;
        }
        return count;
    }

    public void setEntry(List<SearchBean.DataBean.VisitBean> entry, int count) {
        List<SearchBean.DataBean.VisitBean> oEntry = new ArrayList<>(entry);
        pageData = new ArrayList<>();

        for (int i = 0; i < count - 1; i++) {
            List<SearchBean.DataBean.VisitBean> subList = oEntry.subList(0, 6);
            List<SearchBean.DataBean.VisitBean> list = new ArrayList<>(subList);
            pageData.add(list);
            oEntry.removeAll(list);
        }
        pageData.add(oEntry);
    }

    @Override
    public void onItemClick(View view, int i, int pagerPosition) {
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(view, i, pagerPosition);
        }
    }



    private int[] lastSelectPositionArr;

    public void updatePage(int pagerIndex, int position) {
        if (lastSelectPositionArr == null) {
            lastSelectPositionArr = new int[getCount()];
        }

        for (int i = 0; i < getCount(); i++) {
            if (i == pagerIndex) {
                ((MenuPage) getPageLayout(i)).setItemCheck(position, true);
                lastSelectPositionArr[i] = position;
            } else {
                MenuPage page = (MenuPage) getPageLayout(i);
                if (page != null) {
                    page.setItemCheck(lastSelectPositionArr[i], false);
                }
            }
        }
    }


}
