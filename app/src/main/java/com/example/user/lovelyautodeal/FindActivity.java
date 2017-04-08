package com.example.user.lovelyautodeal;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class FindActivity extends AppCompatActivity {
    EditText idET,  regdnoET, modelET, enginenoET, chassisnoET, purchasedfromET, soldtoET;
    CheckBox idCB, regdnoCB, modelCB, enginenoCB, chassisnoCB, purchasedfromCB, soldtoCB, showallCB;
    Button searchBUT;
    DatabaseHelper myDB;
    String buttonClicked;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);

        buttonClicked=getIntent().getExtras().getString("buttonClicked");

        myDB=new DatabaseHelper(this);
        idET = (EditText) findViewById(R.id.idET);
        regdnoET = (EditText) findViewById(R.id.regdnoET);
        modelET = (EditText) findViewById(R.id.modelET);
        enginenoET = (EditText) findViewById(R.id.enginenoET);
        chassisnoET = (EditText) findViewById(R.id.chassisnoET);
        purchasedfromET = (EditText) findViewById(R.id.purchasedfromET);
        soldtoET = (EditText) findViewById(R.id.soldtoET);

        idCB=(CheckBox)findViewById(R.id.idCB);
        regdnoCB=(CheckBox)findViewById(R.id.regdnoCB);
        modelCB=(CheckBox)findViewById(R.id.modelCB);
        enginenoCB=(CheckBox)findViewById(R.id.enginenoCB);
        chassisnoCB=(CheckBox)findViewById(R.id.chassisnoCB);
        purchasedfromCB=(CheckBox)findViewById(R.id.purchasedfromCB);
        soldtoCB=(CheckBox)findViewById(R.id.soldtoCB);
        showallCB=(CheckBox)findViewById(R.id.showallCB);



        idCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(idCB.isChecked()){
                    idET.setEnabled(true);
                }
                else{
                    idET.setEnabled(false);
                    idET.setText("");
                }
            }
        });

        regdnoCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(regdnoCB.isChecked()){
                    regdnoET.setEnabled(true);
                }
                else{
                    regdnoET.setEnabled(false);
                    regdnoET.setText("");
                }
            }
        });
                modelCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(modelCB.isChecked()){
                            modelET.setEnabled(true);
                        }
                        else{
                            modelET.setEnabled(false);
                            modelET.setText("");
                        }
                    }
                });
        enginenoCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(enginenoCB.isChecked()){
                    enginenoET.setEnabled(true);
                }
                else{
                    enginenoET.setEnabled(false);
                    enginenoET.setText("");
                }
            }
        });
                chassisnoCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(chassisnoCB.isChecked()){
                            chassisnoET.setEnabled(true);
                        }
                        else{
                            chassisnoET.setEnabled(false);
                            chassisnoET.setText("");
                        }
                    }
                });
        purchasedfromCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(purchasedfromCB.isChecked()){
                    purchasedfromET.setEnabled(true);
                }
                else{
                    purchasedfromET.setEnabled(false);
                    purchasedfromET.setText("");
                }
            }
        });
                soldtoCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(soldtoCB.isChecked()){
                            soldtoET.setEnabled(true);
                        }
                        else{
                            soldtoET.setEnabled(false);
                            soldtoET.setText("");
                        }
                    }
                });
        showallCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(showallCB.isChecked()){}
                else{}
            }
        });

        reset();

        searchBUT=(Button)findViewById(R.id.searchBUT);
        searchBUT.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String cond="";
                        if(idCB.isChecked()){cond=cond+" and id=0"+(idET.getText()+"").toUpperCase().trim();}
                        if(regdnoCB.isChecked()){cond=cond+" and regdno='"+(regdnoET.getText()+"").toUpperCase().trim()+"'";}
                        if(chassisnoCB.isChecked()){cond=cond+" and chassisno='"+(chassisnoET.getText()+"").toUpperCase().trim()+"'";}
                        if(enginenoCB.isChecked()){cond=cond+" and engineno='"+(enginenoET.getText()+"").toUpperCase().trim()+"'";}
                        if(purchasedfromCB.isChecked()){cond=cond+" and purchasedfrom='"+(purchasedfromET.getText()+"").toUpperCase().trim()+"'";}
                        if(soldtoCB.isChecked()){cond=cond+" and soldto='"+(soldtoET.getText()+"").toUpperCase().trim()+"'";}
                        if(modelCB.isChecked()){cond=cond+" and model=0"+(modelET.getText()+"").toUpperCase().trim();}

                        if(cond.length()>4){
                            cond=" where "+cond.substring(4);
                        }
                        else if(showallCB.isChecked()){
                            cond="order by id desc";
                        }


                        search(cond);
                    }
                });
    }

    public void reset(){
        idET.setEnabled(false);
        regdnoET.setEnabled(false);
        modelET.setEnabled(false);
        enginenoET.setEnabled(false);
        chassisnoET.setEnabled(false);
        purchasedfromET.setEnabled(false);
        soldtoET.setEnabled(false);

        idET.setText("");
        regdnoET.setText("");
        modelET.setText("");
        enginenoET.setText("");
        chassisnoET.setText("");
        purchasedfromET.setText("");
        soldtoET.setText("");

        idCB.setChecked(false);
        regdnoCB.setChecked(false);
        modelCB.setChecked(false);
        enginenoCB.setChecked(false);
        chassisnoCB.setChecked(false);
        purchasedfromCB.setChecked(false);
        soldtoCB.setChecked(false);
        showallCB.setChecked(false);



    }
    public void search(String condition){
        String query="select id,regdno,model from info  "+condition+";";

        Cursor res=myDB.getAllData(query);
        ArrayList<String> searchResult=new ArrayList<String>();
        if(res.getCount()==0){

            showSearchList("No Record Found!!!",searchResult);
        }
        else{

            int i=0;
            while(res.moveToNext()){
               searchResult.add(res.getString(0)+". "+res.getString(1)+"("+res.getString(2)+")");
i++;
            }
            showSearchList(i+" Records Found :-",searchResult);
        }
    }

    public void showSearchList(String title,ArrayList<String> searchResult ){

       AlertDialog.Builder builderSingle = new AlertDialog.Builder(FindActivity.this);

        builderSingle.setTitle(title);

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(FindActivity.this, android.R.layout.select_dialog_singlechoice,searchResult);





        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String selectedItem = arrayAdapter.getItem(which);

                int i = 0, id = 0;
                char ch = '0';
                do {
                    id = (id * 10) + Character.getNumericValue(ch);
                    ch = selectedItem.charAt(i++);

                } while (ch != '.');


                Intent find_to_recordform = new Intent(FindActivity.this, RecordFormActivity.class);
                find_to_recordform.putExtra("buttonClicked", buttonClicked);
                find_to_recordform.putExtra("id", id);
                startActivity(find_to_recordform);
            }
        });
        builderSingle.show();
    }


}
