package com.example.taskraken.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taskraken.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateOrgBlankFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateOrgBlankFragment extends Fragment {
    public CreateOrgBlankFragment() {
        // Required empty public constructor
    }

    public static CreateOrgBlankFragment newInstance() {
        CreateOrgBlankFragment fragment = new CreateOrgBlankFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_regist_org, container, false);
    }
}