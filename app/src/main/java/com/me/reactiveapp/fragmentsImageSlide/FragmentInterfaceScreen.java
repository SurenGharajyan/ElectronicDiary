package com.me.reactiveapp.fragmentsImageSlide;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.me.reactiveapp.R;

/**
 * Created by USER on 14.07.2017.
 */

public class FragmentInterfaceScreen extends Fragment {
    FragmentModel model;

    @SuppressLint("ValidFragment")
    public FragmentInterfaceScreen(FragmentModel model) {
        this.model=model;
    }

    public FragmentInterfaceScreen() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_general_images,container,false);
        ImageView imageView=(ImageView) view.findViewById(R.id.slideshow);
//        RelativeLayout relativeLayout=(RelativeLayout) view.findViewById(R.id.fragment_relative);
        imageView.setBackgroundResource(model.getImageid());
        return view;
    }
}
