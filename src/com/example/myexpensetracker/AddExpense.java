package com.example.myexpensetracker;

import java.text.SimpleDateFormat;
import java.util.Date;

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

public class AddExpense extends Activity 
{
	DbAdapter db;
	EditText ename, eamt;
	TextView heading;
	
	ImageButton create;
	String walletname;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_expense);
		Bundle extras = getIntent().getExtras();
		walletname = extras.getString("walletname");
		ename = (EditText) this.findViewById(R.id.expensename);
		eamt = (EditText) this.findViewById(R.id.expenseamt);
		create = (ImageButton) this.findViewById(R.id.createexpense);
		heading=(TextView) this.findViewById(R.id.heading);
		
		String fontPath = "fonts/db.otf";
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
        heading.setTypeface(tf);
		
		db = new DbAdapter(this);
		create.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(ename.getText().toString().equals("") ||
				  (eamt.getText().toString().equals("")))
				{
					InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
					imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
					Toast.makeText(getApplicationContext(), "Please enter all fields", Toast.LENGTH_SHORT).show();
				}
				else
				{
					db.open();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
					String currentDateandTime = sdf.format(new Date());
					String mname = ename.getText().toString();
					int mamt = Integer.parseInt(eamt.getText().toString());
					
					long id = db.insertExpense(walletname, mname, mamt, currentDateandTime);
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
						Toast.makeText(getApplicationContext(), "Expense added", Toast.LENGTH_SHORT).show();
						finish();
					} 
					
				}
				
			}
		});
	}

	
}
