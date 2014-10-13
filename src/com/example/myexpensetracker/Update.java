package com.example.myexpensetracker;



import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.os.Build;

public class Update extends Activity {

	DbAdapter db;
	TextView heading;
	Spinner s1;
	ImageButton update, delete;
	int counter = 0;
	String mwallet;
	EditText editCountry;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update);
		editCountry = (EditText) this.findViewById(R.id.editcountry);
		update = (ImageButton) this.findViewById(R.id.updatewallet);
		delete = (ImageButton) this.findViewById(R.id.deletewallet);
		heading=(TextView) this.findViewById(R.id.heading);
		String fontPath = "fonts/db.otf";
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
        heading.setTypeface(tf);
		
		db = new DbAdapter(this);
		db.open();
		Cursor cur = db.getAllWallets();
		final String[] wallets = new String[cur.getCount()];
		if((cur != null) && (cur.getCount() > 0))
		{
			if(cur.moveToFirst())
			{
				do
				{
					wallets[counter] = cur.getString(0);
					counter ++;
				}
				while(cur.moveToNext());
			}
		
			s1 = (Spinner) this.findViewById(R.id.spinner);
			ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,wallets);
			s1.setAdapter(adapter);
			db.close();
			s1.setOnItemSelectedListener(new OnItemSelectedListener() 
			{

				@Override
				public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) 
				{
					db.open();
					int index = parent.getSelectedItemPosition();
					mwallet = wallets[index];
					Cursor c = db.getCurrency(mwallet);
					if((c != null) && (c.getCount() > 0))
					{
						if(c.moveToFirst())
						{
							editCountry.setText(c.getString(0));
						}
					}
					else
					{
						InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
						imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
						Toast.makeText(getApplicationContext(), "Database error", Toast.LENGTH_SHORT).show();
					} 
					db.close();
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
				}
			});
			
			update.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					db.open();
					String mcountry = editCountry.getText().toString();
					if(db.updateWallets(mwallet, mcountry))
					{
						InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
						imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
						Toast.makeText(getApplicationContext(), "Wallet Updated", Toast.LENGTH_SHORT).show();
						db.close();
						Intent i = new Intent(getApplicationContext(), Wallet.class);
						startActivity(i);
					}
					else
					{
						InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
						imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
						Toast.makeText(getApplicationContext(), "Database error", Toast.LENGTH_SHORT).show();
						db.close();
						Intent i = new Intent(getApplicationContext(), Wallet.class);
						startActivity(i);
					}
				}
			});
			
			delete.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					db.open();
					
					if((db.deleteWallet(mwallet)) && (db.deleteOther(mwallet)))
					{
						InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
						imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
						Toast.makeText(getApplicationContext(), "Wallet Deleted", Toast.LENGTH_SHORT).show();
						db.close();
						Intent i = new Intent(getApplicationContext(), Wallet.class);
						startActivity(i);
					}
					else
					{
						InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
						imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
						Toast.makeText(getApplicationContext(), "Wallet Deleted", Toast.LENGTH_SHORT).show();
						db.close();
						Intent i = new Intent(getApplicationContext(), Wallet.class);
						startActivity(i);
					}
					
				}
			}); 
			
		}
		
	}


	

}
