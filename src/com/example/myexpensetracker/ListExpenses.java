package com.example.myexpensetracker;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ListExpenses extends ListActivity
{

	DbAdapter db = new DbAdapter(this);
	TextView heading;
	ListView v;
	Cursor c;
	ArrayList<String> mylist;
	String currency;
	String walletname;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_expenses);
		Bundle extras = getIntent().getExtras();
		walletname = extras.getString("walletname");
		currency = extras.getString("currency");
		heading=(TextView) findViewById(R.id.heading);
		String fontPath = "fonts/db.otf";
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
        heading.setTypeface(tf);
		
		
		db.open();
		
		ListView lv = getListView();
		lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		lv.setTextFilterEnabled(true);
		Cursor c = db.getAllExpenses(walletname);
		mylist = new ArrayList<>();
		if((c != null) && (c.getCount() > 0))
		{
			if(c.moveToFirst())
			{
				do
				{
					String newstr = String.format("%-20s %-20s %-20s %-1s", c.getString(0), c.getString(1), c.getString(2), currency);
					mylist.add(newstr);
				}
				while(c.moveToNext());
			}
			setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,mylist));
		}
		else
		{
			Toast.makeText(getApplicationContext(), "No wallets added", Toast.LENGTH_SHORT).show();
		}
	}
	
	public void onListItemClick(ListView parent, View v, int position, long id)
	{
		Intent i = new Intent("android.intent.action.UPDATEEXPENSES"); 
		String s = (String) parent.getItemAtPosition(position);
		i.putExtra("listvalue", s);
		startActivity(i);
	}

	
}
