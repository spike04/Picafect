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

public class Effect_Normal extends Activity implements OnClickListener {

	Button bt, bt1, bt2, bt3, bt4, bt5, bt6, bt7, bt8, bt9, bt10, bt11, bt12,
			bt13, bt14, bt15, bt16, bt17, bt18, bt19, bt20, bt21, bt22, bt23,
			bt24, bt25, bt26, bt27, bt28, bt29, bt30;
	ImageButton apply_normal;

	ImageView image;
	Bitmap bmp, bm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.effect);

		Typeface myTypeface = Typeface.createFromAsset(getAssets(),
				"font/HALOHANDLETTER.OTF");
		TextView myTextView = (TextView) findViewById(R.id.txtTitle);
		myTextView.setTypeface(myTypeface);

		bmp = ImageFilter.getBitmap();
		image = (ImageView) findViewById(R.id.effectImageView);

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
		apply_normal = (ImageButton) findViewById(R.id.apply_Normal);

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
		apply_normal.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.effect_black:
			image.setImageBitmap(ImageFilter.applyBlackFilter(bmp));
			break;
		case R.id.effect_boost_1:
			image.setImageBitmap(ImageFilter.applyBoostEffect(bmp, 1, 40));
			break;
		case R.id.effect_boost_2:
			image.setImageBitmap(ImageFilter.applyBoostEffect(bmp, 2, 30));
			break;
		case R.id.effect_boost_3:
			image.setImageBitmap(ImageFilter.applyBoostEffect(bmp, 3, 67));
			break;
		case R.id.effect_brightness:
			image.setImageBitmap(ImageFilter.applyBrightnessEffect(bmp, 80));
			break;
		case R.id.effect_color_red:
			image.setImageBitmap(ImageFilter.applyColorFilterEffect(bmp, 255,
					0, 0));
			break;
		case R.id.effect_color_green:
			image.setImageBitmap(ImageFilter.applyColorFilterEffect(bmp, 0,
					255, 0));
			break;
		case R.id.effect_color_blue:
			image.setImageBitmap(ImageFilter.applyColorFilterEffect(bmp, 0, 0,
					255));
			break;
		case R.id.effect_color_depth_64:
			image.setImageBitmap(ImageFilter.applyDecreaseColorDepthEffect(bmp,
					64));
			break;
		case R.id.effect_color_depth_32:
			image.setImageBitmap(ImageFilter.applyDecreaseColorDepthEffect(bmp,
					32));
			break;
		case R.id.effect_contrast:
			image.setImageBitmap(ImageFilter.applyContrastEffect(bmp, 70));
			break;
		case R.id.effect_emboss:
			image.setImageBitmap(ImageFilter.applyEmbossEffect(bmp));
			break;
		case R.id.effect_engrave:
			image.setImageBitmap(ImageFilter.applyEngraveEffect(bmp));
			break;
		case R.id.effect_flea:
			image.setImageBitmap(ImageFilter.applyFleaEffect(bmp));
			break;
		case R.id.effect_gaussian_blur:
			image.setImageBitmap(ImageFilter.applyGaussianBlurEffect(bmp));
			break;
		case R.id.effect_gamma:
			image.setImageBitmap(ImageFilter.applyGammaEffect(bmp, 1.8, 1.8,
					1.8));
			break;
		case R.id.effect_grayscale:
			image.setImageBitmap(ImageFilter.applyGreyscaleEffect(bmp));
			break;
		case R.id.effect_hue:
			image.setImageBitmap(ImageFilter.applyHueFilter(bmp, 2));
			break;
		case R.id.effect_invert:
			image.setImageBitmap(ImageFilter.applyInvertEffect(bmp));
			break;
		case R.id.effect_mean_remove:
			image.setImageBitmap(ImageFilter.applyMeanRemovalEffect(bmp));
			break;
		case R.id.effect_round_corner:
			image.setImageBitmap(ImageFilter.applyRoundCornerEffect(bmp, 45));
			break;
		case R.id.effect_saturation:
			image.setImageBitmap(ImageFilter.applySaturationFilter(bmp, 1));
			break;
		case R.id.effect_sepia:
			image.setImageBitmap(ImageFilter.applySepiaToningEffect(bmp, 10,
					1.5, 0.6, 0.12));
			break;
		case R.id.effect_sepia_green:
			image.setImageBitmap(ImageFilter.applySepiaToningEffect(bmp, 10,
					0.88, 2.45, 1.43));
			break;
		case R.id.effect_sepia_blue:
			image.setImageBitmap(ImageFilter.applySepiaToningEffect(bmp, 10,
					1.2, 0.87, 2.1));
			break;
		case R.id.effect_smooth:
			image.setImageBitmap(ImageFilter.applySmoothEffect(bmp, 100));
			break;
		case R.id.effect_sheding_cyan:
			image.setImageBitmap(ImageFilter
					.applyShadingFilter(bmp, Color.CYAN));
			break;
		case R.id.effect_sheding_yellow:
			image.setImageBitmap(ImageFilter.applyShadingFilter(bmp,
					Color.YELLOW));
			break;
		case R.id.effect_sheding_green:
			image.setImageBitmap(ImageFilter.applyShadingFilter(bmp,
					Color.GREEN));
			break;
		case R.id.effect_tint:
			image.setImageBitmap(ImageFilter.applyTintEffect(bmp, 100));
			break;

		case R.id.apply_Normal:
			BitmapDrawable apply = (BitmapDrawable) image.getDrawable();
			bm = apply.getBitmap();
			ImageFilter.setBitmap(bm);
			Intent intent = new Intent(this, Effect_Choose.class);
			startActivity(intent);
			finish();
		default:
			image.setImageBitmap(bmp);
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
