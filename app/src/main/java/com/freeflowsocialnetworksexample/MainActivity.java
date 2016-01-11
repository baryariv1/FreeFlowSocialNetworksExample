package com.freeflowsocialnetworksexample;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.comcast.freeflow.core.FreeFlowContainer;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by bar yariv on 11/01/2016.
 */
public class MainActivity extends AppCompatActivity {
    private RelativeLayout rootView;
    private List<Integer> snList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        rootView = (RelativeLayout) findViewById(R.id.rootView);

        snList = new ArrayList<>();
        for(int i = 0; i < 25; i++){
            snList.add(R.drawable.facebook);
            snList.add(R.drawable.github);
            snList.add(R.drawable.google);
            snList.add(R.drawable.instagram);
            snList.add(R.drawable.linkedin);
            snList.add(R.drawable.youtube);
        }

        FreeFlowContainer socialNetworksView = new FreeFlowContainer(this);
        SocialNetworksLayout socialNetworksLayout = new SocialNetworksLayout();
        SocialNetworksAdapter socialNetworksAdapter = new SocialNetworksAdapter(this, snList);
        socialNetworksView.setLayout(socialNetworksLayout);
        socialNetworksView.setAdapter(socialNetworksAdapter);
        socialNetworksView.dataInvalidated(false);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        socialNetworksView.setLayoutParams(params);
        rootView.addView(socialNetworksView);
    }
}
