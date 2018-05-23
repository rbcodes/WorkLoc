package com.example.stpl.workloc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by niccapdevila on 3/26/16.
 */
public class RecentsFragment extends Fragment implements  IGetActivity{

    public static final String ARGS_INSTANCE = "com.ncapdevi.sample.argsInstance";
    int mInt = 0;
    Button mButton;
    GPSTracker gps;
    BaseFragment.FragmentNavigation mFragmentNavigation;

    public static RecentsFragment  newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt(ARGS_INSTANCE, instance);
        RecentsFragment fragment = new RecentsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recents, container, false);
        mButton = (Button) view.findViewById(R.id.button);


        gps=new GPSTracker(getContext(),this);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gps.startTracker();

            }
        });
       // mButton.setText(getClass().getSimpleName() + " " + mInt);
    }

    @Override
    public Activity getGpsActivity() {
        return getActivity();
    }



}
