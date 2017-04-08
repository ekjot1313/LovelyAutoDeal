package com.example.user.lovelyautodeal;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Environment;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
DatabaseHelper myDB;
    EditText et1,et2;
    Button addbut,viewbut,updatebut,delbut;
    Button addRecordBUT,findRecordBUT,updateRecordBUT,deleteRecordBUT,backuptoBUT,backupfromBUT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDB=new DatabaseHelper(this);



        addRecordBUT =(Button)findViewById(R.id.addRecordBUT);
        findRecordBUT=(Button)findViewById(R.id.findRecordBUT);
        updateRecordBUT=(Button)findViewById(R.id.updateRecordBUT);
        deleteRecordBUT=(Button)findViewById(R.id.deleteRecordBUT);
        backuptoBUT=(Button)findViewById(R.id.backuptoBUT);
        backupfromBUT=(Button)findViewById(R.id.backupfromBUT);

        addRecordBUT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main_to_other("add");
            }
        });
        findRecordBUT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main_to_other("find");
            }
        });
        updateRecordBUT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main_to_other("update");
            }
        });
        deleteRecordBUT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main_to_other("delete");
            }
        });
       backuptoBUT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backupto();

            }
        });
       backupfromBUT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backupfrom();
            }
        });

    }

    public void main_to_other(String buttonClicked){
        Intent main_to_other;
        if(buttonClicked.equals("add")){
            main_to_other=new Intent(MainActivity.this, RecordFormActivity.class);
        }
        else{
            main_to_other=new Intent(MainActivity.this, FindActivity.class);
        }
        main_to_other.putExtra("buttonClicked",buttonClicked);
        startActivity(main_to_other);
    }

public void backupto(){

    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
    builder.setMessage("Are you sure to Backup Database to Text file?");
    builder.setTitle("BACKUP!!!");
    builder.setPositiveButton("Backup", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog,int which) {
            writeFile();
        }


    });
    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    });
    builder.show();

}


    public void writeFile(){
        String database=getDatabase();
        try {
            File root = new File(Environment.getExternalStorageDirectory(), "Lovely Auto Deal");

            // if external memory exists and folder with name Notes

            if (!root.exists()) {

                root.mkdirs(); // this will create folder.

            }

            File filepath = new File(root, "lad database [DO NOT DELETE].txt");  // file path to save
            FileOutputStream fileOutputStream=new  FileOutputStream(filepath);
            fileOutputStream.write(database.getBytes());
            fileOutputStream.close();
            Toast.makeText(MainActivity.this,"Backup Successfull.",Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public String getDatabase(){
        String database="";
        String query="select * from info;";

        Cursor res=myDB.getAllData(query);

        if(res.getCount()==0){

            Toast.makeText(MainActivity.this,"Database Empty!!!",Toast.LENGTH_LONG).show();

        }
        else{

            while(res.moveToNext()){

                String id="/*{$"+res.getString(0)+"$}",
                make="{$"+res.getString(1)+"$}",
                regdno="{$"+res.getString(2)+"$}",
                chassisno="{$"+res.getString(3)+"$}",
                engineno ="{$"+res.getString(4)+"$}",
                model="{$"+res.getString(5)+"$}",
                purchasedfrom="{$"+res.getString(6)+"$}",
                purchaseraddress="{$"+res.getString(7)+"$}",
                purchaserphone="{$"+res.getString(8)+"$}",
                purchasedate="{$"+res.getString(9)+"$}",
                soldto ="{$"+res.getString(10)+"$}",
                selleraddress ="{$"+res.getString(11)+"$}",
                sellerphone="{$"+res.getString(12)+"$}",
                sellingdate="{$"+res.getString(13)+"$}",
                extrainformation="{$"+res.getString(14)+"$}",
                lastupdated="{$"+res.getString(15)+"$}*/";

                database+=id+make+regdno+chassisno+engineno+model+purchasedfrom+purchaseraddress+purchaserphone+
                        purchasedate+soldto+selleraddress+sellerphone+ sellingdate+extrainformation+lastupdated+"\n";


            }

        }
        return database;
    }

    public void backupfrom(){

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Are you sure to Backup Text file to Database?");
        builder.setTitle("BACKUP!!!");
        builder.setPositiveButton("Backup", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog,int which) {
                readFile();
            }


        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    public void readFile(){

        try {

            File root = new File(Environment.getExternalStorageDirectory(), "Lovely Auto Deal");
            File filepath = new File(root, "lad database [DO NOT DELETE].txt");
            // if external memory exists and folder with name Notes

            if (root.exists()&&filepath.exists()) {


                FileInputStream fileInputStream = new FileInputStream(filepath);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuffer stringBuffer = new StringBuffer();
                String lines = "";
                while ((lines = bufferedReader.readLine()) != null) {
                    stringBuffer.append(lines + "\n");

                }
                String data = stringBuffer.toString();
                if (data.length() > 0){
                    String[] data_lines = data.split("\\n");
                ArrayList<String[]> data_values_list = new ArrayList<String[]>();
                String[] data_values;
                for (String s : data_lines) {

                    data_values = new String[16];
                    int startpos = 0, endpos = 0, pos = 0;

// /*{$1$}{$F$}{$G$}{$$}{$$}{$6$}{$$}{$$}{$$}{$$}{$null$}{$null$}{$null$}{$null$}{$$}{$2017-01-22 12:25:27$}*/

                    for (int i = 0, z = 0; z == 0; i++) {

                        char c = s.charAt(i);
                        char d = s.charAt(i + 1);
                        switch (c)

                        {

                            case '{': {
                                if (d == '$') {
                                    startpos = i + 2;
                                }
                                break;
                            }
                            case '$': {
                                if (d == '}') {
                                    endpos = i;
                                    if (endpos == startpos) {
                                        data_values[pos] = "";
                                    } else {
                                        String demo=(s.substring(startpos, endpos));
                                        data_values[pos] = demo.equals("null")?"":demo;
                                    }
                                    pos++;
                                }
                                break;
                            }
                            case '*': {
                                if (d == '/') {
                                    z = 1;
                                }
                                break;
                            }

                        }


                    }

                    data_values_list.add(data_values);

                }
                myDB.deleteTable();
                myDB.createTable();
                for (String[] values : data_values_list) {
                      myDB.insertData(values,true);
                }
                    Toast.makeText(MainActivity.this,"Backup Successfull!!!",Toast.LENGTH_LONG).show();
            }
                else{
                    Toast.makeText(MainActivity.this,"File Empty!!!",Toast.LENGTH_LONG).show();
                }
            }
            else{
                Toast.makeText(MainActivity.this,"File Not Found!!!",Toast.LENGTH_LONG).show();
            }





        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
