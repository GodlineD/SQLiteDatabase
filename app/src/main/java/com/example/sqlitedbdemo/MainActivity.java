package com.example.sqlitedbdemo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static com.example.sqlitedbdemo.DataBaseHelper.CUSTOMERS_TABLE;
import static com.example.sqlitedbdemo.DataBaseHelper.CUSTOMER_NAME_COLUMN;
import static com.example.sqlitedbdemo.DataBaseHelper.LOCATION_COLUMN;

public class MainActivity extends AppCompatActivity {
    Button btn_insertData,btnViewAll;
    EditText editName,editPlace;
    DataBaseHelper dataBaseHelper;
    SQLiteDatabase sqLiteDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editName = findViewById(R.id.edit_name);
        editPlace = findViewById(R.id.edit_location);
        btnViewAll = findViewById(R.id.btn_viewAll);
        btn_insertData = findViewById(R.id.btn_insert_data);
        btn_insertData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData(editName.getText().toString(),editPlace.getText().toString());
            }
        });

        //Calling the viewAll() method to display data from database
        viewAll();


    }
/*-------------------------------------------------------------------------------------------------------------------------------------------------
    ******************************************************InsertData()**************************************************************************
    * This is the method thad insert data into the data base, it takes import from the user and insert into Sqlite data base*
------------------------------------------------------------------------------------------------------------------------------------------------*/
    private void insertData(String name,String location) {
        dataBaseHelper = new DataBaseHelper(getApplicationContext());
        sqLiteDatabase = dataBaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CUSTOMER_NAME_COLUMN,name);
        values.put(LOCATION_COLUMN,location);

        long customers_row=0;
        if (sqLiteDatabase !=null){
            customers_row = sqLiteDatabase.insert(CUSTOMERS_TABLE, null, values);
            if(customers_row != -1){
                Toast.makeText(MainActivity.this,"Data inserted successfully",Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(MainActivity.this,"Error during insertion",Toast.LENGTH_SHORT).show();

            }

        }
        else{
            Toast.makeText(MainActivity.this,"No database to insert data",Toast.LENGTH_SHORT).show();
        }

    } /*************************End of method**********************************************************************************************************************/


    /*-------------------------------------------------------------------------------------------------------------------------------------------------
    ******************************************************viewAll()**************************************************************************
    * This is the method that display  data from the database, it takes all data from the database and and display it using alert Dialog *
------------------------------------------------------------------------------------------------------------------------------------------------*/
    public void viewAll(){
        btnViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Cursor cursor =    getAllData();
            if(cursor.getCount()==0){
                //If no data to show show a message
                showMessage("Error","No data found");
                return;
            }
        //    else{
                StringBuffer stringBuffer = new StringBuffer();
                while (cursor.moveToNext()){
                    stringBuffer.append("CustomerId :"+cursor.getString(0)+"\n");
                    stringBuffer.append("Customer Name:"+cursor.getString(1)+"\n");
                    stringBuffer.append("Region:"+cursor.getString(2)+"\n\n");

                }
                showMessage("Data",stringBuffer.toString());
            }
  //          }
        });
    }/*************************************************End of the method*************************************************************************************************/



    /*-------------------------------------------------------------------------------------------------------------------------------------------------
    ******************************************************ShowMessage()**************************************************************************
                             * This is the method that create alert dialog to display data from the database*
------------------------------------------------------------------------------------------------------------------------------------------------*/
    public void showMessage(String title,String message){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setCancelable(true);
        alertBuilder.setTitle(title);
        alertBuilder.setMessage(message);
        alertBuilder.show();
    }/*********************************************End of AlertDialog creation*****************************************************************/

    /*-------------------------------------------------------------------------------------------------------------------------------------------------
      ******************************************************getAllData()**************************************************************************
      * This is the method that get all data from the database using Curson,this data will be displayed with the help of other methods*
  ------------------------------------------------------------------------------------------------------------------------------------------------*/
    public Cursor getAllData(){
        sqLiteDatabase = dataBaseHelper.getWritableDatabase();
        Cursor res = sqLiteDatabase.rawQuery("SELECT * FROM "+CUSTOMERS_TABLE,null);
        return res;

    }/*****************************************************End of method*******************************************************************************/

}