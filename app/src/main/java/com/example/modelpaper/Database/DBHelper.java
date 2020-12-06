package com.example.modelpaper.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "UserInfo.db";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_USERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(SQL_DELETE_USERS);
    }

    public static final String SQL_CREATE_USERS =
            "CREATE TABLE " + UserProfile.Users.TABLE_NAME + " (" +
                    UserProfile.Users._ID + " INTEGER PRIMARY KEY," +
                    UserProfile.Users.COLUMN_NAME_USERNAME + " TEXT," +
                    UserProfile.Users.COLUMN_NAME_DATEOFBIRTH + " TEXT," +
                    UserProfile.Users.COLUMN_NAME_PASSWORD + " TEXT," +
                    UserProfile.Users.COLUMN_NAME_GENDER + " TEXT)";
    public static final String SQL_DELETE_USERS =
            "DROP TABLE IF EXISTS " + UserProfile.Users.TABLE_NAME;

    public long addInfo(String userName, String dateOfBirth, String gender, String password) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(UserProfile.Users.COLUMN_NAME_USERNAME, userName);
        values.put(UserProfile.Users.COLUMN_NAME_DATEOFBIRTH, dateOfBirth);
        values.put(UserProfile.Users.COLUMN_NAME_PASSWORD, password);
        values.put(UserProfile.Users.COLUMN_NAME_GENDER, gender);

        long newRowID = db.insert(UserProfile.Users.TABLE_NAME, null, values);
        return newRowID;
    }

    public boolean updateInfo(String userName, String dataOfBirth, String password, String gender) {
        SQLiteDatabase db = getWritableDatabase();

// New value for one column

        ContentValues values = new ContentValues();
        values.put(UserProfile.Users.COLUMN_NAME_DATEOFBIRTH, dataOfBirth);
        values.put(UserProfile.Users.COLUMN_NAME_PASSWORD, password);
        values.put(UserProfile.Users.COLUMN_NAME_GENDER, gender);

// Which row to update, based on the title
        String selection = UserProfile.Users.COLUMN_NAME_USERNAME + " LIKE ?";
        String[] selectionArgs = {userName};

        int count = db.update(
                UserProfile.Users.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        if (count >= -1) {
            return true;

        } else {
            return false;
        }
    }

    public List<String> readAllInfo() {
        SQLiteDatabase db = getReadableDatabase();

// Define a projection that specifies which columns from the database
// you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                UserProfile.Users.COLUMN_NAME_USERNAME,
                UserProfile.Users.COLUMN_NAME_DATEOFBIRTH,
                UserProfile.Users.COLUMN_NAME_PASSWORD,
                UserProfile.Users.COLUMN_NAME_GENDER
        };

// Filter results WHERE "title" = 'My Title'
        String selection = UserProfile.Users.COLUMN_NAME_USERNAME + " = ?";
        String userName = "navodya";
        String[] selectionArgs = {userName};

// How you want the results sorted in the resulting Cursor
        String sortOrder =
                UserProfile.Users.COLUMN_NAME_USERNAME + " DESC";

        Cursor cursor = db.query(
                UserProfile.Users.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );

        List<String> userNames = new ArrayList<>();
        while (cursor.moveToNext()) {
            String user = cursor.getString(cursor.getColumnIndexOrThrow(UserProfile.Users.COLUMN_NAME_USERNAME));
            userNames.add(user);
        }
        cursor.close();
        return userNames;
    }

    public List<String> readAllInfo(String userName,Context context) {
        SQLiteDatabase db = getReadableDatabase();

// Define a projection that specifies which columns from the database
// you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                UserProfile.Users.COLUMN_NAME_USERNAME,
                UserProfile.Users.COLUMN_NAME_DATEOFBIRTH,
                UserProfile.Users.COLUMN_NAME_PASSWORD,
                UserProfile.Users.COLUMN_NAME_GENDER
        };

// Filter results WHERE "title" = 'My Title'
        String selection = UserProfile.Users.COLUMN_NAME_USERNAME + " = ?";
        String[] selectionArgs = {userName};

// How you want the results sorted in the resulting Cursor
        String sortOrder =
                UserProfile.Users.COLUMN_NAME_USERNAME + " DESC";

        Toast.makeText(context, "test", Toast.LENGTH_SHORT).show();
        Cursor cursor = db.query(
                UserProfile.Users.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );
        List<String> userInfo = new ArrayList<>();
        while (cursor.moveToNext()) {
            String user = cursor.getString(cursor.getColumnIndex(UserProfile.Users.COLUMN_NAME_USERNAME));
            String dateOfBirth = cursor.getString(cursor.getColumnIndex(UserProfile.Users.COLUMN_NAME_DATEOFBIRTH));
            String password = cursor.getString(cursor.getColumnIndex(UserProfile.Users.COLUMN_NAME_PASSWORD));
            String gender = cursor.getString(cursor.getColumnIndex(UserProfile.Users.COLUMN_NAME_GENDER));

            userInfo.add(user);//0
            userInfo.add(dateOfBirth);//1
            userInfo.add(password);//2
            userInfo.add(gender);//3
        }
        cursor.close();
        return userInfo;
    }

    public void deleteInfo(String userName) {
        SQLiteDatabase db = getWritableDatabase();
        // Define 'where' part of query.
        String selection = UserProfile.Users.COLUMN_NAME_USERNAME + " LIKE ?";
// Specify arguments in placeholder order.
        String[] selectionArgs = {userName};
// Issue SQL statement.
        db.delete(UserProfile.Users.TABLE_NAME, selection, selectionArgs);

    }
    public boolean loginUser(String userName,String password){
        SQLiteDatabase db = getReadableDatabase();

// Define a projection that specifies which columns from the database
// you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                UserProfile.Users.COLUMN_NAME_USERNAME,
                UserProfile.Users.COLUMN_NAME_PASSWORD
        };

// Filter results WHERE "title" = 'My Title'
        String selection = UserProfile.Users.COLUMN_NAME_USERNAME+ " = ? AND "+ UserProfile.Users.COLUMN_NAME_PASSWORD + " = ?";
        String[] selectionArgs = { userName,password };

// How you want the results sorted in the resulting Cursor
        String sortOrder =
                UserProfile.Users.COLUMN_NAME_USERNAME + " ASC";

        Cursor cursor = db.query(
                UserProfile.Users.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );
        List validUser = new ArrayList();
        while(cursor.moveToNext()) {
            String user = cursor.getString(
                    cursor.getColumnIndexOrThrow(UserProfile.Users.COLUMN_NAME_USERNAME));
                    validUser.add(user);
        }
        cursor.close();

        if(validUser.isEmpty()){
            return false;
        }else{
            return true;
        }

    }


}
