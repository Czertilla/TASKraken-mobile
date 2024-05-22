package com.example.taskraken.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ActivityNavigator;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.NavGraph;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.example.taskraken.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrganizationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrganizationFragment extends Fragment {
    Context context;
    View rootView;

    Animation rotateOpen;

    Animation rotateClose;
    NavController navController;
    Animation fromButton;
    Animation toButton;
    FloatingActionButton
            addFloatButton,
            addOrgButton,
            addRoleButton
        ;

    List<FloatingActionButton> subButtons;

    boolean clicked = true;

    public OrganizationFragment() {

        subButtons = new ArrayList<>();
    }


    public static OrganizationFragment newInstance() {
        return new OrganizationFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_organization, container, false);
        context = rootView.getContext();

        setUpNavController();
        setUpFloatingButtons();
        setUpAnimations();
        setUpInterface();

        return rootView;
    }

    private void setUpInterface() {
        addFloatButton.setOnClickListener(v -> {
            onAddFloatButtonClicked();
        });
        addOrgButton.setOnClickListener(v -> {
            Toast.makeText(
                    context,
                    "add Organization in development",
                    Toast.LENGTH_SHORT
            ).show();
            onAddFloatButtonClicked();
            navController.navigate(R.id.navigateToOrgBlankFragment);
        });
        addRoleButton.setOnClickListener(v -> {
//            TODO implement
            Toast.makeText(
                    context,
                    "add Roles in development",
                    Toast.LENGTH_SHORT
            ).show();
        });
    }

    private void setUpAnimations() {
        toButton = AnimationUtils.loadAnimation(context, R.anim.to_button_anim);
        fromButton = AnimationUtils.loadAnimation(context, R.anim.from_button_anim);
        rotateOpen = AnimationUtils.loadAnimation(context, R.anim.rotate_open_anim);
        rotateClose = AnimationUtils.loadAnimation(context, R.anim.rotate_close_anim);
    }

    private void setUpFloatingButtons() {
        addFloatButton = rootView.findViewById(R.id.floatingButtonOrgs);
        subButtons.add(addOrgButton = rootView.findViewById(R.id.addOrgButton));
        subButtons.add(addRoleButton = rootView.findViewById(R.id.addRoleButton));
    }

    private void setUpNavController(){
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        NavHostFragment navHostFragment = (NavHostFragment) fragmentManager
                .findFragmentById(R.id.fragment_container);
        assert navHostFragment != null;
        navController = navHostFragment.getNavController();
    }

    private void onAddFloatButtonClicked(){
        subButtons.forEach(b -> {
            b.setAnimation(clicked ? fromButton : toButton);
            b.setVisibility(clicked ? View.VISIBLE: View.INVISIBLE);
            b.setClickable(clicked);
        });
        addFloatButton.setAnimation(clicked ? rotateOpen : rotateClose);
        clicked = !clicked;
    }

}