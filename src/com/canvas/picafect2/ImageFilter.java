package com.canvas.picafect2;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.Log;

public class ImageFilter {

	public static final int FLIP_VERTICAL = 1;
	public static final int FLIP_HORIZONTAL = 2;
	static Bitmap bitmap;
	public static final int COLOR_MIN = 0x00;
	public static final int COLOR_MAX = 0xFF;

	public static Bitmap getBitmap() {
		return bitmap;
	}

	public static void setBitmap(Bitmap bmp) {
		bitmap = bmp;
	}

	public static Bitmap applyInvertEffect(Bitmap src) {
		// create new bitmap with the same settings as source bitmap
		Bitmap bmOut = Bitmap.createBitmap(src.getWidth(), src.getHeight(),
				src.getConfig());
		// color info
		int A, R, G, B;
		int pixelColor;
		// image size
		int height = src.getHeight();
		int width = src.getWidth();

		// scan through every pixel
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				// get one pixel
				pixelColor = src.getPixel(x, y);
				// saving alpha channel
				A = Color.alpha(pixelColor);
				// inverting byte for each R/G/B channel
				R = 255 - Color.red(pixelColor);
				G = 255 - Color.green(pixelColor);
				B = 255 - Color.blue(pixelColor);
				// set newly-inverted pixel to output image
				bmOut.setPixel(x, y, Color.argb(A, R, G, B));
			}
		}

		// return final bitmap
		return bmOut;
	}

	public static Bitmap applyGreyscaleEffect(Bitmap src) {
		// constant factors
		final double GS_RED = 0.299;
		final double GS_GREEN = 0.587;
		final double GS_BLUE = 0.114;

		// create output bitmap
		Bitmap bmOut = Bitmap.createBitmap(src.getWidth(), src.getHeight(),
				src.getConfig());
		// pixel information
		int A, R, G, B;
		int pixel;

		// get image size
		int width = src.getWidth();
		int height = src.getHeight();

		// scan through every single pixel
		for (int x = 0; x < width; ++x) {
			for (int y = 0; y < height; ++y) {
				// get one pixel color
				pixel = src.getPixel(x, y);
				// retrieve color of all channels
				A = Color.alpha(pixel);
				R = Color.red(pixel);
				G = Color.green(pixel);
				B = Color.blue(pixel);
				// take conversion up to one single value
				R = G = B = (int) (GS_RED * R + GS_GREEN * G + GS_BLUE * B);
				// set new pixel color to output bitmap
				bmOut.setPixel(x, y, Color.argb(A, R, G, B));
			}
		}

		// return final image
		return bmOut;
	}

	// Gamma Image (R, G, B) = (1.8, 1.8, 1.8)
	public static Bitmap applyGammaEffect(Bitmap src, double red, double green,
			double blue) {
		// create output image
		Bitmap bmOut = Bitmap.createBitmap(src.getWidth(), src.getHeight(),
				src.getConfig());
		// get image size
		int width = src.getWidth();
		int height = src.getHeight();
		// color information
		int A, R, G, B;
		int pixel;
		// constant value curve
		final int MAX_SIZE = 256;
		final double MAX_VALUE_DBL = 255.0;
		final int MAX_VALUE_INT = 255;
		final double REVERSE = 1.0;

		// gamma arrays
		int[] gammaR = new int[MAX_SIZE];
		int[] gammaG = new int[MAX_SIZE];
		int[] gammaB = new int[MAX_SIZE];

		// setting values for every gamma channels
		for (int i = 0; i < MAX_SIZE; ++i) {
			gammaR[i] = (int) Math.min(
					MAX_VALUE_INT,
					(int) ((MAX_VALUE_DBL * Math.pow(i / MAX_VALUE_DBL, REVERSE
							/ red)) + 0.5));
			gammaG[i] = (int) Math.min(
					MAX_VALUE_INT,
					(int) ((MAX_VALUE_DBL * Math.pow(i / MAX_VALUE_DBL, REVERSE
							/ green)) + 0.5));
			gammaB[i] = (int) Math.min(
					MAX_VALUE_INT,
					(int) ((MAX_VALUE_DBL * Math.pow(i / MAX_VALUE_DBL, REVERSE
							/ blue)) + 0.5));
		}

		// apply gamma table
		for (int x = 0; x < width; ++x) {
			for (int y = 0; y < height; ++y) {
				// get pixel color
				pixel = src.getPixel(x, y);
				A = Color.alpha(pixel);
				// look up gamma
				R = gammaR[Color.red(pixel)];
				G = gammaG[Color.green(pixel)];
				B = gammaB[Color.blue(pixel)];
				// set new color to output bitmap
				bmOut.setPixel(x, y, Color.argb(A, R, G, B));
			}
		}

		// return final image
		return bmOut;
	}

	public static Bitmap applyColorFilterEffect(Bitmap src, double red,
			double green, double blue) {
		// image size
		int width = src.getWidth();
		int height = src.getHeight();
		// create output bitmap
		Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());
		// color information
		int A, R, G, B;
		int pixel;

		// scan through all pixels
		for (int x = 0; x < width; ++x) {
			for (int y = 0; y < height; ++y) {
				// get pixel color
				pixel = src.getPixel(x, y);
				// apply filtering on each channel R, G, B
				A = Color.alpha(pixel);
				R = (int) (Color.red(pixel) * red);
				G = (int) (Color.green(pixel) * green);
				B = (int) (Color.blue(pixel) * blue);
				// set new color pixel to output bitmap
				bmOut.setPixel(x, y, Color.argb(A, R, G, B));
			}
		}

		// return final image
		return bmOut;
	}

	public static Bitmap applySepiaToningEffect(Bitmap src, int depth,
			double red, double green, double blue) {
		// image size
		int width = src.getWidth();
		int height = src.getHeight();
		// create output bitmap
		Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());
		// constant grayscale
		final double GS_RED = 0.3;
		final double GS_GREEN = 0.59;
		final double GS_BLUE = 0.11;
		// color information
		int A, R, G, B;
		int pixel;

		// scan through all pixels
		for (int x = 0; x < width; ++x) {
			for (int y = 0; y < height; ++y) {
				// get pixel color
				pixel = src.getPixel(x, y);
				// get color on each channel
				A = Color.alpha(pixel);
				R = Color.red(pixel);
				G = Color.green(pixel);
				B = Color.blue(pixel);
				// apply grayscale sample
				B = G = R = (int) (GS_RED * R + GS_GREEN * G + GS_BLUE * B);

				// apply intensity level for sepid-toning on each channel
				R += (depth * red);
				if (R > 255) {
					R = 255;
				}

				G += (depth * green);
				if (G > 255) {
					G = 255;
				}

				B += (depth * blue);
				if (B > 255) {
					B = 255;
				}

				// set new pixel color to output image
				bmOut.setPixel(x, y, Color.argb(A, R, G, B));
			}
		}

		// return final image
		return bmOut;
	}

	public static Bitmap applyDecreaseColorDepthEffect(Bitmap src, int bitOffset) {
		// get image size
		int width = src.getWidth();
		int height = src.getHeight();
		// create output bitmap
		Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());
		// color information
		int A, R, G, B;
		int pixel;

		// scan through all pixels
		for (int x = 0; x < width; ++x) {
			for (int y = 0; y < height; ++y) {
				// get pixel color
				pixel = src.getPixel(x, y);
				A = Color.alpha(pixel);
				R = Color.red(pixel);
				G = Color.green(pixel);
				B = Color.blue(pixel);

				// round-off color offset
				R = ((R + (bitOffset / 2))
						- ((R + (bitOffset / 2)) % bitOffset) - 1);
				if (R < 0) {
					R = 0;
				}
				G = ((G + (bitOffset / 2))
						- ((G + (bitOffset / 2)) % bitOffset) - 1);
				if (G < 0) {
					G = 0;
				}
				B = ((B + (bitOffset / 2))
						- ((B + (bitOffset / 2)) % bitOffset) - 1);
				if (B < 0) {
					B = 0;
				}

				// set pixel color to output bitmap
				bmOut.setPixel(x, y, Color.argb(A, R, G, B));
			}
		}

		// return final image
		return bmOut;
	}

	public static Bitmap applyContrastEffect(Bitmap src, double value) {
		// image size
		int width = src.getWidth();
		int height = src.getHeight();
		// create output bitmap
		Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());
		// color information
		int A, R, G, B;
		int pixel;
		// get contrast value
		double contrast = Math.pow((100 + value) / 100, 2);

		// scan through all pixels
		for (int x = 0; x < width; ++x) {
			for (int y = 0; y < height; ++y) {
				// get pixel color
				pixel = src.getPixel(x, y);
				A = Color.alpha(pixel);
				// apply filter contrast for every channel R, G, B
				R = Color.red(pixel);
				R = (int) (((((R / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
				if (R < 0) {
					R = 0;
				} else if (R > 255) {
					R = 255;
				}

				G = Color.red(pixel);
				G = (int) (((((G / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
				if (G < 0) {
					G = 0;
				} else if (G > 255) {
					G = 255;
				}

				B = Color.red(pixel);
				B = (int) (((((B / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
				if (B < 0) {
					B = 0;
				} else if (B > 255) {
					B = 255;
				}

				// set new pixel color to output bitmap
				bmOut.setPixel(x, y, Color.argb(A, R, G, B));
			}
		}

		// return final image
		return bmOut;
	}

	public static Bitmap applyBrightnessEffect(Bitmap src, int value) {
		// image size
		int width = src.getWidth();
		int height = src.getHeight();
		// create output bitmap
		Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());
		// color information
		int A, R, G, B;
		int pixel;

		// scan through all pixels
		for (int x = 0; x < width; ++x) {
			for (int y = 0; y < height; ++y) {
				// get pixel color
				pixel = src.getPixel(x, y);
				A = Color.alpha(pixel);
				R = Color.red(pixel);
				G = Color.green(pixel);
				B = Color.blue(pixel);

				// increase/decrease each channel
				R += value;
				if (R > 255) {
					R = 255;
				} else if (R < 0) {
					R = 0;
				}

				G += value;
				if (G > 255) {
					G = 255;
				} else if (G < 0) {
					G = 0;
				}

				B += value;
				if (B > 255) {
					B = 255;
				} else if (B < 0) {
					B = 0;
				}

				// apply new pixel color to output bitmap
				bmOut.setPixel(x, y, Color.argb(A, R, G, B));
			}
		}

		// return final image
		return bmOut;
	}

	public static Bitmap applyGaussianBlurEffect(Bitmap src) {
		double[][] GaussianBlurConfig = new double[][] { { 1, 2, 1 },
				{ 2, 4, 2 }, { 1, 2, 1 } };
		ConvolutionMatrix convMatrix = new ConvolutionMatrix(3);
		convMatrix.applyConfig(GaussianBlurConfig);
		convMatrix.Factor = 16;
		convMatrix.Offset = 0;
		return ConvolutionMatrix.computeConvolution3x3(src, convMatrix);
	}

	public static Bitmap applySharpenEffect(Bitmap src, double weight) {
		double[][] SharpConfig = new double[][] { { 0, -2, 0 },
				{ -2, weight, -2 }, { 0, -2, 0 } };
		ConvolutionMatrix convMatrix = new ConvolutionMatrix(3);
		convMatrix.applyConfig(SharpConfig);
		convMatrix.Factor = weight - 8;
		return ConvolutionMatrix.computeConvolution3x3(src, convMatrix);
	}

	public static Bitmap applyMeanRemovalEffect(Bitmap src) {
		double[][] MeanRemovalConfig = new double[][] { { -1, -1, -1 },
				{ -1, 9, -1 }, { -1, -1, -1 } };
		ConvolutionMatrix convMatrix = new ConvolutionMatrix(3);
		convMatrix.applyConfig(MeanRemovalConfig);
		convMatrix.Factor = 1;
		convMatrix.Offset = 0;
		return ConvolutionMatrix.computeConvolution3x3(src, convMatrix);
	}

	public static Bitmap applySmoothEffect(Bitmap src, double value) {
		ConvolutionMatrix convMatrix = new ConvolutionMatrix(3);
		convMatrix.setAll(1);
		convMatrix.Matrix[1][1] = value;
		convMatrix.Factor = value + 8;
		convMatrix.Offset = 1;
		return ConvolutionMatrix.computeConvolution3x3(src, convMatrix);
	}

	public static Bitmap applyEmbossEffect(Bitmap src) {
		double[][] EmbossConfig = new double[][] { { -1, 0, -1 }, { 0, 4, 0 },
				{ -1, 0, -1 } };
		ConvolutionMatrix convMatrix = new ConvolutionMatrix(3);
		convMatrix.applyConfig(EmbossConfig);
		convMatrix.Factor = 1;
		convMatrix.Offset = 127;
		return ConvolutionMatrix.computeConvolution3x3(src, convMatrix);
	}

	public static Bitmap applyEngraveEffect(Bitmap src) {
		ConvolutionMatrix convMatrix = new ConvolutionMatrix(3);
		convMatrix.setAll(0);
		convMatrix.Matrix[0][0] = -2;
		convMatrix.Matrix[1][1] = 2;
		convMatrix.Factor = 1;
		convMatrix.Offset = 95;
		return ConvolutionMatrix.computeConvolution3x3(src, convMatrix);
	}

	public static Bitmap applyBoostEffect(Bitmap src, int type, float percent) {
		int width = src.getWidth();
		int height = src.getHeight();
		Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());

		int A, R, G, B;
		int pixel;

		for (int x = 0; x < width; ++x) {
			for (int y = 0; y < height; ++y) {
				pixel = src.getPixel(x, y);
				A = Color.alpha(pixel);
				R = Color.red(pixel);
				G = Color.green(pixel);
				B = Color.blue(pixel);
				if (type == 1) {
					R = (int) (R * (1 + percent));
					if (R > 255)
						R = 255;
				} else if (type == 2) {
					G = (int) (G * (1 + percent));
					if (G > 255)
						G = 255;
				} else if (type == 3) {
					B = (int) (B * (1 + percent));
					if (B > 255)
						B = 255;
				}
				bmOut.setPixel(x, y, Color.argb(A, R, G, B));
			}
		}
		return bmOut;
	}

	public static Bitmap applyRoundCornerEffect(Bitmap src, float round) {
		// image size
		int width = src.getWidth();
		int height = src.getHeight();
		// create bitmap output
		Bitmap result = Bitmap.createBitmap(width, height,
				Bitmap.Config.ARGB_8888);
		// set canvas for painting
		Canvas canvas = new Canvas(result);
		canvas.drawARGB(0, 0, 0, 0);

		// config paint
		final Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(Color.BLACK);

		// config rectangle for embedding
		final Rect rect = new Rect(0, 0, width, height);
		final RectF rectF = new RectF(rect);

		// draw rect to canvas
		canvas.drawRoundRect(rectF, round, round, paint);

		// create Xfer mode
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		// draw source image to canvas
		canvas.drawBitmap(src, rect, rect, paint);

		// return final image
		return result;
	}

	public static Bitmap applyWaterMarkEffect(Bitmap src, String watermark,
			int x, int y, int color, int alpha, int size, boolean underline) {
		int w = src.getWidth();
		int h = src.getHeight();
		Bitmap result = Bitmap.createBitmap(w, h, src.getConfig());

		Canvas canvas = new Canvas(result);
		canvas.drawBitmap(src, 0, 0, null);

		Paint paint = new Paint();
		paint.setColor(color);
		paint.setAlpha(alpha);
		paint.setTextSize(size);
		paint.setAntiAlias(true);
		paint.setUnderlineText(underline);
		canvas.drawText(watermark, x, y, paint);

		return result;
	}

	public static final double PI = 3.14159d;
	public static final double FULL_CIRCLE_DEGREE = 360d;
	public static final double HALF_CIRCLE_DEGREE = 180d;
	public static final double RANGE = 256d;

	public static Bitmap applyTintEffect(Bitmap src, int degree) {

		int width = src.getWidth();
		int height = src.getHeight();

		int[] pix = new int[width * height];
		src.getPixels(pix, 0, width, 0, 0, width, height);

		int RY, GY, BY, RYY, GYY, BYY, R, G, B, Y;
		double angle = (PI * (double) degree) / HALF_CIRCLE_DEGREE;

		int S = (int) (RANGE * Math.sin(angle));
		int C = (int) (RANGE * Math.cos(angle));

		for (int y = 0; y < height; y++)
			for (int x = 0; x < width; x++) {
				int index = y * width + x;
				int r = (pix[index] >> 16) & 0xff;
				int g = (pix[index] >> 8) & 0xff;
				int b = pix[index] & 0xff;
				RY = (70 * r - 59 * g - 11 * b) / 100;
				GY = (-30 * r + 41 * g - 11 * b) / 100;
				BY = (-30 * r - 59 * g + 89 * b) / 100;
				Y = (30 * r + 59 * g + 11 * b) / 100;
				RYY = (S * BY + C * RY) / 256;
				BYY = (C * BY - S * RY) / 256;
				GYY = (-51 * RYY - 19 * BYY) / 100;
				R = Y + RYY;
				R = (R < 0) ? 0 : ((R > 255) ? 255 : R);
				G = Y + GYY;
				G = (G < 0) ? 0 : ((G > 255) ? 255 : G);
				B = Y + BYY;
				B = (B < 0) ? 0 : ((B > 255) ? 255 : B);
				pix[index] = 0xff000000 | (R << 16) | (G << 8) | B;
			}

		Bitmap outBitmap = Bitmap.createBitmap(width, height, src.getConfig());
		outBitmap.setPixels(pix, 0, width, 0, 0, width, height);

		pix = null;

		return outBitmap;
	}

	public static Bitmap applyFleaEffect(Bitmap source) {
		// get image size
		int width = source.getWidth();
		int height = source.getHeight();
		int[] pixels = new int[width * height];
		// get pixel array from source
		source.getPixels(pixels, 0, width, 0, 0, width, height);
		// a random object
		Random random = new Random();

		int index = 0;
		// iteration through pixels
		for (int y = 0; y < height; ++y) {
			for (int x = 0; x < width; ++x) {
				// get current index in 2D-matrix
				index = y * width + x;
				// get random color
				int randColor = Color.rgb(random.nextInt(COLOR_MAX),
						random.nextInt(COLOR_MAX), random.nextInt(COLOR_MAX));
				// OR
				pixels[index] |= randColor;
			}
		}
		// output bitmap
		Bitmap bmOut = Bitmap.createBitmap(width, height, source.getConfig());
		bmOut.setPixels(pixels, 0, width, 0, 0, width, height);
		return bmOut;
	}

	public static Bitmap applyBlackFilter(Bitmap source) {
		// get image size
		int width = source.getWidth();
		int height = source.getHeight();
		int[] pixels = new int[width * height];
		// get pixel array from source
		source.getPixels(pixels, 0, width, 0, 0, width, height);
		// random object
		Random random = new Random();

		int R, G, B, index = 0, thresHold = 0;
		// iteration through pixels
		for (int y = 0; y < height; ++y) {
			for (int x = 0; x < width; ++x) {
				// get current index in 2D-matrix
				index = y * width + x;
				// get color
				R = Color.red(pixels[index]);
				G = Color.green(pixels[index]);
				B = Color.blue(pixels[index]);
				// generate threshold
				thresHold = random.nextInt(COLOR_MAX);
				if (R < thresHold && G < thresHold && B < thresHold) {
					pixels[index] = Color.rgb(COLOR_MIN, COLOR_MIN, COLOR_MIN);
				}
			}
		}
		// output bitmap
		Bitmap bmOut = Bitmap.createBitmap(width, height,
				Bitmap.Config.ARGB_8888);
		bmOut.setPixels(pixels, 0, width, 0, 0, width, height);
		return bmOut;
	}

	public static Bitmap applySnowEffect(Bitmap source) {
		// get image size
		int width = source.getWidth();
		int height = source.getHeight();
		int[] pixels = new int[width * height];
		// get pixel array from source
		source.getPixels(pixels, 0, width, 0, 0, width, height);
		// random object
		Random random = new Random();

		int R, G, B, index = 0, thresHold = 50;
		// iteration through pixels
		for (int y = 0; y < height; ++y) {
			for (int x = 0; x < width; ++x) {
				// get current index in 2D-matrix
				index = y * width + x;
				// get color
				R = Color.red(pixels[index]);
				G = Color.green(pixels[index]);
				B = Color.blue(pixels[index]);
				// generate threshold
				thresHold = random.nextInt(COLOR_MAX);
				if (R > thresHold && G > thresHold && B > thresHold) {
					pixels[index] = Color.rgb(COLOR_MAX, COLOR_MAX, COLOR_MAX);
				}
			}
		}
		// output bitmap
		Bitmap bmOut = Bitmap
				.createBitmap(width, height, Bitmap.Config.RGB_565);
		bmOut.setPixels(pixels, 0, width, 0, 0, width, height);
		return bmOut;
	}

	public static Bitmap applyShadingFilter(Bitmap source, int shadingColor) {
		// get image size
		int width = source.getWidth();
		int height = source.getHeight();
		int[] pixels = new int[width * height];
		// get pixel array from source
		source.getPixels(pixels, 0, width, 0, 0, width, height);

		int index = 0;
		// iteration through pixels
		for (int y = 0; y < height; ++y) {
			for (int x = 0; x < width; ++x) {
				// get current index in 2D-matrix
				index = y * width + x;
				// AND
				pixels[index] &= shadingColor;
			}
		}
		// output bitmap
		Bitmap bmOut = Bitmap.createBitmap(width, height,
				Bitmap.Config.ARGB_8888);
		bmOut.setPixels(pixels, 0, width, 0, 0, width, height);
		return bmOut;
	}

	public static Bitmap applySaturationFilter(Bitmap source, int level) {
		// get image size
		int width = source.getWidth();
		int height = source.getHeight();
		int[] pixels = new int[width * height];
		float[] HSV = new float[3];
		// get pixel array from source
		source.getPixels(pixels, 0, width, 0, 0, width, height);

		int index = 0;
		// iteration through pixels
		for (int y = 0; y < height; ++y) {
			for (int x = 0; x < width; ++x) {
				// get current index in 2D-matrix
				index = y * width + x;
				// convert to HSV
				Color.colorToHSV(pixels[index], HSV);
				// increase Saturation level
				HSV[1] *= level;
				HSV[1] = (float) Math.max(0.0, Math.min(HSV[1], 1.0));
				// take color back
				pixels[index] |= Color.HSVToColor(HSV);
			}
		}
		// output bitmap
		Bitmap bmOut = Bitmap.createBitmap(width, height,
				Bitmap.Config.ARGB_8888);
		bmOut.setPixels(pixels, 0, width, 0, 0, width, height);
		return bmOut;
	}

	public static Bitmap applyHueFilter(Bitmap source, int level) {
		// get image size
		int width = source.getWidth();
		int height = source.getHeight();
		int[] pixels = new int[width * height];
		float[] HSV = new float[3];
		// get pixel array from source
		source.getPixels(pixels, 0, width, 0, 0, width, height);

		int index = 0;
		// iteration through pixels
		for (int y = 0; y < height; ++y) {
			for (int x = 0; x < width; ++x) {
				// get current index in 2D-matrix
				index = y * width + x;
				// convert to HSV
				Color.colorToHSV(pixels[index], HSV);
				// increase Saturation level
				HSV[0] *= level;
				HSV[0] = (float) Math.max(0.0, Math.min(HSV[0], 360.0));
				// take color back
				pixels[index] |= Color.HSVToColor(HSV);
			}
		}
		// output bitmap
		Bitmap bmOut = Bitmap.createBitmap(width, height,
				Bitmap.Config.ARGB_8888);
		bmOut.setPixels(pixels, 0, width, 0, 0, width, height);
		return bmOut;
	}

	public static Bitmap applyReflection(Bitmap originalImage) {
		// gap space between original and reflected
		final int reflectionGap = 4;
		// get image size
		int width = originalImage.getWidth();
		int height = originalImage.getHeight();

		// this will not scale but will flip on the Y axis
		Matrix matrix = new Matrix();
		matrix.preScale(1, -1);

		// create a Bitmap with the flip matrix applied to it.
		// we only want the bottom half of the image
		Bitmap reflectionImage = Bitmap.createBitmap(originalImage, 0,
				height / 2, width, height / 2, matrix, false);

		// create a new bitmap with same width but taller to fit reflection
		Bitmap bitmapWithReflection = Bitmap.createBitmap(width,
				(height + height / 2), Bitmap.Config.ARGB_8888);

		// create a new Canvas with the bitmap that's big enough for
		// the image plus gap plus reflection
		Canvas canvas = new Canvas(bitmapWithReflection);
		// draw in the original image
		canvas.drawBitmap(originalImage, 0, 0, null);
		// draw in the gap
		Paint defaultPaint = new Paint();
		canvas.drawRect(0, height, width, height + reflectionGap, defaultPaint);
		// draw in the reflection
		canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);

		// create a shader that is a linear gradient that covers the reflection
		Paint paint = new Paint();
		LinearGradient shader = new LinearGradient(0,
				originalImage.getHeight(), 0, bitmapWithReflection.getHeight()
						+ reflectionGap, 0x70ffffff, 0x00ffffff,
				Shader.TileMode.CLAMP);
		// set the paint to use this shader (linear gradient)
		paint.setShader(shader);
		// set the Transfer mode to be porter duff and destination in
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
		// draw a rectangle using the paint with our linear gradient
		canvas.drawRect(0, height, width, bitmapWithReflection.getHeight()
				+ reflectionGap, paint);

		return bitmapWithReflection;
	}

	// Api Effects

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
					// 放大镜效果
					// src_x = (int)((float)(x-centerX)/xishu + centerX);
					// src_y = (int)((float)(y-centerY)/xishu + centerY);
					// 哈哈镜效果
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

		int strength = 150; // 光照强度 100~150
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
		Bitmap bmpReturn = Bitmap.createBitmap(width, heigth,
				Bitmap.Config.RGB_565);
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

	public static Bitmap changeToSoftness(Bitmap bitmap) {
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
				pixel = 255 - (255 - R) * (255 - R) / 255;
				if (pixel < 0)
					pixel = -pixel;
				pixel = pixel * R / 256;
				if (pixel > 255)
					pixel = 255;
				R = pixel;

				pixel = 255 - (255 - G) * (255 - G) / 255;
				if (pixel < 0)
					pixel = -pixel;
				pixel = pixel * R / 256;
				if (pixel > 255)
					pixel = 255;
				G = pixel;

				pixel = 255 - (255 - B) * (255 - B) / 255;
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
				Bitmap.Config.ARGB_8888);
		processBitmap.setPixels(dst, 0, width, 0, 0, width, height);

		return processBitmap;
	}

	public static Bitmap flip(Bitmap src, int type) {
		// create new matrix for transformation
		Matrix matrix = new Matrix();
		// if vertical
		if (type == FLIP_VERTICAL) {
			// y = y * -1
			matrix.preScale(1.0f, -1.0f);
		}
		// if horizonal
		else if (type == FLIP_HORIZONTAL) {
			// x = x * -1
			matrix.preScale(-1.0f, 1.0f);
			// unknown type
		} else {
			return null;
		}

		// return transformed image
		return Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(),
				matrix, true);
	}

	public static Bitmap adjustBrightness(Bitmap src, int value) {
		// image size
		int width = src.getWidth();
		int height = src.getHeight();
		// create output bitmap
		Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());
		// color information
		int A, R, G, B;
		int pixel;

		// scan through all pixels
		for (int x = 0; x < width; ++x) {
			for (int y = 0; y < height; ++y) {
				// get pixel color
				pixel = src.getPixel(x, y);
				A = Color.alpha(pixel);
				R = Color.red(pixel);
				G = Color.green(pixel);
				B = Color.blue(pixel);

				// increase/decrease each channel
				R += value;
				if (R > 255) {
					R = 255;
				} else if (R < 0) {
					R = 0;
				}

				G += value;
				if (G > 255) {
					G = 255;
				} else if (G < 0) {
					G = 0;
				}

				B += value;
				if (B > 255) {
					B = 255;
				} else if (B < 0) {
					B = 0;
				}

				// apply new pixel color to output bitmap
				bmOut.setPixel(x, y, Color.argb(A, R, G, B));
			}
		}

		// return final image
		return bmOut;
	}

	public static Bitmap rotate(Bitmap src, float degree) {
		// create new matrix
		Matrix matrix = new Matrix();
		// setup rotation degree
		matrix.postRotate(degree);

		// return new bitmap rotated using matrix
		return Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(),
				matrix, true);
	}

	private static Bitmap adjustedContrast(Bitmap src, double value) {
		// image size
		int width = src.getWidth();
		int height = src.getHeight();
		// create output bitmap

		// create a mutable empty bitmap
		Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());

		// create a canvas so that we can draw the bmOut Bitmap from source
		// bitmap
		Canvas c = new Canvas();
		c.setBitmap(bmOut);

		// draw bitmap to bmOut from src bitmap so we can modify it
		c.drawBitmap(src, 0, 0, new Paint(Color.BLACK));

		// color information
		int A, R, G, B;
		int pixel;
		// get contrast value
		double contrast = Math.pow((100 + value) / 100, 2);

		// scan through all pixels
		for (int x = 0; x < width; ++x) {
			for (int y = 0; y < height; ++y) {
				// get pixel color
				pixel = src.getPixel(x, y);
				A = Color.alpha(pixel);
				// apply filter contrast for every channel R, G, B
				R = Color.red(pixel);
				R = (int) (((((R / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
				if (R < 0) {
					R = 0;
				} else if (R > 255) {
					R = 255;
				}

				G = Color.green(pixel);
				G = (int) (((((G / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
				if (G < 0) {
					G = 0;
				} else if (G > 255) {
					G = 255;
				}

				B = Color.blue(pixel);
				B = (int) (((((B / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
				if (B < 0) {
					B = 0;
				} else if (B > 255) {
					B = 255;
				}

				// set new pixel color to output bitmap
				bmOut.setPixel(x, y, Color.argb(A, R, G, B));
			}
		}
		return bmOut;
	}
}
