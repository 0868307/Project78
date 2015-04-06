package com.resist.mus3d;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.EGLConfig;

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

    public MyRenderer(Context context) {
        super(context);
        setFrameRate(60);
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
        addChild(mObject);

        ToonMaterial toonMat = new ToonMaterial();
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
    }

}
