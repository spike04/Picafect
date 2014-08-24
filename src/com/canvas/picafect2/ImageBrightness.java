package com.canvas.picafect2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class ImageBrightness extends Activity implements OnClickListener {
	Bitmap bmp, bm;
	ImageView image;
	ImageButton apply_brightness;
	Button increase, decrease;
	SeekBar adjustBrightness;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.image_brightness);

		Typeface myTypeface = Typeface.createFromAsset(getAssets(),
				"font/HALOHANDLETTER.OTF");
		TextView myTextView = (TextView) findViewById(R.id.txtTitle);
		myTextView.setTypeface(myTypeface);

		bmp = ImageFilter.getBitmap();
		image = (ImageView) findViewById(R.id.brightnessImageView);
		image.setImageBitmap(bmp);

		initialize();
		clickListener();

	}

	public void initialize() {
		apply_brightness = (ImageButton) findViewById(R.id.apply_Brightness);
		increase = (Button) findViewById(R.id.increaseBrightness);
		decrease = (Button) findViewById(R.id.decreaseBrightness);

	}

	public void clickListener() {
		apply_brightness.setOnClickListener(this);
		increase.setOnClickListener(this);
		decrease.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {

		case R.id.increaseBrightness:
			BitmapDrawable increase = (BitmapDrawable) image.getDrawable();
			bm = increase.getBitmap();
			image.setImageBitmap(ImageFilter.adjustBrightness(bm, 5));
			break;
		case R.id.decreaseBrightness:
			BitmapDrawable decrease = (BitmapDrawable) image.getDrawable();
			bm = decrease.getBitmap();
			image.setImageBitmap(ImageFilter.adjustBrightness(bm, -5));
			break;
		case R.id.apply_Brightness:
			BitmapDrawable apply = (BitmapDrawable) image.getDrawable();
			bm = apply.getBitmap();
			ImageFilter.setBitmap(bm);
			Intent intent = new Intent(this, Effect_Choose.class);
			startActivity(intent);
			finish();
			break;
		}

	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent(this, Effect_Choose.class);
		startActivity(intent);
		finish();
	}

}
