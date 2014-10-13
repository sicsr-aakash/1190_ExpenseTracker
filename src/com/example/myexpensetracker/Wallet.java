package com.example.myexpensetracker;


import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class Wallet extends Activity
{
	TextView heading;
	DbAdapter db;
	ImageButton add,view,update;
	ActionBar ac;
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.wallet, menu);
		
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_about_us) {
			Intent i = new Intent(this,AboutUsActivity.class);
			startActivity(i);
			
			return true;
		}
		
		if(id==R.id.action_instructions){
			Intent i=new Intent(getApplicationContext(),InsActivity.class);
			startActivity(i);
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wallet);
		db = new DbAdapter(this);
		add = (ImageButton) this.findViewById(R.id.walletadd);
		view = (ImageButton) this.findViewById(R.id.walletview);
		update=(ImageButton) this.findViewById(R.id.walletupdate);
		heading=(TextView) this.findViewById(R.id.heading);
		ac = getActionBar();
		ac.show();
		
		
		String fontPath = "fonts/db.otf";
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
        heading.setTypeface(tf);
		
		add.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent("android.intent.action.ADDWALLET");
				startActivity(i);
				
				
			}
		});
		
		view.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i =new Intent("android.intent.action.VIEWWALLET");
				startActivity(i);
				
			}
		});
		
		
		update.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent i=new Intent("android.intent.action.UPDATE");
				startActivity(i);
				
			}
		});
	}

	
}
