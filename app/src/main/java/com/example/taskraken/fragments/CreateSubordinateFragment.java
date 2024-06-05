package com.example.taskraken.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.taskraken.R;
import com.example.taskraken.network.api.RolesApi;
import com.example.taskraken.network.api.StructsApi;
import com.example.taskraken.network.schemas.structs.RegistOrgResponse;
import com.example.taskraken.network.services.NetworkService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateSubordinateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateSubordinateFragment extends Fragment {
    private Context context;

    private NetworkService networkService;

    private EditText name;

    private TextView debugText;
    private Spinner roleTemplateSpinner
            ,createSubstructRightSpinner
            ,createSubordinateRightSpinner
            ,sendTaskRightSpinner
            ,sendPetitionRightSpinner
            ,rejectTaskRigthSpinner
            ,editOtherRightsRightSpinner;

    private SwitchCompat canSendReportSwitch
            , canEditOneselfRightsSwitch
            ,canCreateProjectSwitch;
    private View rootView;

    private Button registButton;
    private RolesApi roleApi;
    private NavController navController;

    public CreateSubordinateFragment() {}

    public static CreateSubordinateFragment newInstance() {
        CreateSubordinateFragment fragment = new CreateSubordinateFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void setUpSwitch() {
        canSendReportSwitch = rootView.findViewById(R.id.switch_send_report_FCS);
        canEditOneselfRightsSwitch = rootView.findViewById(R.id.switch_edit_oneself_rights_FCS);
        canCreateProjectSwitch = rootView.findViewById(R.id.switch_create_project_FCS);
    }

    private void setUpSpinner(){
        roleTemplateSpinner = rootView.findViewById(R.id.spinner_role_template_FCS);
        ArrayAdapter roleTemplateAdapter = new ArrayAdapter(
                context,
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.role_templates)
        );
        roleTemplateAdapter.setDropDownViewResource(
                android.R.layout.simple_dropdown_item_1line
        );
        roleTemplateSpinner.setAdapter(roleTemplateAdapter);

        createSubstructRightSpinner = rootView.findViewById(R.id.spinner_create_substruct_FCS);
        ArrayAdapter createSubStructAdapter = new ArrayAdapter(
                context,
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.create_substruct_rights)
        );
        createSubStructAdapter.setDropDownViewResource(
                android.R.layout.simple_dropdown_item_1line
        );
        createSubstructRightSpinner.setAdapter(createSubStructAdapter);

        createSubordinateRightSpinner = rootView.findViewById(R.id.spinner_create_subordinates_FCS);
        ArrayAdapter createSubOrdinateAdapter = new ArrayAdapter(
                context,
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.create_subordinates_rights)
        );
        createSubOrdinateAdapter.setDropDownViewResource(
                android.R.layout.simple_dropdown_item_1line
        );
        createSubordinateRightSpinner.setAdapter(createSubOrdinateAdapter);

        sendTaskRightSpinner = rootView.findViewById(R.id.spinner_send_task_FCS);
        ArrayAdapter sendTaskRightAdapter = new ArrayAdapter(
                context,
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.send_task_rights)
        );
        sendTaskRightAdapter.setDropDownViewResource(
                android.R.layout.simple_dropdown_item_1line
        );
        sendTaskRightSpinner.setAdapter(sendTaskRightAdapter);


        sendPetitionRightSpinner = rootView.findViewById(R.id.spinner_send_petition_FCS);
        ArrayAdapter sendPetitionAdapter = new ArrayAdapter(
                context,
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.send_petition_rights)
        );
        sendPetitionAdapter.setDropDownViewResource(
                android.R.layout.simple_dropdown_item_1line
        );
        sendPetitionRightSpinner.setAdapter(sendPetitionAdapter);

        rejectTaskRigthSpinner = rootView.findViewById(R.id.spinner_reject_task_FCS);
        ArrayAdapter rejectTaskAdapter = new ArrayAdapter(
                context,
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.reject_task_rights)
        );
        rejectTaskAdapter.setDropDownViewResource(
                android.R.layout.simple_dropdown_item_1line
        );
        rejectTaskRigthSpinner.setAdapter(rejectTaskAdapter);

        editOtherRightsRightSpinner = rootView.findViewById(R.id.spinner_edit_other_rights_FCS);
        ArrayAdapter editOtherRightsAdapter = new ArrayAdapter(
                context,
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.edit_other_rights)
        );
        editOtherRightsAdapter.setDropDownViewResource(
                android.R.layout.simple_dropdown_item_1line
        );
        editOtherRightsRightSpinner.setAdapter(editOtherRightsAdapter);

        roleTemplateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (parent.getItemAtPosition(position).toString()){
//                TODO get rid of the constant definition of strings
                    case ("gendir"): {
                        createSubstructRightSpinner.setSelection(0);
                        createSubordinateRightSpinner.setSelection(0);
                        sendTaskRightSpinner.setSelection(0);
                        sendPetitionRightSpinner.setSelection(0);
                        rejectTaskRigthSpinner.setSelection(0);
                        editOtherRightsRightSpinner.setSelection(0);
                        canSendReportSwitch.setChecked(true);
                        canEditOneselfRightsSwitch.setChecked(true);
                        canCreateProjectSwitch.setChecked(true);
                        break;
                    }
                    case ("head"): {
                        createSubstructRightSpinner.setSelection(2);
                        createSubordinateRightSpinner.setSelection(3);
                        sendTaskRightSpinner.setSelection(3);
                        sendPetitionRightSpinner.setSelection(2);
                        rejectTaskRigthSpinner.setSelection(3);
                        editOtherRightsRightSpinner.setSelection(1);
                        canSendReportSwitch.setChecked(true);
                        canEditOneselfRightsSwitch.setChecked(true);
                        canCreateProjectSwitch.setChecked(false);
                        break;
                    }
                    case ("ordinary"): {
                        createSubstructRightSpinner.setSelection(3);
                        createSubordinateRightSpinner.setSelection(6);
                        sendTaskRightSpinner.setSelection(3);
                        sendPetitionRightSpinner.setSelection(2);
                        rejectTaskRigthSpinner.setSelection(3);
                        editOtherRightsRightSpinner.setSelection(3);
                        canSendReportSwitch.setChecked(true);
                        canEditOneselfRightsSwitch.setChecked(false);
                        canCreateProjectSwitch.setChecked(false);
                        break;
                    }
                    case ("hr"): {
                        createSubstructRightSpinner.setSelection(3);
                        createSubordinateRightSpinner.setSelection(0);
                        sendTaskRightSpinner.setSelection(3);
                        sendPetitionRightSpinner.setSelection(2);
                        rejectTaskRigthSpinner.setSelection(3);
                        editOtherRightsRightSpinner.setSelection(0);
                        canSendReportSwitch.setChecked(true);
                        canEditOneselfRightsSwitch.setChecked(false);
                        canCreateProjectSwitch.setChecked(false);
                        canCreateProjectSwitch.setChecked(false);
                        break;
                    }
                    default: {

                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void setUpButton(){
        registButton = rootView.findViewById(R.id.button_create_subordinate);
        registButton.setOnClickListener(v -> {
            roleApi.createSubordinate(
                    name.getText().toString(),
                    roleTemplateSpinner.getSelectedItem().toString(),
                    createSubstructRightSpinner.getSelectedItem().toString(),
                    createSubordinateRightSpinner.getSelectedItem().toString(),
                    sendTaskRightSpinner.getSelectedItem().toString(),
                    canSendReportSwitch.isChecked(),
                    sendPetitionRightSpinner.getSelectedItem().toString(),
                    rejectTaskRigthSpinner.getSelectedItem().toString(),
                    canCreateProjectSwitch.isChecked(),
                    editOtherRightsRightSpinner.getSelectedItem().toString(),
                    canEditOneselfRightsSwitch.isChecked()
            ).enqueue(new Callback<Object>() {
                @Override
                public void onResponse(
                        @NonNull Call<Object> call,
                        @NonNull Response<Object> response
                ) {
//                    TODO implement
//                    debugText.setText(response.headers().toMultimap().toString());
//                    debugText.append("\n"+response.code());
                    if (response.isSuccessful() & response.body() != null){
                        navController.navigate(R.id.action_createSubordinateFragment_to_fragment_assignments);
                    }
//                    else if (response.errorBody() != null){
//                        debugText.append(response.errorBody().toString());
//                    }
//                    else {
//                        debugText.append("some goes wrong");
//                    }
                }

                @SuppressLint("SetTextI18n")
                @Override
                public void onFailure(
                        @NonNull Call<Object> call,
                        @NonNull Throwable throwable
                ) {
                    debugText.setText("Error occurred while getting request!");
                    throwable.printStackTrace();
                }
            });
        });
    }
    private void setUpNavController(){
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        NavHostFragment navHostFragment = (NavHostFragment) fragmentManager
                .findFragmentById(R.id.fragment_container);
        assert navHostFragment != null;
        navController = navHostFragment.getNavController();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_create_subordinate, container, false);
        context = rootView.getContext();

        setUpNavController();
        setUpSwitch();
        setUpText();
        setUpSpinner();
        roleTemplateSpinner.setSelection(0);
        setUpNetwork();
        setUpButton();

        return rootView;
    }

    private void setUpText() {
        name = rootView.findViewById(R.id.name_FCS);
//        debugText = getView().findViewById(R.id.text_view_debug_AM);
    }

    private void setUpNetwork() {
        networkService = NetworkService.getInstance();
        roleApi = networkService.getRoleApi();
    }
}