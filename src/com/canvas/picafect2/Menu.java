package com.canvas.picafect2;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

public class Menu extends Activity {

	public static int RESULT_LOAD_IMAGE = 1;
	public static int CAMERA_REQUEST = 0;
	Intent i;
	TextView mCamera, mTitle, mGallery, mAdd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_main);

		Typeface myTypeface = Typeface.createFromAsset(getAssets(),
				"font/HALOHANDLETTER.OTF");
		mCamera = (TextView) findViewById(R.id.txtCamera);
		mGallery = (TextView) findViewById(R.id.txtGallery);
		mTitle = (TextView) findViewById(R.id.txtTitle);
		mAdd = (TextView) findViewById(R.id.txtAdd);

		mTitle.setTypeface(myTypeface);
		mGallery.setTypeface(myTypeface);
		mCamera.setTypeface(myTypeface);
		mAdd.setTypeface(myTypeface);

		ImageButton buttonCamera = (ImageButton) findViewById(R.id.btnCamera);
		buttonCamera.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent cameraIntent = new Intent(
						MediaStore.ACTION_IMAGE_CAPTURE);
				cameraIntent.putExtra("picture", true);
				overridePendingTransition(R.anim.slide_in_right,
						R.anim.slide_out_left);
				startActivityForResult(cameraIntent, CAMERA_REQUEST);
			}
		});

		ImageButton buttonLoadImage = (ImageButton) findViewById(R.id.btnGallery);
		buttonLoadImage.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				i = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

				startActivityForResult(i, RESULT_LOAD_IMAGE);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK
				&& null != data) {
			Uri selectedImage = data.getData();
			String temPath = getPath(selectedImage, Menu.this);
			Bitmap bmp;
			BitmapFactory.Options btmapOptions = new BitmapFactory.Options();
			bmp = BitmapFactory.decodeFile(temPath, btmapOptions);

			ImageFilter.setBitmap(bmp);
			/*
			 * ByteArrayOutputStream stream = new ByteArrayOutputStream();
			 * bmp.compress(Bitmap.CompressFormat.PNG, 100, stream); byte[]
			 * byteArray = stream.toByteArray();
			 */

			Intent intent = new Intent(this, Effect_Choose.class);
			// intent.putExtra("picture", byteArray);
			startActivity(intent);
		}

		else if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
			Bitmap bmp = (Bitmap) data.getExtras().get("data");
			ImageFilter.setBitmap(bmp);
			/*
			 * ByteArrayOutputStream stream = new ByteArrayOutputStream();
			 * bmp.compress(Bitmap.CompressFormat.PNG, 100, stream); byte[]
			 * byteArray = stream.toByteArray();
			 */

			Intent intent = new Intent(this, Effect_Choose.class);
			// intent.putExtra("picture", byteArray);
			startActivity(intent);
		}
	}

	public String getPath(Uri uri, Activity activity) {
		String[] projection = { MediaStore.MediaColumns.DATA };
		@SuppressWarnings("deprecation")
		Cursor cursor = activity
				.managedQuery(uri, projection, null, null, null);
		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}

}
