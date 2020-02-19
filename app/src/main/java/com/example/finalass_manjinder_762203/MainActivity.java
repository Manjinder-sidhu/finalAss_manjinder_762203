package com.example.finalass_manjinder_762203;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


DatabaseHelper mDatabase;
EditText firstname,lastname,address,phone;
TextView count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        firstname = findViewById(R.id.first_name);
        lastname = findViewById(R.id.last_name);
        address = findViewById(R.id.address);
        phone = findViewById(R.id.phone_number);
        count = findViewById(R.id.countshow);


        findViewById(R.id.btn_add_person).setOnClickListener(this);
        findViewById(R.id.view_persons).setOnClickListener(this);


        mDatabase = new DatabaseHelper(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_add_person:
                addperson();
                break;

            case R.id.view_persons:
                //start activity to another activity to use the list of employees
                Intent intent = new Intent(MainActivity.this,PersonList.class);
                startActivity(intent);

                break;
                
            case R.id.btn_count:
              long i =   mDatabase.getTaskCount();
              count.setText(String.valueOf(i));
                Toast.makeText(this, "bjhdgvfchevh", Toast.LENGTH_SHORT).show();
              break;

        }
    }


    private void addperson() {
        String first_name = firstname.getText().toString().trim();
        String last_name = lastname.getText().toString().trim();
        String address1 = address.getText().toString().trim();
        String contact = phone.getText().toString().trim();


        if (first_name.isEmpty()){
            firstname.setError("firstname field is empty");
           firstname.requestFocus();
            return;
        }
        if (last_name.isEmpty()){
            lastname.setError("lastname field is empty");
            lastname.requestFocus();
            return;
        }
        if (address1.isEmpty()){
            address.setError("address field is empty");
            address.requestFocus();
            return;
        }
        if (contact.isEmpty()){
           phone.setError("contact number field is empty");
            phone.requestFocus();
            return;
        }




        //new method
        if (mDatabase.addPerson(first_name,last_name,address1,contact))
            Toast.makeText(this, "Person added", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Person not added", Toast.LENGTH_SHORT).show();


    }

}
