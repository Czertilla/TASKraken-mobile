package com.example.taskraken.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.taskraken.R;

public class CreateOrgBlankFragment extends Fragment {

    private Context context;
    private Spinner roleTemplateSpinner;
    private View rootView;
    public CreateOrgBlankFragment() {}

    public static CreateOrgBlankFragment newInstance() {
        CreateOrgBlankFragment fragment = new CreateOrgBlankFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void setUpSpinner(){
        roleTemplateSpinner = rootView.findViewById(R.id.spinner_role_template);
        ArrayAdapter adapter = new ArrayAdapter(
                context,
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.role_templates)
        );
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        roleTemplateSpinner.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_regist_org, container, false);
        context = rootView.getContext();

        setUpSpinner();

        return rootView;
    }
}