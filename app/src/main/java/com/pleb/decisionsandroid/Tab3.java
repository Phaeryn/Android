package com.pleb.decisionsandroid;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.github.tbouron.shakedetector.library.ShakeDetector;
import java.util.Random;
import io.saeid.fabloading.LoadingView;

/**
 * Created by Maxime on 11/04/2017.
 */

public class Tab3 extends Fragment {


    private LoadingView mLoadingView;
    Button b_play;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab3, container, false);

        b_play = (Button) rootView.findViewById(R.id.b_play);
        boolean isLollipop = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
        final int heads = isLollipop ? R.drawable.heads_1_lollipop : R.drawable.heads;
        int tails = isLollipop ? R.drawable.tails_2_lollipop : R.drawable.tails;

        mLoadingView = (LoadingView) rootView.findViewById(R.id.loading_view);
        mLoadingView.addAnimation(Color.parseColor("#ffffff"), heads,
                LoadingView.FROM_LEFT);
        mLoadingView.addAnimation(Color.parseColor("#ffffff"), tails,
                LoadingView.FROM_RIGHT);

        mLoadingView = (LoadingView) rootView.findViewById(R.id.loading_view);
        mLoadingView.addAnimation(Color.parseColor("#ffffff"), tails, LoadingView.FROM_RIGHT);
        mLoadingView.addAnimation(Color.parseColor("#ffffff"), heads, LoadingView.FROM_LEFT);
        b_play.setOnClickListener (new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start();
            }
        });
        ShakeDetector.create(getActivity().getApplicationContext(), new ShakeDetector.OnShakeListener() {
            @Override
            public void OnShake() {
                Toast.makeText(getActivity().getApplicationContext(), "Device shaken!", Toast.LENGTH_SHORT).show();
                start();
            }
        });
        return rootView;
    }

    public void start() {
        Random r = new Random();
        int n = 1 + r.nextInt(30 - 1);

        mLoadingView.startAnimation();
        mLoadingView.setRepeat(n);
    }

    @Override
    public void onResume() {
        super.onResume();
        ShakeDetector.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        ShakeDetector.stop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ShakeDetector.destroy();
    }
}