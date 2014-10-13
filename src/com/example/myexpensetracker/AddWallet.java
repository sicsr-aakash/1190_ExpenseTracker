package com.example.myexpensetracker;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;

public class AddWallet extends Activity
{
	DbAdapter db;
	TextView heading;
	EditText name, currency;
	ImageButton add;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_wallet);
		name = (EditText) this.findViewById(R.id.editname);
		currency = (EditText) this.findViewById(R.id.editcurrency);
		add = (ImageButton) this.findViewById(R.id.addwallet);
		heading=(TextView) this.findViewById(R.id.heading);
		String fontPath = "fonts/db.otf";
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
        heading.setTypeface(tf);
		db = new DbAdapter(this);
		add.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(name.getText().toString().equals("") ||
				  (currency.getText().toString().equals("")))
				{
					InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
					imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
					Toast.makeText(getApplicationContext(), "Please enter all fields", Toast.LENGTH_SHORT).show();
				}
				else
				{
					db.open();
					String mname = name.getText().toString();
					String mcurrency = currency.getText().toString();
					long id = db.insertWallet(mname, mcurrency);
					if(id == -1)
					{
						InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
						imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
						Toast.makeText(getApplicationContext(), "Database error", Toast.LENGTH_SHORT).show();
					}
					else
					{
						InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
						imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
						Toast.makeText(getApplicationContext(), "Wallet created", Toast.LENGTH_SHORT).show();
						Intent i = new Intent(getApplicationContext(),Wallet.class);
						startActivity(i);
					}
				}
				
			}
		});
	}

	
}
