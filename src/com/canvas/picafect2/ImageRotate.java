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

public class ImageRotate extends Activity implements OnClickListener {
	Bitmap bmp, bm;
	ImageView image;
	Button rotateRight, rotateLeft;
	ImageButton apply_rotate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.image_rotate);

		Typeface myTypeface = Typeface.createFromAsset(getAssets(),
				"font/HALOHANDLETTER.OTF");
		TextView myTextView = (TextView) findViewById(R.id.txtTitle);
		myTextView.setTypeface(myTypeface);

		bmp = ImageFilter.getBitmap();
		image = (ImageView) findViewById(R.id.rotateImageView);
		image.setImageBitmap(bmp);

		initialize();
		clickListener();

	}

	public void initialize() {
		rotateLeft = (Button) findViewById(R.id.rotate_left);
		rotateRight = (Button) findViewById(R.id.rotate_right);
		apply_rotate = (ImageButton) findViewById(R.id.apply_Rotate);
	}

	public void clickListener() {
		rotateLeft.setOnClickListener(this);
		rotateRight.setOnClickListener(this);
		apply_rotate.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.rotate_left:
			BitmapDrawable abmp1 = (BitmapDrawable) image.getDrawable();
			bm = abmp1.getBitmap();
			image.setImageBitmap(ImageFilter.rotate(bm, -90));
			break;
		case R.id.rotate_right:
			BitmapDrawable abmp2 = (BitmapDrawable) image.getDrawable();
			bm = abmp2.getBitmap();
			image.setImageBitmap(ImageFilter.rotate(bm, 90));
			break;
		case R.id.apply_Rotate:
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
