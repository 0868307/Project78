package mus3d.resist.com.mus3d;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Thomas on 19-3-2015.
 */
public class GISurfaceView extends GLSurfaceView {

   // MyRenderer myRenderer;
    TextView textView;
    MainActivity mainActivity;

    public GISurfaceView(Context context){
        super(context);
        mainActivity = (MainActivity) context;
        setEGLContextClientVersion(2);

        setRenderer(new Renderer() {
            @Override
            public void onSurfaceCreated(GL10 gl, EGLConfig config) {
                // Set the background frame color
                GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);// r g b alpha
            }

            @Override
            public void onSurfaceChanged(GL10 gl, int width, int height) {
                GLES20.glViewport(0, 0, width, height);
            }

            @Override
            public void onDrawFrame(GL10 gl) {
                // Redraw background color
                GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
            }
        });
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
