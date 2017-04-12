package com.pleb.decisionsandroid;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;


import java.util.Random;

/**
 * Created by Maxime on 11/04/2017.
 */

public class Tab2 extends Fragment {

    ImageView iv_sword;
    Button b_play;
    Random r;
    int angle;
    boolean restart = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab2, container, false);

        r = new Random();
        iv_sword = (ImageView) rootView.findViewById(R.id.iv_sword);
        b_play = (Button) rootView.findViewById(R.id.b_play);
        b_play.setOnClickListener (new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (restart) {
                    angle = angle % 360;
                    RotateAnimation rotate = new RotateAnimation(angle, 360,
                            RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                            RotateAnimation.RELATIVE_TO_SELF, 0.5f);

                    rotate.setFillAfter(true);
                    rotate.setDuration(360);
                    rotate.setInterpolator(new AccelerateDecelerateInterpolator());

                    iv_sword.startAnimation(rotate);
                    b_play.setText("PLAY");
                    restart = false;
                } else {
                    angle = r.nextInt(3600) + 360;
                    RotateAnimation rotate = new RotateAnimation(0, angle,
                            RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                            RotateAnimation.RELATIVE_TO_SELF, 0.5f);

                    rotate.setFillAfter(true);
                    rotate.setDuration(3600);
                    rotate.setInterpolator(new AccelerateDecelerateInterpolator());

                    iv_sword.startAnimation(rotate);
                    restart = true;
                    b_play.setText("Reset");
                }
            }
        });
        return rootView;
    }
}
