package com.example.myexpensetracker;


import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class Expenses extends Activity {

	
	TextView wname;
	ImageButton add, view;
	String walletname, wallet;
	String currency;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_expenses);
		wname = (TextView) this.findViewById(R.id.heading);
		wname=(TextView) this.findViewById(R.id.heading);
		String fontPath = "fonts/db.otf";
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
        wname.setTypeface(tf);
		add = (ImageButton) this.findViewById(R.id.addexpense1);
		view = (ImageButton) this.findViewById(R.id.viewexpense1);
		Bundle extras = getIntent().getExtras();
		walletname = extras.getString("listvalue");
		currency = extras.getString("currency");
		//String[] str = walletname.split("-");
		wallet = walletname;
		wname.setText(wallet);
		
		
		
		add.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent("android.intent.action.ADDEXPENSES");
				i.putExtra("walletname", wallet);
				
				startActivity(i);
				
			}
		});
		
		view.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent("android.intent.action.LISTEXPENSES");
				i.putExtra("walletname", wallet);
				i.putExtra("currency",currency);
				startActivity(i);
			}
		});
	}

	

	
}
