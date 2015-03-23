package mus3d.resist.com.mus3d;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import java.util.Arrays;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Thomas on 23-3-2015.
 */
public class MyRenderer implements GLSurfaceView.Renderer {


    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];
    private final float[] mRotationMatrix = new float[16];
    public boolean shouldDrawCube=true;
    public DrawingClass line,rect,triangle;
    private float verticesCube[] = {
            -1.0f, -1.0f, -1.0f,
            1.0f, -1.0f, -1.0f,
            1.0f,  1.0f, -1.0f,
            -1.0f, 1.0f, -1.0f,
            -1.0f, -1.0f,  1.0f,
            1.0f, -1.0f,  1.0f,
            1.0f,  1.0f,  1.0f,
            -1.0f,  1.0f,  1.0f
    };
    private short indicesCube[] = {
            0, 4, 5, 0, 5, 1,
            1, 5, 6, 1, 6, 2,
            2, 6, 7, 2, 7, 3,
            3, 7, 4, 3, 4, 0,
            4, 7, 6, 4, 6, 5,
            3, 0, 1, 3, 1, 2
    };

    private float colorsCube[] = {
            0.3f,  0.2f,  1.0f,  1.0f,
    };



    public DrawingClass cube;

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);// r g b alpha
        ////.................... Drawing Demo...................................................//

        // Line Demo..............................
        line = new DrawingClass(3, new float[]{ -0.5f,  0.5f, 0.0f,     -0.5f, -0.5f, 0.0f,   -0.49f, -0.5f, 0.0f,       -0.49f,  0.5f, 0.0f }, new float[]{1.0f,0.0f,0.0f,1.0f},new short[]{0,1,2,0,2,3});
        // Rectangle Demo..............................
        rect = new DrawingClass(3, new float[]{ -1.0f,  0.5f, 0.0f,     -1.0f, 1.0f, 0.0f,   0, 1.0f, 0.0f,       0,  .5f, 0.0f }, new float[]{0.0f,1.5f,0.0f,1.0f},new short[]{0,1,2,0,2,3});
        //Triangle..............................
        triangle = new DrawingClass(3, new float[]{ 0.9f,  0.7f, 0.0f,     .9f,.2f, 0.0f,   .4f, .2f, 0.0f,  }, new float[]{0.0f,0.0f,1.0f,1.0f},new short[]{0,1,2});

        for(int i=0;i<verticesCube.length;i++)
        {
            verticesCube[i]=verticesCube[i]/3;
        }
        // 3D Cube
        cube = new DrawingClass(3, verticesCube,colorsCube,indicesCube);

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);

        float ratio = (float) width / height;

        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
    }
    float mCubeRotation=0;
    float[] mRotateCubeM=new float[16];
    @Override
    public void onDrawFrame(GL10 gl) {
        float[] scratch = new float[16];

        // Draw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        // Set the camera position (View matrix)
        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

        // Calculate the projection and view transformation
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

        Matrix.multiplyMM(scratch, 0, mMVPMatrix, 0, mRotationMatrix, 0);

        line.draw(mMVPMatrix);
        rect.draw(mMVPMatrix);
        triangle.draw(mMVPMatrix);

        if(shouldDrawCube)
        {
            Matrix.setRotateM(mRotateCubeM, 0, mCubeRotation, 0f, 0f, -1f);

            //rotation x camera = modelView
            float[] duplicateMatrix = Arrays.copyOf(mMVPMatrix, 16);

            Matrix.multiplyMM(mMVPMatrix, 0, duplicateMatrix, 0, mRotateCubeM, 0);

            Matrix.setRotateM(mRotateCubeM, 0, mCubeRotation, 0f, -1f, 0f);
            duplicateMatrix = Arrays.copyOf(mMVPMatrix, 16);
            Matrix.multiplyMM(mMVPMatrix, 0, duplicateMatrix, 0, mRotateCubeM, 0);

            Matrix.setRotateM(mRotateCubeM, 0, mCubeRotation, -1f, 0f, 0f);
            duplicateMatrix = Arrays.copyOf(mMVPMatrix, 16);
            Matrix.multiplyMM(mMVPMatrix, 0, duplicateMatrix, 0, mRotateCubeM, 0);

            cube.draw(mMVPMatrix);
            ///////////////////////////////////////////////////////
            Matrix.multiplyMM(scratch, 0, mMVPMatrix, 0, mRotateCubeM, 0);

            mCubeRotation += 0.15f;
        }



    }

    public static int loadShader(int type, String shaderCode){

        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }
}
