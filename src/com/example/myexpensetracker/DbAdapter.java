package com.example.myexpensetracker;

import java.lang.Character.UnicodeBlock;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;


public class DbAdapter 
{
	private static final String DATABASE_NAME ="expensetracker";
	static final int DATABASE_VERSION = 1;
	private static final String WALLET_TABLE = "wallet";
	private static final String EXPENSE_TABLE = "expense";
	private static final String WALLET_ID = "_id";
	private static final String WALLET_NAME = "walletname";
	private static final String WALLET_CURRENCY = "currency";
	
	private static final String EXPENSE_ID = "_id";
	private static final String EXPENSE_WALLETNAME = "walletname";
	private static final String EXPENSE_NAME = "expensename";
	private static final String EXPENSE_AMOUNT = "expenseamount";
	private static final String EXPENSE_TIME = "time";
	
	final Context context;
	DbHelper dbhelper;
	SQLiteDatabase db;
	public DbAdapter(Context context) 
	{
		this.context = context;
		dbhelper = new DbHelper(context);
	}
	
	public DbAdapter open() throws SQLException
	{
		db = dbhelper.getWritableDatabase();
		return this;
	}
	
	public void close()
	{
		dbhelper.close();
	}
	
	public long insertWallet(String name, String currency)
	{
		ContentValues cv = new ContentValues();
		cv.put(WALLET_NAME, name);
		cv.put(WALLET_CURRENCY, currency);
		return db.insert(WALLET_TABLE, null, cv);
	}
	
	public long insertExpense(String walletname, String name, int amt, String time)
	{
		ContentValues cv = new ContentValues();
		cv.put(EXPENSE_WALLETNAME, walletname);
		cv.put(EXPENSE_NAME, name);
		cv.put(EXPENSE_AMOUNT, amt);
		cv.put(EXPENSE_TIME, time);
		return db.insert(EXPENSE_TABLE, null, cv);
	}
	
	public Cursor getAllWallets() throws SQLException
	{
		return db.query(WALLET_TABLE, new String[] {WALLET_NAME, WALLET_CURRENCY}, null, null, null, null, null); 
		
	}
	
	public Cursor getAllExpenses(String walletname) throws SQLException
	{
		Cursor c = db.query(true, EXPENSE_TABLE, new String[] {EXPENSE_ID, EXPENSE_NAME, EXPENSE_AMOUNT}, EXPENSE_WALLETNAME + "=" + "'" + walletname + "'", null, null, null, null, null);                               
		if(c != null)
		{
			if(c.moveToFirst())
			{
				return c;
			}
		}
		return null;
	}
	
	public Cursor getExpenseDetails(int id) throws SQLException
	{
		Cursor c = db.query(true, EXPENSE_TABLE, new String[] {EXPENSE_NAME, EXPENSE_AMOUNT}, EXPENSE_ID + "=" + id, null, null, null, null, null);
		if(c != null)
		{
			if(c.moveToFirst())
			{
				return c;
			}
		}
		return null;
	}
	
	public boolean updateExpenses(int id, String name, int amt)
	{
		ContentValues cv = new ContentValues();
		cv.put(EXPENSE_NAME, name);
		cv.put(EXPENSE_AMOUNT, amt);
		return db.update(EXPENSE_TABLE, cv, EXPENSE_ID + "=" + id , null) > 0;
	}
	
	public boolean deleteExpense(int id)
	{
		return db.delete(EXPENSE_TABLE, EXPENSE_ID + "=" + id , null) > 0;
	}
	
	public Cursor getCurrency(String name) throws SQLException
	{
		return db.query(true, WALLET_TABLE, new String[] {WALLET_CURRENCY}, WALLET_NAME + "=" + "'" + name + "'", null, null, null, null, null);

	}
	
	public boolean updateWallets(String wallet, String currency) throws SQLException
	{
		ContentValues cv = new ContentValues();
		cv.put(WALLET_CURRENCY, currency);
		return db.update(WALLET_TABLE, cv, WALLET_NAME + "=" + "'" + wallet + "'", null) > 0;
	}
	
	public boolean deleteWallet(String wallet) throws SQLException
	{
		return db.delete(WALLET_TABLE, WALLET_NAME + "=" + "'" + wallet + "'", null) > 0;
	}
	
	public boolean deleteOther(String wallet) throws SQLException
	{
		return db.delete(EXPENSE_TABLE, EXPENSE_WALLETNAME + "=" + "'" + wallet + "'", null) > 0;
	}
	

}
