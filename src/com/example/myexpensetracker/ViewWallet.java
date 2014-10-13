package com.example.myexpensetracker;


import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView;


public class ViewWallet extends ListActivity
{
	
	DbAdapter db = new DbAdapter(this);
	ListView v;
	Cursor c;
	TextView heading;
	ArrayList<String> mylist;
	ArrayList<String> currencies;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_wallet);
		
		heading=(TextView) findViewById(R.id.heading);
		String fontPath = "fonts/db.otf";
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
        heading.setTypeface(tf);
		
		db.open();
		ListView lv = getListView();
		lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		lv.setTextFilterEnabled(true);
		c = db.getAllWallets();
		mylist = new ArrayList<>();
		currencies = new ArrayList<>();
		if((c != null) && (c.getCount() > 0))
		{
			if(c.moveToFirst())
			{
				do
				{
					mylist.add(c.getString(0));
					currencies.add(c.getString(1));
				}
				while(c.moveToNext());
			}
			setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,mylist));
		}
		else
		{
			Toast.makeText(getApplicationContext(), "No wallets added", Toast.LENGTH_SHORT).show();
			Intent i = new Intent(getApplicationContext(),Wallet.class);
			startActivity(i);
		}
	}
	
	public void onListItemClick(ListView parent, View v, int position, long id)
	{
		Intent i = new Intent("android.intent.action.EXPENSES");
		String s = (String) parent.getItemAtPosition(position);
		i.putExtra("listvalue", s);
		i.putExtra("currency",currencies.get(position));
		startActivity(i);
	}

	
}
