package com.example.testapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

public class FragmentGallery extends Fragment {
    ImageView imageView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        imageView=(ImageView) getView().findViewById(R.id.imageView);

        // 1. load the saved image in the external storage of the device

        // 2. show the image in the imageView on the fragment

        return inflater.inflate(R.layout.fragment_gallery, container, false);
    }
}