package com.example.myexpensetracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper
{
	private static final String DATABASE_NAME ="expensetracker";
	static final int DATABASE_VERSION = 1;
	private static final String WALLET_TABLE = "wallet";
	private static final String EXPENSE_TABLE = "expense";
	
	private static final String TABLE_CREATE1 = "create table wallet(_id integer primary key autoincrement, walletname text not null, currency text not null, UNIQUE(walletname) ON CONFLICT REPLACE);";
	private static final String TABLE_CREATE2 = "create table expense(_id integer primary key autoincrement, walletname text not null, expensename text not null, expenseamount float not null, time text not null);";               

	
	
	public DbHelper(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		try
		{
		db.execSQL(TABLE_CREATE1);
		db.execSQL(TABLE_CREATE2);
		
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w("onUpgrade", "Updating database from version " + oldVersion + " to " + newVersion + " which will destroy old data");
		db.execSQL("DROP TABLE IF EXISTS wallet;");
		db.execSQL("DROP TABLE IF EXISTS expense;");
		onCreate(db);
		
	}

}
