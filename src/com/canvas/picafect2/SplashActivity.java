package com.canvas.picafect2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class SplashActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.splash);
		Thread timer = new Thread(){
			public void run() {
				try{
					sleep(2000);
					Intent intent = new Intent("com.canvas.picafect2.MENU");
					startActivity(intent);
				}
				catch(InterruptedException e){
					e.printStackTrace();
				}
				finally{
					finish();
				}
			}
		};
		timer.start();
	}
}
