package com.resist.mus3d;

import android.content.Context;
import android.graphics.Color;
import android.opengl.GLES20;
import android.os.AsyncTask;
import com.resist.mus3d.database.ObjectTable;
import com.resist.mus3d.map.Marker;
import com.resist.mus3d.objects.Afmeerboei;
import com.resist.mus3d.objects.Anchorage;
import com.resist.mus3d.objects.Bolder;
import com.resist.mus3d.objects.Common;
import com.resist.mus3d.objects.Koningspaal;
import com.resist.mus3d.objects.Meerpaal;
import com.resist.mus3d.objects.Object;
import com.resist.mus3d.objects.coords.Point;
import org.osmdroid.util.Position;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import rajawali.BaseObject3D;
import rajawali.lights.DirectionalLight;
import rajawali.materials.SimpleMaterial;
import rajawali.parser.AParser;
import rajawali.parser.ObjParser;
import rajawali.primitives.Cube;
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
	public MyRenderer(Rajawali context) {
		super(context);
		this.context = context;
		setFrameRate(60);
		setBackgroundColor(Color.LTGRAY);
	}

	public void initScene() {
		mCamera.setLookAt(0, 0, 0);
		createLight();
		mPicker = new ObjectColorPicker(this);
		mPicker.setOnObjectPickedListener(this);

	}

	private void createLight() {
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
	public void makeObjects() {
		new LongOperation().execute();
	}

	@Override
	public void onObjectPicked(BaseObject3D object) {
		selectedObject = object;
		final Object mijnObject = object3Ds.get(object);
		context.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				new Marker(context, mijnObject).getDialog().show();
			}
		});
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
			for (int n=0, size = list.size(); n < size; n++) {
				Object o = list.get(n);
				ObjParser parser = new ObjParser(mContext.getResources(), mTextureManager, R.raw.bolder_obj);

				try {
					parser.parse();
				} catch (AParser.ParsingException e) {
					e.printStackTrace();
				}

				BaseObject3D mObject = parser.getParsedObject();
				mObject = new Cube(1);
				addChild(mObject);
				mPicker.registerObject(mObject);
				System.out.println(mPicker);
				lastPos = o.getLocation().getPosition();
				mObject.setPosition((float) (lastPos.getLongitude() * MULTIPLIER), 0, (float) (lastPos.getLatitude() * MULTIPLIER));
				mObject.setRotation(0, 0, 0);
				mObject.setScale(.1f);
				mObject.setDrawingMode(GLES20.GL_LINE_STRIP);
				SimpleMaterial myMaterial = new SimpleMaterial();
				myMaterial.setUseColor(true);
				mObject.setMaterial(myMaterial);
				newobject3Ds.put(mObject, o);
				context.onObjectLoadingProgress((double)n / (double)size * 100.0);
				if (o instanceof Afmeerboei) {
					mObject.setColor(Color.BLUE);
				} else if (o instanceof Bolder) {
					mObject.setColor(Color.RED);
				} else if (o instanceof Koningspaal) {
					mObject.setColor(Color.YELLOW);
				} else if (o instanceof Anchorage) {
					mObject.setColor(Color.GREEN);
				} else if (o instanceof Meerpaal) {
					mObject.setColor(Color.BLACK);
				}
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
			context.onObjectsLoaded(result);
		}

		@Override
		protected void onPreExecute() {}


		@Override
		protected void onProgressUpdate(Void... values) {}


	}
}
