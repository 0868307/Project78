package com.resist.mus3d;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.opengl.GLES20;
import android.os.AsyncTask;


import com.resist.mus3d.database.Afmeerboeien;
import com.resist.mus3d.objects.Afmeerboei;

import org.osmdroid.util.Position;

import java.util.ArrayList;
import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

import rajawali.BaseObject3D;
import rajawali.OrthographicCamera;
import rajawali.lights.DirectionalLight;
import rajawali.materials.SimpleMaterial;
import rajawali.materials.ToonMaterial;
import rajawali.parser.AParser;
import rajawali.parser.ObjParser;
import rajawali.renderer.RajawaliRenderer;

/**
 * Created by Thomas on 23-3-2015.
 */
public class MyRenderer extends RajawaliRenderer {
    private DirectionalLight mLight;
    private BaseObject3D mSphere;
    private ArrayList<BaseObject3D> object3Ds = new ArrayList<>();
    public MyRenderer(Context context) {
        super(context);
        setFrameRate(60);
        setBackgroundColor(7.0f, 2.0f, 0.0f, .20f);
    }

    public void initScene() {
        mLight = new DirectionalLight(1f, 0.2f, -1.0f); // set the direction
        mLight.setColor(1.0f, 1.0f, 1.0f);
        mLight.setPower(2);

        ObjParser parser = new ObjParser(mContext.getResources(), mTextureManager, R.raw.bolder_obj);

        try {
            parser.parse();
        } catch (AParser.ParsingException e) {
            e.printStackTrace();
        }
        BaseObject3D mObject = parser.getParsedObject();
        //addChild(mObject);
        new LongOperation().execute();
        mCamera.setLookAt(0,0,0);
        mCamera.setZ(4.2f);
        mCamera.setY(-1.5f);
        mObject.setRotation(40, 0, 70);
        mObject.setScale(.3f);
        mObject.setDrawingMode(GLES20.GL_LINE_STRIP);


        /** ToonMaterial toonMat = new ToonMaterial();
        toonMat.setToonColors(0xffffffff, 0xff000000, 0xff666666, 0xff000000);
        mObject.setMaterial(toonMat);
        mObject.addLight(mLight);

        // -- Create an instance
        OrthographicCamera camera = new OrthographicCamera();
        // -- Set the zoom level
        camera.setZoom(1); // -- this is the default
        camera.setZoom(2);
        // -- Set the look at coordinates
        camera.setLookAt(1, 10, 3);
        mObject.setScale(0.2f);
         */

    }
    public void setCamera(float x, float y, float z){
        mCamera.setPosition(x,y,z);
    }
    public void setCameraRotation(float x, float y, float z){
        mCamera.setRotation(x,y,z);}
    private class LongOperation extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            Afmeerboeien afmeerboeien = new Afmeerboeien(Mus3D.getDatabase().getDatabase());
            Position lastPos = null;
            for(Afmeerboei afmeerboei : afmeerboeien.getAll()){
                ObjParser parser = new ObjParser(mContext.getResources(), mTextureManager, R.raw.bolder_obj);

                try {
                    parser.parse();
                } catch (AParser.ParsingException e) {
                    e.printStackTrace();
                }
                BaseObject3D mObject = parser.getParsedObject();
                addChild(mObject);
                lastPos = afmeerboei.getLocation().getPosition();
                mObject.setPosition((float) (lastPos.getLatitude()), (float) (lastPos.getLongitude()), 0);
                mObject.setRotation(90, 0, 90);
                mObject.setScale(.3f);
                mObject.setDrawingMode(GLES20.GL_LINE_STRIP);
                object3Ds.add(mObject);
            }
            System.out.println("executed");
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
        }

        @Override
        protected void onPreExecute() {}


        @Override
        protected void onProgressUpdate(Void... values) {}
    }
}
