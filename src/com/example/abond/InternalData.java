package com.example.abond;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class InternalData extends Activity implements OnClickListener{
	
	EditText sharedData;
	TextView dataResults;
	Button bSave, bLoad;
	String FILENAME = "InternalString";
	FileOutputStream fos;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sharedpreferences);
		setupvariables();
	}
	
	private void setupvariables() {
		// TODO Auto-generated method stub
		sharedData = (EditText)findViewById(R.id.etSharedPrefs);
		dataResults = (TextView)findViewById(R.id.tvLoadSharedPrefs);
		bSave = (Button)findViewById(R.id.bSave);
		bLoad = (Button)findViewById(R.id.bLoad);
		bSave.setOnClickListener(this);
		bLoad.setOnClickListener(this);
		try {
			fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.bSave:
			String stringData = sharedData.getText().toString();
			//	Saving Data via File
			/*File f = new File(FILENAME);
			try {
				fos = new FileOutputStream(f);
				fos.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			*/
			try {
				fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
				fos.write(stringData.getBytes());
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case R.id.bLoad:
			new loadSomeStuff().execute(FILENAME);
			break;
		}
	}
		public class loadSomeStuff extends AsyncTask<String, Integer, String> {

			@Override
			protected String doInBackground(String... arg0) {
				// TODO Auto-generated method stub
				FileInputStream fis = null;
				String collected = null;
				
				try {
					fis = openFileInput(FILENAME);
					byte[] dataArray = new byte[fis.available()];
					while(fis.read(dataArray) != -1){
						collected = new String(dataArray);
					}
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					try {
						fis.close();
						return collected;
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				return null;
			
			}
		}

}
