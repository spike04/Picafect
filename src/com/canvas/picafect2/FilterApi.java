package com.canvas.picafect2;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.util.Log;

public class FilterApi {

	public static Bitmap changeToBlock(Bitmap bitmap) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();

		int dst[] = new int[width * height];
		bitmap.getPixels(dst, 0, width, 0, 0, width, height);

		int iPixel = 0;
		int i, j, color, pos;
		for (j = 0; j < height; j++) {
			for (i = 0; i < width; i++) {
				pos = j * width + i;
				color = dst[pos];
				int avg = (Color.red(color) + Color.green(color) + Color
						.blue(color)) / 3;
				if (avg >= 100)
					iPixel = 255;
				else
					iPixel = 0;

				dst[pos] = Color.rgb(iPixel, iPixel, iPixel);
			}
		}

		Bitmap bmpReturn = Bitmap.createBitmap(width, height,
				Bitmap.Config.RGB_565);
		bmpReturn.setPixels(dst, 0, width, 0, 0, width, height);
		return bmpReturn;
	}

	public static Bitmap changeToCarton(Bitmap bitmap) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		int dst[] = new int[width * height];
		bitmap.getPixels(dst, 0, width, 0, 0, width, height);

		int R, G, B, pixel;
		int pos, pixColor;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				pos = y * width + x;
				pixColor = dst[pos];
				R = Color.red(pixColor); // (color >> 16) & 0xFF
				G = Color.green(pixColor); // (color >> 8) & 0xFF;
				B = Color.blue(pixColor); // color & 0xFF
				pixel = G - B + G + R;
				if (pixel < 0)
					pixel = -pixel;
				pixel = pixel * R / 256;
				if (pixel > 255)
					pixel = 255;
				R = pixel;

				pixel = B - G + B + R;
				if (pixel < 0)
					pixel = -pixel;
				pixel = pixel * R / 256;
				if (pixel > 255)
					pixel = 255;
				G = pixel;

				pixel = B - G + B + R;
				if (pixel < 0)
					pixel = -pixel;
				pixel = pixel * G / 256;
				if (pixel > 255)
					pixel = 255;
				B = pixel;

				dst[pos] = Color.rgb(R, G, B);
			}
		}
		Bitmap processBitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.RGB_565);
		processBitmap.setPixels(dst, 0, width, 0, 0, width, height);
		return processBitmap;
	}

	public static Bitmap changeToEclosion(Bitmap bitmap) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		int dst[] = new int[width * height];
		bitmap.getPixels(dst, 0, width, 0, 0, width, height);

		int ratio = width > height ? height * 32768 / width : width * 32768
				/ height;

		int cx = width >> 1;
		int cy = height >> 1;
		int max = cx * cx + cy * cy;
		int min = (int) (max * (1 - 0.5f));
		int diff = max - min;

		int R, G, B;
		int pos, pixColor;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				pos = y * width + x;
				pixColor = dst[pos];
				R = Color.red(pixColor);
				G = Color.green(pixColor);
				B = Color.blue(pixColor);

				int dx = cx - x;
				int dy = cy - y;
				if (width > height) {
					dx = (dx * ratio) >> 15;
				} else {
					dy = (dy * ratio) >> 15;
				}

				int distSq = dx * dx + dy * dy;
				float v = ((float) distSq / diff) * 255;
				R = (int) (R + (v));
				G = (int) (G + (v));
				B = (int) (B + (v));
				R = (R > 255 ? 255 : (R < 0 ? 0 : R));
				G = (G > 255 ? 255 : (G < 0 ? 0 : G));
				B = (B > 255 ? 255 : (B < 0 ? 0 : B));

				dst[pos] = Color.rgb(R, G, B);
			}
		}
		Bitmap processBitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.RGB_565);
		processBitmap.setPixels(dst, 0, width, 0, 0, width, height);
		return processBitmap;
	}

	public static Bitmap changeToGray(Bitmap bitmap) {

		int width, height;
		width = bitmap.getWidth();
		height = bitmap.getHeight();

		Bitmap grayBitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(grayBitmap);
		Paint paint = new Paint();
		paint.setAntiAlias(true);

		// test
		/*
		 * float[] array = {1, 0, 0, 0, 100, 0, 1, 0, 0, 100, 0, 0, 1, 0, 0, 0,
		 * 0, 0, 1, 0}; ColorMatrix colorMatrix = new ColorMatrix(array);
		 */

		ColorMatrix colorMatrix = new ColorMatrix();
		colorMatrix.setSaturation(0);

		ColorMatrixColorFilter filter = new ColorMatrixColorFilter(colorMatrix);

		paint.setColorFilter(filter);
		canvas.drawBitmap(bitmap, 0, 0, paint);

		return grayBitmap;
	}

	public static Bitmap changeToHaha(Bitmap bitmap) {
		int centerX = bitmap.getWidth() / 2;
		int centerY = bitmap.getHeight() / 2;
		float radius = Math.min(centerX * 2 / 3, centerY * 2 / 3);
		float mutiple = 2.0f;
		return changeToHaha(bitmap, radius, centerX, centerY, mutiple);
	}

	public static Bitmap changeToHaha(Bitmap bitmap, float radius, int centerX,
			int centerY, float mutiple) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();

		int[] src = new int[width * height];
		int[] dst = new int[width * height];
		bitmap.getPixels(src, 0, width, 0, 0, width, height);

		int x, y, pos, color;
		int R, G, B;
		int distance;
		int src_x, src_y, src_color;
		int real_radius = (int) (radius / mutiple);

		for (y = 0; y < height; y++) {
			for (x = 0; x < width; x++) {
				pos = y * width + x;
				color = src[pos];

				R = Color.red(color);
				G = Color.green(color);
				B = Color.blue(color);

				distance = (centerX - x) * (centerX - x) + (centerY - y)
						* (centerY - y);
				if (distance < radius * radius) {
					// æ”¾å¤§é•œæ•ˆæžœ
					// src_x = (int)((float)(x-centerX)/xishu + centerX);
					// src_y = (int)((float)(y-centerY)/xishu + centerY);
					// å“ˆå“ˆé•œæ•ˆæžœ
					src_x = (int) ((float) (x - centerX) / mutiple);
					src_y = (int) ((float) (y - centerY) / mutiple);
					src_x = (int) (src_x * (Math.sqrt(distance) / real_radius));
					src_y = (int) (src_y * (Math.sqrt(distance) / real_radius));
					src_x += centerX;
					src_y += centerY;

					src_color = src[src_y * width + src_x];
					R = Color.red(src_color);
					G = Color.green(src_color);
					B = Color.blue(src_color);

					R = Math.min(255, Math.max(0, R));
					G = Math.min(255, Math.max(0, G));
					B = Math.min(255, Math.max(0, B));

					dst[pos] = Color.rgb(R, G, B);
				} else {
					dst[pos] = src[pos];
				}

			}
		}

		Bitmap returnBitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.RGB_565);
		returnBitmap.setPixels(dst, 0, width, 0, 0, width, height);
		return returnBitmap;
	}

	public static Bitmap changeToIce(Bitmap bitmap) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		int dst[] = new int[width * height];
		bitmap.getPixels(dst, 0, width, 0, 0, width, height);
		Log.i("IceStyle", "width=" + width + "; height=" + height);
		int R, G, B, pixel;
		int pos, pixColor;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				pos = y * width + x;
				pixColor = dst[pos];
				R = Color.red(pixColor);
				G = Color.green(pixColor);
				B = Color.blue(pixColor);

				pixel = R - G - B;
				pixel = pixel * 3 / 2;
				if (pixel < 0)
					pixel = -pixel;
				if (pixel > 255)
					pixel = 255;
				R = pixel;

				pixel = G - B - R;
				pixel = pixel * 3 / 2;
				if (pixel < 0)
					pixel = -pixel;
				if (pixel > 255)
					pixel = 255;
				G = pixel;

				pixel = B - R - G;
				pixel = pixel * 3 / 2;
				if (pixel < 0)
					pixel = -pixel;
				if (pixel > 255)
					pixel = 255;
				B = pixel;

				dst[pos] = Color.rgb(R, G, B);
			}
		}
		Bitmap iceBitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.RGB_565);
		iceBitmap.setPixels(dst, 0, width, 0, 0, width, height);
		return iceBitmap;
	}

	public static Bitmap chageToInvert(Bitmap bitmap) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();

		int colorArray[] = new int[width * height];
		int r, g, b, index;
		bitmap.getPixels(colorArray, 0, width, 0, 0, width, height);
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				index = y * width + x;
				r = (colorArray[index] >> 16) & 0xff;
				g = (colorArray[index] >> 8) & 0xff;
				b = colorArray[index] & 0xff;
				colorArray[index] = 0xff000000 | (r << 16) | (g << 8) | b;

				r = (255 - (int) (colorArray[index] & 0x00FF0000) >>> 16);
				g = (255 - (int) (colorArray[index] & 0x0000FF00) >>> 8);
				b = (255 - (int) (colorArray[index] & 0x000000FF));

				colorArray[index] = (255 << 24) + (r << 16) + (g << 8) + b;
			}
		}
		Bitmap returnBitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.RGB_565);
		returnBitmap.setPixels(colorArray, 0, width, 0, 0, width, height);

		return returnBitmap;
	}

	public static Bitmap changeToLight(Bitmap bitmap) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();

		int pixColor = 0;
		int R = 0;
		int G = 0;
		int B = 0;

		int centerX = width / 3;
		int centerY = height / 3;
		int radius = Math.min(centerX, centerY);

		int strength = 150; // å…‰ç…§å¼ºåº¦ 100~150
		int[] pixels = new int[width * height];
		bitmap.getPixels(pixels, 0, width, 0, 0, width, height);

		int pos = 0, distance, result;
		for (int y = 1; y < height - 1; y++) {
			for (int x = 1; x < width - 1; x++) {
				pos = y * width + x;
				pixColor = pixels[pos];

				R = Color.red(pixColor);
				G = Color.green(pixColor);
				B = Color.blue(pixColor);

				distance = (centerY - y) * (centerY - y) + (centerX - x)
						* (centerX - x);
				if (distance < radius * radius) {

					result = (int) (strength * (1.0 - Math.sqrt(distance)
							/ radius));
					R += result;
					G += result;
					B += result;

					R = Math.min(255, Math.max(0, R));
					G = Math.min(255, Math.max(0, G));
					B = Math.min(255, Math.max(0, B));

					pixels[pos] = Color.rgb(R, G, B);
				}
			}
		}

		Bitmap returnBitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.RGB_565);
		returnBitmap.setPixels(pixels, 0, width, 0, 0, width, height);
		return returnBitmap;
	}

	public static Bitmap changeToLomo(Bitmap bitmap) {

		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		int dst[] = new int[width * height];
		bitmap.getPixels(dst, 0, width, 0, 0, width, height);

		int ratio = width > height ? height * 32768 / width : width * 32768
				/ height;
		int cx = width >> 1;
		int cy = height >> 1;
		int max = cx * cx + cy * cy;
		int min = (int) (max * (1 - 0.8f));
		int diff = max - min;

		int ri, gi, bi;
		int dx, dy, distSq, v;

		int R, G, B;

		int value;
		int pos, pixColor;
		int newR, newG, newB;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				pos = y * width + x;
				pixColor = dst[pos];
				R = Color.red(pixColor);
				G = Color.green(pixColor);
				B = Color.blue(pixColor);

				value = R < 128 ? R : 256 - R;
				newR = (value * value * value) / 64 / 256;
				newR = (R < 128 ? newR : 255 - newR);

				value = G < 128 ? G : 256 - G;
				newG = (value * value) / 128;
				newG = (G < 128 ? newG : 255 - newG);

				newB = B / 2 + 0x25;

				dx = cx - x;
				dy = cy - y;
				if (width > height)
					dx = (dx * ratio) >> 15;
				else
					dy = (dy * ratio) >> 15;

				distSq = dx * dx + dy * dy;
				if (distSq > min) {
					v = ((max - distSq) << 8) / diff;
					v *= v;

					ri = (int) (newR * v) >> 16;
					gi = (int) (newG * v) >> 16;
					bi = (int) (newB * v) >> 16;

					newR = ri > 255 ? 255 : (ri < 0 ? 0 : ri);
					newG = gi > 255 ? 255 : (gi < 0 ? 0 : gi);
					newB = bi > 255 ? 255 : (bi < 0 ? 0 : bi);
				}

				dst[pos] = Color.rgb(newR, newG, newB);
			}
		}

		Bitmap acrossFlushBitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.RGB_565);
		acrossFlushBitmap.setPixels(dst, 0, width, 0, 0, width, height);
		return acrossFlushBitmap;
	}

	public static Bitmap changeToMolten(Bitmap bitmap) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		int dst[] = new int[width * height];
		bitmap.getPixels(dst, 0, width, 0, 0, width, height);

		int R, G, B, pixel;
		int pos, pixColor;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				pos = y * width + x;
				pixColor = dst[pos];
				R = Color.red(pixColor); // (color >> 16) & 0xFF
				G = Color.green(pixColor); // (color >> 8) & 0xFF;
				B = Color.blue(pixColor); // color & 0xFF

				pixel = R * 128 / (G + B + 1);
				if (pixel < 0)
					pixel = -pixel;
				if (pixel > 255)
					pixel = 255;
				R = pixel;

				pixel = G * 128 / (B + R + 1);
				if (pixel < 0)
					pixel = -pixel;
				if (pixel > 255)
					pixel = 255;
				G = pixel;

				pixel = G * 128 / (B + R + 1);
				if (pixel < 0)
					pixel = -pixel;
				if (pixel > 255)
					pixel = 255;
				B = pixel;

				dst[pos] = Color.rgb(R, G, B);
			}
		}
		Bitmap processBitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.RGB_565);
		processBitmap.setPixels(dst, 0, width, 0, 0, width, height);

		return processBitmap;
	}
	
	public static Bitmap changeToOil(Bitmap bitmap) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();

		int dst[] = new int[width * height];
		bitmap.getPixels(dst, 0, width, 0, 0, width, height);

		int color = 0;
		Random random = new Random();
		int iModel = 4;
		int i = width - iModel;
		int pos = 0, iPos;

		while (i > 1) {
			int j = height - iModel;
			while (j > 1) {
				iPos = random.nextInt(100000000) % iModel;
				pos = (j + iPos) * width + (i + iPos);
				color = dst[pos];
				pos = j * width + i;
				dst[pos] = color;
				j--;
			}
			i--;
		}

		Bitmap returnBitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.RGB_565);
		returnBitmap.setPixels(dst, 0, width, 0, 0, width, height);
		return returnBitmap;
	}
	
	public static Bitmap changeToOld(Bitmap bitmap) {

		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		Log.i("OldFilter", "width=" + width + "; height=" + height);
		Bitmap returnBitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.RGB_565);
		int pixColor = 0;
		int pixR = 0;
		int pixG = 0;
		int pixB = 0;
		int newR = 0;
		int newG = 0;
		int newB = 0;
		int[] pixels = new int[width * height];
		bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
		for (int i = 0; i < height; i++) {
			for (int k = 0; k < width; k++) {
				pixColor = pixels[width * i + k];
				pixR = Color.red(pixColor);
				pixG = Color.green(pixColor);
				pixB = Color.blue(pixColor);
				newR = (int) (0.393 * pixR + 0.769 * pixG + 0.189 * pixB);
				newG = (int) (0.349 * pixR + 0.686 * pixG + 0.168 * pixB);
				newB = (int) (0.272 * pixR + 0.534 * pixG + 0.131 * pixB);
				int newColor = Color.argb(255, newR > 255 ? 255 : newR,
						newG > 255 ? 255 : newG, newB > 255 ? 255 : newB);
				pixels[width * i + k] = newColor;
			}
		}

		returnBitmap.setPixels(pixels, 0, width, 0, 0, width, height);
		return returnBitmap;
	}
	
	public static Bitmap changeToRelief(Bitmap mBitmap) {
		int width = mBitmap.getWidth();
		int heigth = mBitmap.getHeight();

		int preColor = 0;
		int prepreColor = 0;
		preColor = mBitmap.getPixel(0, 0);

		int dst[] = new int[heigth * width];
		mBitmap.getPixels(dst, 0, width, 0, 0, width, heigth);
		int pos, curr_color, R, G, B;
		for (int y = 0; y < heigth; y++) {
			for (int x = 0; x < width; x++) {
				pos = y * width + x;
				curr_color = dst[pos];
				R = Color.red(curr_color) - Color.red(prepreColor) + 128;
				G = Color.green(curr_color) - Color.red(prepreColor) + 128;
				B = Color.blue(curr_color) - Color.blue(prepreColor) + 128;

				dst[pos] = Color.rgb(R, G, B);
				prepreColor = preColor;
				preColor = curr_color;
			}
		}
		Bitmap bmpReturn = Bitmap.createBitmap(width, heigth, Bitmap.Config.RGB_565);
		bmpReturn.setPixels(dst, 0, width, 0, 0, width, heigth);

		Canvas c = new Canvas(bmpReturn);
		Paint paint = new Paint();
		ColorMatrix cm = new ColorMatrix();
		cm.setSaturation(0);
		ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
		paint.setColorFilter(f);
		c.drawBitmap(bmpReturn, 0, 0, paint);

		return bmpReturn;

	}
	
	public static Bitmap changeToSoftness(Bitmap bitmap){
		int width = bitmap.getWidth();
    	int height = bitmap.getHeight();
    	int dst[] = new int[width*height];
    	bitmap.getPixels(dst, 0, width, 0, 0, width, height);
    	
    	int R, G, B, pixel;
    	int pos, pixColor;
    	for(int y=0; y<height; y++){
    		for(int x=0; x<width; x++){
    			pos = y*width + x;
    			pixColor = dst[pos];
    			R = Color.red(pixColor);		//(color >> 16) & 0xFF
    			G = Color.green(pixColor);		//(color >> 8) & 0xFF;
    			B = Color.blue(pixColor);		//color & 0xFF
    			 pixel = 255 - (255-R)*(255-R)/255;
                 if (pixel < 0)
                         pixel = -pixel;
                 pixel = pixel * R / 256;
                 if (pixel > 255)
                         pixel = 255;
                 R = pixel;
                 
                 pixel = 255 - (255-G)*(255-G)/255;
                 if (pixel < 0)
                         pixel = -pixel;
                 pixel = pixel * R / 256;
                 if (pixel > 255)
                         pixel = 255;
                 G = pixel;
                 
                 pixel = 255 - (255-B)*(255-B)/255;
                 if (pixel < 0)
                         pixel = -pixel;
                 pixel = pixel * G / 256;
                 if (pixel > 255)
                         pixel = 255;
                 B = pixel;
                
                dst[pos] = Color.rgb(R, G, B);
    		}
    	}
    	Bitmap processBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
    	processBitmap.setPixels(dst, 0, width, 0, 0, width, height);
    	
    	return processBitmap;
	}
	
}
