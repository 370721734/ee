package com.jarhero790.eub.record.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jarhero790.eub.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MusicSingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MusicSingFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_music_sing, container, false);
    }

}
