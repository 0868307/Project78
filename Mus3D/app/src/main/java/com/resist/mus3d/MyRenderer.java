package com.resist.mus3d;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;

import javax.microedition.khronos.opengles.GL10;

import rajawali.BaseObject3D;
import rajawali.lights.DirectionalLight;
import rajawali.materials.SimpleMaterial;
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
        setBackgroundColor(7.0f, 2.0f, 0.0f, .20f);
    }

    public void initScene() {
        mLight = new DirectionalLight(1f, 0.2f, -1.0f); // set the direction
        mLight.setColor(1.0f, 1.0f, 1.0f);
        mLight.setPower(2);
        ObjParser parser = new ObjParser(mContext.getResources(), mTextureManager, R.raw.kubus1_obj);
        try {
            parser.parse();
        } catch (AParser.ParsingException e) {
            e.printStackTrace();
        }
        BaseObject3D mObject = parser.getParsedObject();
        addChild(mObject);
        mCamera.setZ(4.2f);
        mCamera.setY(-1.5f);
        mObject.setRotation(40, 0, 70);
        mObject.setScale(.3f);
        mObject.setDrawingMode(GLES20.GL_LINE_STRIP);
    }
}
