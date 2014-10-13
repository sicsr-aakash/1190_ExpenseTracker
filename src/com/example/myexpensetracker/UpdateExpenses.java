package com.example.myexpensetracker;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateExpenses extends Activity
{
	TextView heading;
	EditText name, amt;
	ImageButton update, delete;
	DbAdapter db;
	String listdata;
	int id;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_expenses);
		Bundle extras = getIntent().getExtras();
		listdata = extras.getString("listvalue");
		heading=(TextView) this.findViewById(R.id.heading);
		String fontPath = "fonts/db.otf";
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
        heading.setTypeface(tf);
		String d[] = listdata.split(" ");
		id = Integer.parseInt(d[0]);
		
		name = (EditText) this.findViewById(R.id.expensename);
		amt = (EditText) this.findViewById(R.id.expenseamt);
		update = (ImageButton) this.findViewById(R.id.updateexpense);
		delete = (ImageButton) this.findViewById(R.id.deleteexpense);
		db = new DbAdapter(this);
		db.open();
		Cursor c = db.getExpenseDetails(id);
		if((c != null) && (c.getCount() > 0))
		{
			if(c.moveToFirst())
			{
				name.setText(c.getString(0));
				amt.setText(c.getString(1));
			}
		}
		db.close();
		update.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(name.getText().toString().equals("") ||
				  (amt.getText().toString().equals("")))
				{
					InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
					imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
					Toast.makeText(getApplicationContext(), "Please enter all fields", Toast.LENGTH_SHORT).show();
				}
				else
				{
					db.open();
					String mname = name.getText().toString();
					int mamt = Integer.parseInt(amt.getText().toString());
					if(db.updateExpenses(id, mname, mamt))
					{
						InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
						imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
						Toast.makeText(getApplicationContext(), "Update Successful", Toast.LENGTH_SHORT).show();
						db.close();
						Intent i = new Intent("android.intent.action.VIEWWALLET");
						startActivity(i);
						
					}
					else
					{
						InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
						imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
						Toast.makeText(getApplicationContext(), "Database error", Toast.LENGTH_SHORT).show();
					
					}
					
				}
				
			}
		});
		
		delete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				db.open();
				if(db.deleteExpense(id))
				{
					db.close();
					Toast.makeText(getApplicationContext(), "Expense deleted", Toast.LENGTH_SHORT).show();
					Intent i = new Intent("android.intent.action.VIEWWALLET");
					startActivity(i);
				}
				else
				{
					Toast.makeText(getApplicationContext(), "Database error", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	
}
