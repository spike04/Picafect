package com.canvas.picafect2;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class Effect_Choose extends Activity implements OnClickListener {

	ImageView image;
	Bitmap bitmap, bm;
	Button effects, specialEffects, comboEffects, flip, rotation, brightness,
			contrast;
	ImageButton save, share;
	Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.effect_choose);

		Typeface myTypeface = Typeface.createFromAsset(getAssets(),
				"font/HALOHANDLETTER.OTF");
		TextView myTextView = (TextView) findViewById(R.id.txtTitle);
		myTextView.setTypeface(myTypeface);

		image = (ImageView) findViewById(R.id.mImageView);

		image.setImageBitmap(ImageFilter.getBitmap());

		initialize();
		clickListener();

	}

	public void clickListener() {
		effects.setOnClickListener(this);
		specialEffects.setOnClickListener(this);
		comboEffects.setOnClickListener(this);
		flip.setOnClickListener(this);
		rotation.setOnClickListener(this);
		brightness.setOnClickListener(this);
		contrast.setOnClickListener(this);
		save.setOnClickListener(this);
		share.setOnClickListener(this);
	}

	private void initialize() {
		effects = (Button) findViewById(R.id.normal);
		specialEffects = (Button) findViewById(R.id.enhanced);
		comboEffects = (Button) findViewById(R.id.combo);
		flip = (Button) findViewById(R.id.flip);
		rotation = (Button) findViewById(R.id.orientation);
		brightness = (Button) findViewById(R.id.brightness);
		contrast = (Button) findViewById(R.id.contrast);
		save = (ImageButton) findViewById(R.id.save);
		share = (ImageButton) findViewById(R.id.share);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.normal:
			Intent intent = new Intent(this, Effect_Normal.class);
			startActivity(intent);
			finish();
			break;
		case R.id.enhanced:
			Intent i = new Intent(this, Effects_Special.class);
			startActivity(i);
			finish();
			break;
		case R.id.combo:
			Intent combo = new Intent(this, Effects_Combo.class);
			startActivity(combo);
			finish();
			break;
		case R.id.flip:
			Intent flip = new Intent(this, ImageFlip.class);
			startActivity(flip);
			finish();
			break;
		case R.id.orientation:
			Intent rotate = new Intent(this, ImageRotate.class);
			startActivity(rotate);
			finish();
			break;
		case R.id.brightness:
			Intent brightness = new Intent(this, ImageBrightness.class);
			startActivity(brightness);
			finish();
			break;
		case R.id.contrast:
			Intent contrast = new Intent(this, ImageContrast.class);
			startActivity(contrast);
			finish();
			break;
		case R.id.save:
			BitmapDrawable abmp = (BitmapDrawable) image.getDrawable();
			bm = abmp.getBitmap();
			save(bm);
			break;
		case R.id.share:
			shareImage();
			break;
		}

	}

	public void save(Bitmap bmp) {

		/*
		 * File f = new File(Environment.getExternalStorageDirectory() +
		 * File.separator + "picafect"+ rand()+".jpg"); f.createNewFile();
		 * //write the bytes in file FileOutputStream fo = new
		 * FileOutputStream(f); fo.write(bytes.toByteArray());
		 * 
		 * // remember close de FileOutput fo.close();
		 */

		Save saveFile = new Save();
		saveFile.SaveImage(this, bmp);
	}

	// Method to share any image.
	private void shareImage() {
		Intent share = new Intent(Intent.ACTION_SEND);
		// If you want to share a png image only, you can do:
		// setType("image/png"); OR for jpeg: setType("image/jpeg");
		share.setType("image/*");

		// Make sure you put example png image named myImage.png in your
		// directory
		String imagePath = Environment.getExternalStorageDirectory()
				+ "Picafect/Picafect" + getCurrentDateAndTime() + ".png";

		File imageFileToShare = new File(imagePath);

		Uri uri = Uri.fromFile(imageFileToShare);
		share.putExtra(Intent.EXTRA_STREAM, uri);

		startActivity(Intent.createChooser(share, "Share Image!"));
	}

	@SuppressLint("SimpleDateFormat")
	private String getCurrentDateAndTime() {
		Calendar c = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		String formattedDate = df.format(c.getTime());
		return formattedDate;
	}

}