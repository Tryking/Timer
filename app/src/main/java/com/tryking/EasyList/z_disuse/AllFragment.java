package com.tryking.EasyList.z_disuse;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tryking.EasyList.R;

/**
 * Created by 26011 on 2016/7/27.
 */
public class AllFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_viewhistory_all, container, false);
        return view;
    }
}
