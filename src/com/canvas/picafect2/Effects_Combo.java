package com.canvas.picafect2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
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

public class Effects_Combo extends Activity implements OnClickListener {

	Button bt, bt1, bt2, bt3, bt4, bt5, bt6, bt7, bt8, bt9, bt10, bt11, bt12,
			bt13, bt14, bt15, bt16, bt17, bt18, bt19, bt20, bt21, bt22, bt23,
			bt24, bt25, bt26, bt27, bt28, bt29, bt30;
	ImageButton apply;
	ImageView image;
	Bitmap bmp, bm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.effects_combo);

		Typeface myTypeface = Typeface.createFromAsset(getAssets(),
				"font/HALOHANDLETTER.OTF");
		TextView myTextView = (TextView) findViewById(R.id.txtTitle);
		myTextView.setTypeface(myTypeface);

		/*
		 * Bundle extras = getIntent().getExtras(); byte[] byteArray =
		 * extras.getByteArray("picture");
		 * 
		 * bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
		 */
		bmp = ImageFilter.getBitmap();
		image = (ImageView) findViewById(R.id.effects_comboImageView);

		image.setImageBitmap(bmp);

		initialize();
		clickListener();

	}

	public void initialize() {
		bt = (Button) findViewById(R.id.original);
		bt1 = (Button) findViewById(R.id.effect_black);
		bt2 = (Button) findViewById(R.id.effect_boost_1);
		bt3 = (Button) findViewById(R.id.effect_boost_2);
		bt4 = (Button) findViewById(R.id.effect_boost_3);
		bt5 = (Button) findViewById(R.id.effect_brightness);
		bt6 = (Button) findViewById(R.id.effect_color_blue);
		bt7 = (Button) findViewById(R.id.effect_color_green);
		bt8 = (Button) findViewById(R.id.effect_color_red);
		bt9 = (Button) findViewById(R.id.effect_color_depth_32);
		bt10 = (Button) findViewById(R.id.effect_color_depth_64);
		bt11 = (Button) findViewById(R.id.effect_contrast);
		bt12 = (Button) findViewById(R.id.effect_emboss);
		bt13 = (Button) findViewById(R.id.effect_engrave);
		bt14 = (Button) findViewById(R.id.effect_flea);
		bt15 = (Button) findViewById(R.id.effect_gamma);
		bt16 = (Button) findViewById(R.id.effect_gaussian_blur);
		bt17 = (Button) findViewById(R.id.effect_grayscale);
		bt18 = (Button) findViewById(R.id.effect_hue);
		bt19 = (Button) findViewById(R.id.effect_invert);
		bt20 = (Button) findViewById(R.id.effect_mean_remove);
		bt21 = (Button) findViewById(R.id.effect_round_corner);
		bt22 = (Button) findViewById(R.id.effect_saturation);
		bt23 = (Button) findViewById(R.id.effect_sepia);
		bt24 = (Button) findViewById(R.id.effect_sepia_blue);
		bt25 = (Button) findViewById(R.id.effect_sepia_green);
		bt26 = (Button) findViewById(R.id.effect_sheding_cyan);
		bt27 = (Button) findViewById(R.id.effect_sheding_green);
		bt28 = (Button) findViewById(R.id.effect_sheding_yellow);
		bt29 = (Button) findViewById(R.id.effect_smooth);
		bt30 = (Button) findViewById(R.id.effect_tint);
		apply = (ImageButton) findViewById(R.id.applyInCombo);

	}

	public void clickListener() {
		bt.setOnClickListener(this);
		bt1.setOnClickListener(this);
		bt2.setOnClickListener(this);
		bt3.setOnClickListener(this);
		bt4.setOnClickListener(this);
		bt5.setOnClickListener(this);
		bt6.setOnClickListener(this);
		bt7.setOnClickListener(this);
		bt8.setOnClickListener(this);
		bt9.setOnClickListener(this);
		bt10.setOnClickListener(this);
		bt11.setOnClickListener(this);
		bt12.setOnClickListener(this);
		bt13.setOnClickListener(this);
		bt14.setOnClickListener(this);
		bt15.setOnClickListener(this);
		bt16.setOnClickListener(this);
		bt17.setOnClickListener(this);
		bt18.setOnClickListener(this);
		bt19.setOnClickListener(this);
		bt20.setOnClickListener(this);
		bt21.setOnClickListener(this);
		bt22.setOnClickListener(this);
		bt23.setOnClickListener(this);
		bt24.setOnClickListener(this);
		bt25.setOnClickListener(this);
		bt26.setOnClickListener(this);
		bt27.setOnClickListener(this);
		bt28.setOnClickListener(this);
		bt29.setOnClickListener(this);
		bt30.setOnClickListener(this);
		apply.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.original:
			image.setImageBitmap(bmp);
			break;
		case R.id.effect_invert:
			BitmapDrawable abmp = (BitmapDrawable) image.getDrawable();
			bm = abmp.getBitmap();
			image.setImageBitmap(ImageFilter.applyInvertEffect(bm));
			break;
		case R.id.effect_grayscale:
			BitmapDrawable abmp1 = (BitmapDrawable) image.getDrawable();
			bm = abmp1.getBitmap();
			image.setImageBitmap(ImageFilter.applyGreyscaleEffect(bm));
			break;
		case R.id.effect_black:
			BitmapDrawable abmp3 = (BitmapDrawable) image.getDrawable();
			bm = abmp3.getBitmap();
			image.setImageBitmap(ImageFilter.applyBlackFilter(bm));
			break;
		case R.id.effect_boost_1:
			BitmapDrawable abmp4 = (BitmapDrawable) image.getDrawable();
			bm = abmp4.getBitmap();
			image.setImageBitmap(ImageFilter.applyBoostEffect(bm, 1, 40));
			break;
		case R.id.effect_boost_2:
			BitmapDrawable abmp5 = (BitmapDrawable) image.getDrawable();
			bm = abmp5.getBitmap();
			image.setImageBitmap(ImageFilter.applyBoostEffect(bm, 2, 30));
			break;
		case R.id.effect_boost_3:
			BitmapDrawable abmp6 = (BitmapDrawable) image.getDrawable();
			bm = abmp6.getBitmap();
			image.setImageBitmap(ImageFilter.applyBoostEffect(bm, 3, 67));
			break;
		case R.id.effect_brightness:
			BitmapDrawable abmp7 = (BitmapDrawable) image.getDrawable();
			bm = abmp7.getBitmap();
			image.setImageBitmap(ImageFilter.applyBrightnessEffect(bm, 50));
			break;
		case R.id.effect_color_red:
			BitmapDrawable abmp8 = (BitmapDrawable) image.getDrawable();
			bm = abmp8.getBitmap();
			image.setImageBitmap(ImageFilter.applyColorFilterEffect(bm, 255, 0,
					0));
			break;
		case R.id.effect_color_green:
			BitmapDrawable abmp9 = (BitmapDrawable) image.getDrawable();
			bm = abmp9.getBitmap();
			image.setImageBitmap(ImageFilter.applyColorFilterEffect(bm, 0, 255,
					0));
			break;
		case R.id.effect_color_blue:
			BitmapDrawable abmp10 = (BitmapDrawable) image.getDrawable();
			bm = abmp10.getBitmap();
			image.setImageBitmap(ImageFilter.applyColorFilterEffect(bm, 0, 0,
					255));
			break;
		case R.id.effect_color_depth_64:
			BitmapDrawable abmp11 = (BitmapDrawable) image.getDrawable();
			bm = abmp11.getBitmap();
			image.setImageBitmap(ImageFilter.applyDecreaseColorDepthEffect(bm,
					64));
			break;
		case R.id.effect_color_depth_32:
			BitmapDrawable abmp12 = (BitmapDrawable) image.getDrawable();
			bm = abmp12.getBitmap();
			image.setImageBitmap(ImageFilter.applyDecreaseColorDepthEffect(bm,
					32));
			break;
		case R.id.effect_contrast:
			BitmapDrawable abmp13 = (BitmapDrawable) image.getDrawable();
			bm = abmp13.getBitmap();
			image.setImageBitmap(ImageFilter.applyContrastEffect(bm, 50));
			break;
		case R.id.effect_emboss:
			BitmapDrawable abmp14 = (BitmapDrawable) image.getDrawable();
			bm = abmp14.getBitmap();
			image.setImageBitmap(ImageFilter.applyEmbossEffect(bm));
			break;
		case R.id.effect_engrave:
			BitmapDrawable abmp15 = (BitmapDrawable) image.getDrawable();
			bm = abmp15.getBitmap();
			image.setImageBitmap(ImageFilter.applyEngraveEffect(bm));
			break;
		case R.id.effect_flea:
			BitmapDrawable abmp16 = (BitmapDrawable) image.getDrawable();
			bm = abmp16.getBitmap();
			image.setImageBitmap(ImageFilter.applyFleaEffect(bm));
			break;
		case R.id.effect_gaussian_blur:
			BitmapDrawable abmp17 = (BitmapDrawable) image.getDrawable();
			bm = abmp17.getBitmap();
			image.setImageBitmap(ImageFilter.applyGaussianBlurEffect(bm));
			break;
		case R.id.effect_gamma:
			BitmapDrawable abmp18 = (BitmapDrawable) image.getDrawable();
			bm = abmp18.getBitmap();
			image.setImageBitmap(ImageFilter
					.applyGammaEffect(bm, 1.8, 1.8, 1.8));
			break;
		case R.id.effect_hue:
			BitmapDrawable abmp19 = (BitmapDrawable) image.getDrawable();
			bm = abmp19.getBitmap();
			image.setImageBitmap(ImageFilter.applyHueFilter(bm, 2));
			break;
		case R.id.effect_mean_remove:
			BitmapDrawable abmp20 = (BitmapDrawable) image.getDrawable();
			bm = abmp20.getBitmap();
			image.setImageBitmap(ImageFilter.applyMeanRemovalEffect(bm));
			break;
		case R.id.effect_round_corner:
			BitmapDrawable abmp21 = (BitmapDrawable) image.getDrawable();
			bm = abmp21.getBitmap();
			image.setImageBitmap(ImageFilter.applyRoundCornerEffect(bm, 45));
			break;
		case R.id.effect_saturation:
			BitmapDrawable abmp22 = (BitmapDrawable) image.getDrawable();
			bm = abmp22.getBitmap();
			image.setImageBitmap(ImageFilter.applySaturationFilter(bm, 1));
			break;
		case R.id.effect_sepia:
			BitmapDrawable abmp2 = (BitmapDrawable) image.getDrawable();
			bm = abmp2.getBitmap();
			image.setImageBitmap(ImageFilter.applySepiaToningEffect(bm, 10,
					1.5, 0.6, 0.12));
			break;
		case R.id.effect_sepia_blue:
			BitmapDrawable abmp23 = (BitmapDrawable) image.getDrawable();
			bm = abmp23.getBitmap();
			image.setImageBitmap(ImageFilter.applySepiaToningEffect(bm, 10,
					0.88, 2.45, 1.43));
			break;
		case R.id.effect_sepia_green:
			BitmapDrawable abmp24 = (BitmapDrawable) image.getDrawable();
			bm = abmp24.getBitmap();
			image.setImageBitmap(ImageFilter.applySepiaToningEffect(bm, 10,
					1.2, 0.87, 2.1));
			break;
		case R.id.effect_smooth:
			BitmapDrawable abmp25 = (BitmapDrawable) image.getDrawable();
			bm = abmp25.getBitmap();
			image.setImageBitmap(ImageFilter.applySmoothEffect(bm, 50));
			break;
		case R.id.effect_sheding_cyan:
			BitmapDrawable abmp26 = (BitmapDrawable) image.getDrawable();
			bm = abmp26.getBitmap();
			image.setImageBitmap(ImageFilter.applyShadingFilter(bm, Color.CYAN));
			break;
		case R.id.effect_sheding_yellow:
			BitmapDrawable abmp27 = (BitmapDrawable) image.getDrawable();
			bm = abmp27.getBitmap();
			image.setImageBitmap(ImageFilter.applyShadingFilter(bm,
					Color.YELLOW));
			break;
		case R.id.effect_sheding_green:
			BitmapDrawable abmp28 = (BitmapDrawable) image.getDrawable();
			bm = abmp28.getBitmap();
			image.setImageBitmap(ImageFilter
					.applyShadingFilter(bm, Color.GREEN));
			break;
		case R.id.effect_tint:
			BitmapDrawable abmp29 = (BitmapDrawable) image.getDrawable();
			bm = abmp29.getBitmap();
			image.setImageBitmap(ImageFilter.applyTintEffect(bm, 100));
			break;
		case R.id.applyInCombo:
			BitmapDrawable apply = (BitmapDrawable) image.getDrawable();
			bm = apply.getBitmap();
			ImageFilter.setBitmap(bm);
			Intent intent = new Intent(Effects_Combo.this, Effect_Choose.class);
			startActivity(intent);
			finish();
		}
	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent(getApplicationContext(), Effect_Choose.class);
		startActivity(intent);
		finish();
	}

}
