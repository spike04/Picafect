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
import android.widget.TextView;

public class ImageFlip extends Activity implements OnClickListener {

	Bitmap bmp, bm;
	ImageView image;
	Button flipHor, flipVer;
	ImageButton apply_flip;
	public static final int FLIP_VERTICAL = 1;
	public static final int FLIP_HORIZONTAL = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.image_filp);

		Typeface myTypeface = Typeface.createFromAsset(getAssets(),
				"font/HALOHANDLETTER.OTF");
		TextView myTextView = (TextView) findViewById(R.id.txtTitle);
		myTextView.setTypeface(myTypeface);

		bmp = ImageFilter.getBitmap();
		image = (ImageView) findViewById(R.id.flipImageView);
		image.setImageBitmap(bmp);

		initialize();
		clickListener();

	}

	public void initialize() {
		flipHor = (Button) findViewById(R.id.flip_hor);
		flipVer = (Button) findViewById(R.id.flip_ver);
		apply_flip = (ImageButton) findViewById(R.id.apply_Flip);
	}

	public void clickListener() {
		flipHor.setOnClickListener(this);
		flipVer.setOnClickListener(this);
		apply_flip.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.flip_hor:
			BitmapDrawable abmp1 = (BitmapDrawable) image.getDrawable();
			bm = abmp1.getBitmap();
			image.setImageBitmap(ImageFilter.flip(bm, 2));
			break;
		case R.id.flip_ver:
			BitmapDrawable abmp2 = (BitmapDrawable) image.getDrawable();
			bm = abmp2.getBitmap();
			image.setImageBitmap(ImageFilter.flip(bm, 1));
			break;
		case R.id.apply_Flip:
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
