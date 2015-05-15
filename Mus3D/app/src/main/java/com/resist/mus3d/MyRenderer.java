package com.resist.mus3d;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.opengl.GLES20;
import android.os.AsyncTask;
import com.resist.mus3d.database.ObjectTable;
import com.resist.mus3d.objects.Object;
import com.resist.mus3d.objects.coords.Coordinate;
import com.resist.mus3d.objects.coords.Point;
import org.osmdroid.util.Position;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import rajawali.BaseObject3D;
import rajawali.lights.DirectionalLight;
import rajawali.materials.TextureInfo;
import rajawali.materials.ToonMaterial;
import rajawali.parser.AParser;
import rajawali.parser.ObjParser;
import rajawali.renderer.RajawaliRenderer;
import rajawali.util.ObjectColorPicker;
import rajawali.util.OnObjectPickedListener;

public class MyRenderer extends RajawaliRenderer implements OnObjectPickedListener {
    public static final int MULTIPLIER = 10000;
    private DirectionalLight mLight;

    private Map<BaseObject3D, Object> object3Ds = new HashMap<>();
    private BaseObject3D selectedObject;
    private Rajawali context;
    private ObjectColorPicker mPicker;
    private OnObjectPickedListener mObjectPickedListener;
    public MyRenderer(Context context) {
        super(context);
        this.context = (Rajawali)context;
        setFrameRate(60);
        setBackgroundColor(Color.RED);
    }

    public void initScene() {
        mLight = new DirectionalLight(1f, 0.2f, -1.0f); // set the direction
        mLight.setColor(1.0f, 1.0f, 1.0f);
        mLight.setPower(2);
        mCamera.setLookAt(0, 0, 0);

        ObjParser parser = new ObjParser(mContext.getResources(), mTextureManager, R.raw.bolder_obj);

        try {
            parser.parse();
        } catch (AParser.ParsingException e) {
            e.printStackTrace();
        }
        BaseObject3D mObject = parser.getParsedObject();
        addChild(mObject);
        mCamera.setZ(4.2f);
        mCamera.setY(-1.5f);
        mObject.setRotation(40, 0, 90);
        mObject.setScale(.3f);
        mObject.setDrawingMode(GLES20.GL_LINE_STRIP);
        //setCamera(0,0,0);
        mPicker = new ObjectColorPicker(this);
        mPicker.setOnObjectPickedListener(this);
        mPicker.registerObject(mObject);
        System.out.println("Dus, ja"+mPicker);


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
        mObject.setScale(0_mtl.2f);
         */

    }
    public void setCamera(float x, float y, float z){
        mCamera.setPosition(x, y, z);
    }
    public void setCameraRotation(float x, float y, float z){
        mCamera.setRotation(x, y, z);}

    public void makeObjects(){
        new LongOperation().execute();
    }

    @Override
    public void onObjectPicked(BaseObject3D object) {
        selectedObject = object;
        Object mijnObject = object3Ds.get(object);



    }

    public void getObjectAt(float x, float y) {
        mPicker.getObjectAt(x, y);

    }

    public int[] getIntArray(Integer number){

        int[] characterizedArray = new int[number.toString().length()];
        String tussenweg;

        for (int i = 0; i < number.toString().length() ; i++) {
            char numberAsChar = number.toString().charAt(i);
            tussenweg =""+numberAsChar;
            int parsedInt = Integer.parseInt(tussenweg);
            characterizedArray[i] = parsedInt;
        }


        return characterizedArray;
    }

    private class LongOperation extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            ObjectTable objectTable = new ObjectTable(Mus3D.getDatabase().getDatabase());
            List<? extends com.resist.mus3d.objects.Object> list = objectTable.getObjectsAround(new Point(context.getLocation()), 0.001);
            Map<BaseObject3D, Object> newobject3Ds = new HashMap<>();
            Position lastPos = null;
            System.out.println("lat = "+context.getLocation().getLatitude()*MULTIPLIER+" long = "+context.getLocation().getLongitude()*MULTIPLIER);
            System.out.println("list size = "+list.size());
            for (Object o : list) {

                ObjParser parser = new ObjParser(mContext.getResources(), mTextureManager, R.raw.bolder_obj);

                try {
                    parser.parse();
                } catch (AParser.ParsingException e) {
                    e.printStackTrace();
                }
                BaseObject3D mObject = parser.getParsedObject();
                addChild(mObject);
                mPicker.registerObject(mObject);
                System.out.println(mPicker);
                lastPos = o.getLocation().getPosition();
                float objectPosX = (float) (lastPos.getLongitude() * MULTIPLIER);
                float objectPosY = 0;
                float objectPosZ = (float) (lastPos.getLatitude() * MULTIPLIER);

                mObject.setPosition(objectPosX, objectPosY, objectPosZ);
                mObject.setRotation(0, 0, 0);
                mObject.setScale(.1f);
                mObject.setDrawingMode(1);
                newobject3Ds.put(mObject, o);
                drawNumber((int)o.getLocation().getDistanceTo(new Point(mCamera.getX(),mCamera.getY())),objectPosX,objectPosY,objectPosZ,newobject3Ds,o);
            }
            System.out.println("executed");
            for (Map.Entry<BaseObject3D, Object> o : object3Ds.entrySet()) {
                removeChild(o.getKey());
            }
            object3Ds = newobject3Ds;
            System.out.println("old objects removed");

            return true;
        }

        public void drawNumber(int number,float x,float y,float z , Map<BaseObject3D, Object> map, Object o){
            int[] numbers = getIntArray(number);
            for(int i=0;i<numbers.length;i++){
                ObjParser parser = new ObjParser(mContext.getResources(), mTextureManager, R.raw.bolder_obj);
                try {
                    parser.parse();
                } catch (AParser.ParsingException e) {
                    e.printStackTrace();
                }
                BaseObject3D mObject = parser.getParsedObject();
                addChild(mObject);
                mPicker.registerObject(mObject);
                System.out.println(mPicker);
                mObject.setPosition(x + (i * 10), y - 50, z);
                mObject.setRotation(0, 0, 0);
                mObject.setScale(.1f);
                mObject.setDrawingMode(1);
                map.put(mObject,o);
            }
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
