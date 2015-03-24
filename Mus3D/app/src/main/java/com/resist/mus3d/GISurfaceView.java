package com.resist.mus3d;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by Thomas on 19-3-2015.
 */
public class GISurfaceView extends GLSurfaceView {

    MyRenderer renderer;
    MainActivity mainActivity;

    public GISurfaceView(Context context){
        super(context);
        mainActivity = (MainActivity) context;
        setEGLContextClientVersion(2);

        final float[] mMVPMatrix = new float[16];
        final float[] mProjectionMatrix = new float[16];
        final float[] mViewMatrix = new float[16];
        final float[] mRotationMatrix = new float[16];

        renderer = new MyRenderer();
        renderer.shouldDrawCube=true;
        setRenderer(renderer);
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent){
        final float x = motionEvent.getX();
        final float y = motionEvent.getY();
        Log.i("GL Surface View", "X = "+x+" Y = "+y);
        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mainActivity.setTitle("X = "+x+" Y ="+y);
            }
        });
        return false;
    }

}
