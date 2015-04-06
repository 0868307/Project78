package com.resist.mus3d;


import android.os.Bundle;
import rajawali.RajawaliActivity;

public class Rajawali extends RajawaliActivity {
    private MyRenderer myRenderer;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myRenderer = new MyRenderer(this);
        myRenderer.setSurfaceView(mSurfaceView);
        super.setRenderer(myRenderer);
    }
}
