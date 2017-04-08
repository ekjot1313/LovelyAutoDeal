package com.example.user.lovelyautodeal;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.SystemClock;
import android.support.annotation.BoolRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class RecordFormActivity extends AppCompatActivity {
    DatabaseHelper myDB;
    //id,make,regdno,model,engineno,chasisno,purchasedfrom,purchaseraddress,purchaserphone,purchasedate,soldto,selleraddress,sellerphone,sellingdate,extrainformation,lastupdated;

    TextView idTV, makeTV, regdnoTV, modelTV, enginenoTV, chassisnoTV, purchasedfromTV, purchaseraddressTV, purchaserphoneTV, purchasedateTV, soldtoTV, selleraddressTV, sellerphoneTV, sellingdateTV, extrainformationTV, lastupdatedTV;
    EditText idET, makeET, regdnoET, modelET, enginenoET, chassisnoET, purchasedfromET, purchaseraddressET, purchaserphoneET, purchasedateET, soldtoET, selleraddressET, sellerphoneET, sellingdateET, extrainformationET, lastupdatedET;
    String id, make, regdno, model, engineno, chassisno, purchasedfrom, purchaseraddress, purchaserphone, purchasedate, soldto, selleraddress, sellerphone, sellingdate, extrainformation, lastupdated;
    Button saveBUT, updateBUT, deleteBUT, cancelBUT, clearfieldsBUT;
    String[] values = new String[16];
    String buttonClicked;
    int id_from_find;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_form);
        myDB = new DatabaseHelper(this);

        idTV = (TextView) findViewById(R.id.idTV);
        makeTV = (TextView) findViewById(R.id.makeTV);
        regdnoTV = (TextView) findViewById(R.id.regdnoTV);
        modelTV = (TextView) findViewById(R.id.modelTV);
        enginenoTV = (TextView) findViewById(R.id.enginenoTV);
        chassisnoTV = (TextView) findViewById(R.id.chassisnoTV);
        purchasedfromTV = (TextView) findViewById(R.id.purchasedfromTV);
        purchaseraddressTV = (TextView) findViewById(R.id.purchaseraddressTV);
        purchaserphoneTV = (TextView) findViewById(R.id.purchaserphoneTV);
        purchasedateTV = (TextView) findViewById(R.id.purchasedateTV);
        soldtoTV = (TextView) findViewById(R.id.soldtoTV);
        selleraddressTV = (TextView) findViewById(R.id.selleraddressTV);
        sellerphoneTV = (TextView) findViewById(R.id.sellerphoneTV);
        sellingdateTV = (TextView) findViewById(R.id.sellingdateTV);
        extrainformationTV = (TextView) findViewById(R.id.extrainformationTV);
        lastupdatedTV = (TextView) findViewById(R.id.lastupdatedTV);

        idET = (EditText) findViewById(R.id.idET);
        makeET = (EditText) findViewById(R.id.makeET);
        regdnoET = (EditText) findViewById(R.id.regdnoET);
        modelET = (EditText) findViewById(R.id.modelET);
        enginenoET = (EditText) findViewById(R.id.enginenoET);
        chassisnoET = (EditText) findViewById(R.id.chassisnoET);
        purchasedfromET = (EditText) findViewById(R.id.purchasedfromET);
        purchaseraddressET = (EditText) findViewById(R.id.purchaseraddressET);
        purchaserphoneET = (EditText) findViewById(R.id.purchaserphoneET);
        purchasedateET = (EditText) findViewById(R.id.purchasedateET);
        soldtoET = (EditText) findViewById(R.id.soldtoET);
        selleraddressET = (EditText) findViewById(R.id.selleraddressET);
        sellerphoneET = (EditText) findViewById(R.id.sellerphoneET);
        sellingdateET = (EditText) findViewById(R.id.sellingdateET);
        extrainformationET = (EditText) findViewById(R.id.extrainformationET);
        lastupdatedET = (EditText) findViewById(R.id.lastupdatedET);

        saveBUT = (Button) findViewById(R.id.saveBUT);
        updateBUT = (Button) findViewById(R.id.updateBUT);
        deleteBUT = (Button) findViewById(R.id.deleteBUT);
        cancelBUT = (Button) findViewById(R.id.cancelBUT);
        clearfieldsBUT = (Button) findViewById(R.id.clearfieldsBUT);

        purchasedateET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateETClicked(purchasedateET);
            }
        });

        sellingdateET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateETClicked(sellingdateET);
            }
        });

         buttonClicked=getIntent().getExtras().getString("buttonClicked");
         id_from_find=getIntent().getExtras().getInt("id");

        if (buttonClicked.equals("add")) {
            addButtonClicked();
        }
        else if (buttonClicked.equals("find")) {
            findButtonClicked();
            search_show(id_from_find);

        }
        else if (buttonClicked.equals("update")) {
            updateButtonClicked();
            search_show(id_from_find);

        }
        else if (buttonClicked.equals("delete")) {
            findButtonClicked();
            updateBUT.setVisibility(View.GONE);
            search_show(id_from_find);
        }

        saveBUT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                getValues();
                if(checkValues()) {

                    if (buttonClicked.equals("add")) {
                        boolean isInserted = myDB.insertData(values,false);
                        dialogbox(isInserted,"Insert Status.", "Record Inserted Successfully.", "Record Not Inserted.");
                        findButtonClicked();
                        idTV.setVisibility(View.GONE);
                        idET.setVisibility(View.GONE);
                        soldtoTV.setVisibility(View.GONE);
                        soldtoET.setVisibility(View.GONE);
                        selleraddressTV.setVisibility(View.GONE);
                        selleraddressET.setVisibility(View.GONE);
                        sellerphoneTV.setVisibility(View.GONE);
                        sellerphoneET.setVisibility(View.GONE);
                        sellingdateTV.setVisibility(View.GONE);
                        sellingdateET.setVisibility(View.GONE);
                        lastupdatedTV.setVisibility(View.GONE);
                        lastupdatedET.setVisibility(View.GONE);
                        updateBUT.setVisibility(View.GONE);
                        deleteBUT.setVisibility(View.GONE);
                    }
                    else if (buttonClicked.equals("update")) {
                        boolean isUpdated = myDB.updateData(id_from_find + "", values);
                        dialogbox(isUpdated,"Update Status.", "Record Updated Successfully.", "Record Not Updated.");
                        findButtonClicked();
                    }
                }



            }
        });

        updateBUT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateButtonClicked();
            }
        });
        deleteBUT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteButtonClicked();

            }
        });
        cancelBUT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
finish();
            }
        });
        clearfieldsBUT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                makeET.setText("");
                regdnoET.setText("");
                modelET.setText("");
                enginenoET.setText("");
                chassisnoET.setText("");
                purchasedfromET.setText("");
                purchaseraddressET.setText("");
                purchaserphoneET.setText("");
                purchasedateET.setText("");
                soldtoET.setText("");
                selleraddressET.setText("");
                sellerphoneET.setText("");
                sellingdateET.setText("");
                extrainformationET.setText("");

            }
        });
    }


    public void reset() {

        idTV.setVisibility(View.VISIBLE);
        makeTV.setVisibility(View.VISIBLE);
        regdnoTV.setVisibility(View.VISIBLE);
        modelTV.setVisibility(View.VISIBLE);
        enginenoTV.setVisibility(View.VISIBLE);
        chassisnoTV.setVisibility(View.VISIBLE);
        purchasedfromTV.setVisibility(View.VISIBLE);
        purchaseraddressTV.setVisibility(View.VISIBLE);
        purchaserphoneTV.setVisibility(View.VISIBLE);
        purchasedateTV.setVisibility(View.VISIBLE);
        soldtoTV.setVisibility(View.VISIBLE);
        selleraddressTV.setVisibility(View.VISIBLE);
        sellerphoneTV.setVisibility(View.VISIBLE);
        sellingdateTV.setVisibility(View.VISIBLE);
        extrainformationTV.setVisibility(View.VISIBLE);
        lastupdatedTV.setVisibility(View.VISIBLE);

        idET.setVisibility(View.VISIBLE);
        makeET.setVisibility(View.VISIBLE);
        regdnoET.setVisibility(View.VISIBLE);
        modelET.setVisibility(View.VISIBLE);
        enginenoET.setVisibility(View.VISIBLE);
        chassisnoET.setVisibility(View.VISIBLE);
        purchasedfromET.setVisibility(View.VISIBLE);
        purchaseraddressET.setVisibility(View.VISIBLE);
        purchaserphoneET.setVisibility(View.VISIBLE);
        purchasedateET.setVisibility(View.VISIBLE);
        soldtoET.setVisibility(View.VISIBLE);
        selleraddressET.setVisibility(View.VISIBLE);
        sellerphoneET.setVisibility(View.VISIBLE);
        sellingdateET.setVisibility(View.VISIBLE);
        extrainformationET.setVisibility(View.VISIBLE);
        lastupdatedET.setVisibility(View.VISIBLE);


        idET.setEnabled(true);
        makeET.setEnabled(true);
        regdnoET.setEnabled(true);
        modelET.setEnabled(true);
        enginenoET.setEnabled(true);
        chassisnoET.setEnabled(true);
        purchasedfromET.setEnabled(true);
        purchaseraddressET.setEnabled(true);
        purchaserphoneET.setEnabled(true);
        purchasedateET.setEnabled(true);
        soldtoET.setEnabled(true);
        selleraddressET.setEnabled(true);
        sellerphoneET.setEnabled(true);
        sellingdateET.setEnabled(true);
        extrainformationET.setEnabled(true);
        lastupdatedET.setEnabled(true);

        saveBUT.setVisibility(View.VISIBLE);
        updateBUT.setVisibility(View.VISIBLE);
        deleteBUT.setVisibility(View.VISIBLE);
        cancelBUT.setVisibility(View.VISIBLE);
        clearfieldsBUT.setVisibility(View.VISIBLE);
    }
    public void addButtonClicked() {
        reset();
        idTV.setVisibility(View.GONE);
        idET.setVisibility(View.GONE);
        soldtoTV.setVisibility(View.GONE);
        soldtoET.setVisibility(View.GONE);
        selleraddressTV.setVisibility(View.GONE);
        selleraddressET.setVisibility(View.GONE);
        sellerphoneTV.setVisibility(View.GONE);
        sellerphoneET.setVisibility(View.GONE);
        sellingdateTV.setVisibility(View.GONE);
        sellingdateET.setVisibility(View.GONE);
        lastupdatedTV.setVisibility(View.GONE);
        lastupdatedET.setVisibility(View.GONE);

        updateBUT.setVisibility(View.GONE);
        deleteBUT.setVisibility(View.GONE);
    }
    public void findButtonClicked(){
    reset();
    saveBUT.setVisibility(View.GONE);
    clearfieldsBUT.setVisibility(View.GONE);

    idET.setEnabled(false);
    makeET.setEnabled(false);
    regdnoET.setEnabled(false);
    modelET.setEnabled(false);
    enginenoET.setEnabled(false);
    chassisnoET.setEnabled(false);
    purchasedfromET.setEnabled(false);
    purchaseraddressET.setEnabled(false);
    purchaserphoneET.setEnabled(false);
    purchasedateET.setEnabled(false);
    soldtoET.setEnabled(false);
    selleraddressET.setEnabled(false);
    sellerphoneET.setEnabled(false);
    sellingdateET.setEnabled(false);
    extrainformationET.setEnabled(false);
    lastupdatedET.setEnabled(false);
}
    public void updateButtonClicked(){
        buttonClicked="update";
        reset();

        updateBUT.setVisibility(View.GONE);
        deleteBUT.setVisibility(View.GONE);

        idET.setEnabled(false);

        lastupdatedTV.setVisibility(View.GONE);
        lastupdatedET.setVisibility(View.GONE);

    }
    public void deleteButtonClicked(){
        AlertDialog.Builder builder = new AlertDialog.Builder(RecordFormActivity.this);
        builder.setMessage("Are you sure to DELETE this Record?");
        builder.setTitle("DELETE!!!");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog,int which) {
                boolean isDeleted=myDB.deleteData(id_from_find+"");
                dialogbox(isDeleted,"Delete Status.","Record Deleted Successfully.","Record Not Deleted.");
                dialog.dismiss();

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog,int which) {
                dialog.dismiss();
            }
        });


        builder.show();
    }
    public void getValues() {

        values[0] = id = idET.getText()+"";
        values[1] = make = ((makeET.getText() + "").toUpperCase()).trim();
        values[2] = regdno = ((regdnoET.getText() + "").toUpperCase()).trim();
        values[3] = chassisno = ((chassisnoET.getText() + "").toUpperCase()).trim();
        values[4] = engineno = ((enginenoET.getText()+ "").toUpperCase()).trim();
        values[5] =  model = modelET.getText() + "";
        values[6] = purchasedfrom = ((purchasedfromET.getText() + "").toUpperCase()).trim();
        values[7] = purchaseraddress =(( purchaseraddressET.getText() + "").toUpperCase()).trim();
        values[8] = purchaserphone = ((purchaserphoneET.getText() + "").toUpperCase()).trim();
        values[9] = purchasedate = ((purchasedateET.getText() + "").toUpperCase()).trim();
        values[10] = soldto = ((soldtoET.getText() + "").toUpperCase()).trim();
        values[11] = selleraddress =(( selleraddressET.getText() + "").toUpperCase()).trim();
        values[12] = sellerphone = ((sellerphoneET.getText() + "").toUpperCase()).trim();
        values[13] = sellingdate = ((sellingdateET.getText() + "").toUpperCase()).trim();
        values[14] = extrainformation = ((extrainformationET.getText() + "").toUpperCase()).trim();
        values[15] = lastupdated = "";

    }
    public void search_show(int id_from_find){
        String query="select * from info where id= "+id_from_find+";";

        Cursor res=myDB.getAllData(query);

        if(res.getCount()==0){

            dialogbox(false,"","","Record Not Found!!!");

        }
        else{

            while(res.moveToNext()){

                id=res.getString(0);
                make=res.getString(1);
                regdno=res.getString(2);
                chassisno=res.getString(3);
                engineno =res.getString(4);
                model=res.getString(5);
                purchasedfrom=res.getString(6);
                purchaseraddress=res.getString(7);
                purchaserphone=res.getString(8);
                purchasedate=res.getString(9);
                soldto =res.getString(10);
                selleraddress =res.getString(11);
                sellerphone=res.getString(12);
                sellingdate=res.getString(13);
                extrainformation=res.getString(14);
                lastupdated=res.getString(15);


                idET.setText(id);
                makeET.setText(make);
                regdnoET.setText(regdno);
                modelET.setText(model);
                enginenoET.setText(engineno);
                chassisnoET.setText(chassisno);
                purchasedfromET.setText(purchasedfrom);
                purchaseraddressET.setText(purchaseraddress);
                purchaserphoneET.setText(purchaserphone);
                purchasedateET.setText(purchasedate);
                soldtoET.setText(soldto);
                selleraddressET.setText(selleraddress);
                sellerphoneET.setText(sellerphone);
                sellingdateET.setText(sellingdate);
                extrainformationET.setText(extrainformation);
                lastupdatedET.setText(lastupdated);


            }

        }
    }
    public void dialogbox(final boolean isSuccessful, final String title, String successfulmessage, String failuremessage){

       final AlertDialog.Builder builderInner = new AlertDialog.Builder(RecordFormActivity.this);
       builderInner.setMessage(isSuccessful?successfulmessage:failuremessage);
       builderInner.setTitle(title);
       builderInner.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog,int which) {
               dialog.dismiss();
              if(title.equals("Delete Status.")){
                  if(isSuccessful){
                   finish();
                  }
              }
           }
       });



       builderInner.show();
   }

    public void dateETClicked(final EditText ET){

        final Calendar myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener[] date = {new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "yyyy-MM-dd";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                ET.setText(sdf.format(myCalendar.getTime()));

            }

        }};
        DatePickerDialog datePickerDialog= new DatePickerDialog(RecordFormActivity.this, date[0], myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.getDatePicker().setMaxDate(myCalendar.getTimeInMillis());
        datePickerDialog.show();


    }
    public Boolean checkValues(){
        String message="";
    if(make.equals("")){message="Make";}
        else if(regdno.equals("")){message="Regdno";}
        else if(model.equals("")){message="Model";}

        if(message.equals("")){return true;}
        else{
            dialogbox(false,"ERROR","","'"+message+"' field empty!!!");
            return false;}
    }
}
