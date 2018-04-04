package com.raspberyl.mynews.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.raspberyl.mynews.R;

public class FragmentBusiness extends Fragment {

    View view;

    public FragmentBusiness() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.article_recyclerview, container, false);

        return view;
    }

}
