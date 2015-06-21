package com.resist.mus3d.ar;

import android.graphics.Color;
import android.os.AsyncTask;

import com.resist.mus3d.Mus3D;
import com.resist.mus3d.R;
import com.resist.mus3d.database.ObjectTable;
import com.resist.mus3d.map.Marker;
import com.resist.mus3d.objects.Afmeerboei;
import com.resist.mus3d.objects.Anchorage;
import com.resist.mus3d.objects.Bolder;
import com.resist.mus3d.objects.Koningspaal;
import com.resist.mus3d.objects.Meerpaal;
import com.resist.mus3d.objects.Object;
import com.resist.mus3d.objects.coords.Point;

import org.osmdroid.util.Position;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import rajawali.BaseObject3D;
import rajawali.lights.DirectionalLight;
import rajawali.materials.SimpleMaterial;
import rajawali.parser.AParser;
import rajawali.parser.ObjParser;
import rajawali.primitives.Cube;
import rajawali.renderer.RajawaliRenderer;
import rajawali.util.ObjectColorPicker;
import rajawali.util.OnObjectPickedListener;

public class ObjectRenderer extends RajawaliRenderer implements OnObjectPickedListener {
	public static final int MULTIPLIER = 10000;
	private Map<BaseObject3D, Object> object3Ds = new HashMap<>();
	private Rajawali context;
	private ObjectColorPicker mPicker;

	/**
	 * Instantiates a new Object renderer.
	 *
	 * @param context the context
	 */
	public ObjectRenderer(Rajawali context) {
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
		DirectionalLight mLight = new DirectionalLight(1f, 0.2f, -1.0f);
		mLight.setColor(1.0f, 1.0f, 1.0f);
		mLight.setPower(2);

	}

	/**
	 * Set camera.
	 *
	 * @param x the x
	 * @param y the y
	 * @param z the z
	 */
	public void setCamera(float x, float y, float z){
		mCamera.setPosition(x, y, z);
	}

	/**
	 * Set camera rotation.
	 *
	 * @param x the x
	 * @param y the y
	 * @param z the z
	 */
	public void setCameraRotation(float x, float y, float z){
		mCamera.setRotation(x, y, z);
	}

	/**
	 * Make objects.
	 */
	public void makeObjects() {
		new LongOperation().execute();
	}

	@Override
	public void onObjectPicked(BaseObject3D object) {
		final Object mijnObject = object3Ds.get(object);
		context.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				new Marker(context, mijnObject).getDialog().show();
			}
		});
	}

	/**
	 * Gets object at.
	 *
	 * @param x the x
	 * @param y the y
	 */
	public void getObjectAt(float x, float y) {
		mPicker.getObjectAt(x, y);

	}


	private class LongOperation extends AsyncTask<String, Void, Boolean> {

		@Override
		protected Boolean doInBackground(String... params) {
			try {

				ObjectTable objectTable = new ObjectTable(Mus3D.getDatabase().getDatabase());
				List<? extends com.resist.mus3d.objects.Object> list = objectTable.getObjectsAround(new Point(context.getLocation()), 0.001);

				Map<BaseObject3D, Object> newobject3Ds = new HashMap<>();
				Position lastPos;
				Set<Object> searchObjects;
				searchObjects = new HashSet<>();
				searchObjects = getObjects(searchObjects);

				for (int n = 0, size = list.size(); n < size; n++) {
					Object o = list.get(n);
					ObjParser parser = new ObjParser(mContext.getResources(), mTextureManager, R.raw.bolder_obj);

					try {
						parser.parse();
					} catch (AParser.ParsingException e) {
						e.printStackTrace();
					}

					BaseObject3D mObject = new Cube(1);
					lastPos = o.getLocation().getPosition();

					setValuesToObject(lastPos, mObject);

					if (checkHashsetForObjects(searchObjects, o)) {
						mObject.setTransparent(false);
					} else {
						mObject.setTransparent(true);
					}

					if (checkForAfmeerboei(o)) {
						mObject.setColor(0x70FF0000); // rood
					} else if (checkForBolder(o)) {
						mObject.setColor(0x700000FF); // blauw
					} else if (checkForKoningspaal(o)) {
						mObject.setColor(0x70FA7202);	// oranje
					} else if (checkForAnchorage(o)) {
						mObject.setColor(0x7000FF00);	// groen
					} else if (checkForMeerpaal(o)) {
						mObject.setColor(0x70F202FA);	// Pimpelpaars met een goud randje zonder het gouden randje
					}
					addChild(mObject);
					mPicker.registerObject(mObject);
					newobject3Ds.put(mObject, o);
					context.onObjectLoadingProgress((double) n / (double) size * 100.0);
				}
				for (Map.Entry<BaseObject3D, Object> o : object3Ds.entrySet()) {
					removeChild(o.getKey());
				}
				object3Ds = newobject3Ds;

			}catch (Exception e){
				e.printStackTrace();
			}
			return true;
		}

		private boolean checkForMeerpaal(Object o) {
			return o instanceof Meerpaal;
		}

		private boolean checkForAnchorage(Object o) {
			return o instanceof Anchorage;
		}

		private boolean checkForKoningspaal(Object o) {
			return o instanceof Koningspaal;
		}

		private boolean checkForBolder(Object o) {
			return o instanceof Bolder;
		}

		private boolean checkForAfmeerboei(Object o) {
			return o instanceof Afmeerboei;
		}

		private boolean checkHashsetForObjects(Set<Object> searchObjects, Object o) {
			return !searchObjects.isEmpty() && searchObjects.contains(o);
		}

		private void setValuesToObject(Position lastPos, BaseObject3D mObject) {
			SimpleMaterial myMaterial = new SimpleMaterial();
			myMaterial.setUseColor(true);
			mObject.setPosition((float) (lastPos.getLongitude() * MULTIPLIER), 0, (float) (lastPos.getLatitude() * MULTIPLIER));
			mObject.setRotation(0, 0, 0);
			mObject.setScale(.1f);
			mObject.setMaterial(myMaterial);
		}

		private Set<Object> getObjects(Set<Object> searchObjects) {
			if (context.getIntentFromSearch() != null) {
				searchObjects = context.getIntentFromSearch();
			}
			return searchObjects;
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
