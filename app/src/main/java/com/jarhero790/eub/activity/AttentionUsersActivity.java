package com.jarhero790.eub.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.jarhero790.eub.R;
import com.jarhero790.eub.adapter.AttentionUsersRecyclerViewAdapter;
import com.jarhero790.eub.bean.AttentionUser;
import java.util.ArrayList;

public class AttentionUsersActivity extends Activity {

    private RecyclerView recyclerView;
    private SearchView searchView;
    ArrayList<AttentionUser> attentionUsers;
    private ImageView back;
    private ImageView userHeadIcon;
    private TextView username;
    private TextView sign;
    AttentionUsersRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attentionusers);
        recyclerView=findViewById(R.id.searchAttentionUsersRecyclerView);
        searchView=findViewById(R.id.searchAttentionUsersSearchView);
        back=findViewById(R.id.attentionUsersActivity_back);
        userHeadIcon=findViewById(R.id.userHeadIcon);
        username=findViewById(R.id.tv_username);
        sign=findViewById(R.id.tv_sign);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("customBundle");
        ArrayList<AttentionUser> attentionUsers = (ArrayList<AttentionUser>) args.getSerializable("attentionUsersList");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new AttentionUsersRecyclerViewAdapter(R.layout.attention_user_recyclerview_item, attentionUsers);
        recyclerView.setAdapter(adapter);
    }





}
