package com.resist.mus3d;

import android.content.Context;
import android.graphics.Color;
import android.opengl.GLES20;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import com.resist.mus3d.database.ObjectTable;
import com.resist.mus3d.objects.Object;
import com.resist.mus3d.objects.coords.Point;
import org.osmdroid.util.Position;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import rajawali.BaseObject3D;
import rajawali.lights.DirectionalLight;
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
        setBackgroundColor(Color.LTGRAY);
    }

    public void initScene() {

        stopRendering();
        startGettingGPS();
        mCamera.setLookAt(0, 0, 0);

        // Create the default light
        letThereBeLight();

        }

    private void letThereBeLight(){
        mLight = new DirectionalLight(1f, 0.2f, -1.0f); // set the direction
        mLight.setColor(1.0f, 1.0f, 1.0f);
        mLight.setPower(2);

    }

    public void setCamera(float x, float y, float z){
        mCamera.setPosition(x, y, z);
    }
    public void setCameraRotation(float x, float y, float z){
        mCamera.setRotation(x, y, z);}

    public void drawValues(final String... values){
        System.out.println("+-+-+-+-+-+-+-+-+-+-+-+-");
        for (int i = 0; i < values.length; i++) {
            System.out.println("value "+i+" : "+values[i]);
            System.out.println("...........................");
        }
        System.out.println("+-+-+-+-+-+-+-+-+-+-+-+-");
    }

    public void startGettingGPS(){
        mHandler.post(
                new Runnable() {
                    @Override
                    public void run() {

                        new GetGPS().execute();

                    }
                }
        );
    }


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


    private void clearProgressLabel(){
        Message msg = new Message();
        msg.arg1 = 0;
        mHandler.sendMessage(msg);
    }

    private void updateProgressLabel(String text, boolean determinate){
        Message msg = new Message();
        msg.arg1 = determinate ? 1 : 2;
        msg.obj = text;
        System.out.println(msg.arg1);
        mHandler.sendMessage(msg);
    }

    private void setProgressLimit(int max)
    {
        Message msg = new Message();
        msg.arg1 = 3;
        msg.obj = max;
        mHandler.sendMessage(msg);
    }

     private void resetProgress()
    {
        Message msg = new Message();
        msg.arg1 = 3;
        mHandler.sendMessage(msg);
    }

    private Handler mHandler = new Handler();

    public void handleMessage(Message msg){
        Rajawali act = (Rajawali)mContext;
        switch (msg.arg1){
            case 0:
                act.clearLabel();
                break;
            case 1:
                act.setLabel((String)msg.obj);
                act.showProgressBar(true);
                break;
            case 2:
                act.setLabel((String)msg.obj);
                act.showProgressBar(false);
                break;
            case 3:
                act.setProgressBarPosition(0);
                break;


        }
    }

    private class GetGPS
            extends AsyncTask<Void,Void,Void>{
        @Override
        protected Void doInBackground(Void... unused){

            updateProgressLabel("GPS Locatie wordt bepaald...", false);

            while (doIhaveALocation()) {
                refreshGeometry();
            }

            return null;
        }
    }

    private boolean doIhaveALocation(){
        System.out.println("Trying to get GPS");
        System.out.println(context.getLocation());
        return context.getLocation() != null;
    }


    private void refreshGeometry(){
        //resetProgress();

        mSurfaceView.queueEvent(
                new Runnable() {
                    @Override
                    public void run() {
                        clearProgressLabel();
                        startRendering();
                    }
                });
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
                mObject.setPosition((float) (lastPos.getLongitude()*MULTIPLIER) ,0, (float) (lastPos.getLatitude()*MULTIPLIER) );
                mObject.setRotation(0, 0, 0);
                mObject.setScale(.1f);
                mObject.setDrawingMode(GLES20.GL_LINE_STRIP);
                newobject3Ds.put(mObject,o);
                System.out.println("++++++++++++++++++++++++++++++++++++");
                System.out.println(lastPos.getLatitude()*MULTIPLIER);
                System.out.println(lastPos.getLongitude()*MULTIPLIER);
                System.out.println("||||||||||||||||||||||||||||||||||||");
            }
            System.out.println("executed");
            for (Map.Entry<BaseObject3D, Object> o : object3Ds.entrySet()) {
                removeChild(o.getKey());
            }
            object3Ds = newobject3Ds;
            System.out.println("old objects removed");

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
