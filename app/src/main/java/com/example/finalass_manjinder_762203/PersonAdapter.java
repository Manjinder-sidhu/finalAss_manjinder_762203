package com.example.finalass_manjinder_762203;

import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PersonAdapter extends ArrayAdapter {

    Context mContext;
    int layoutRes;
    List<PersonClass> persons;
    //SQLiteDatabase mDatabase;

    DatabaseHelper mDatabase;


    public PersonAdapter(@NonNull Context context, int layout_resource,  List<PersonClass> persons, DatabaseHelper mDatabase) {
        super(context, layout_resource, persons);
        this.mContext = context;
        this.layoutRes = layout_resource;
        this.persons = persons;
        this.mDatabase = mDatabase;

    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(layoutRes, null);
        TextView tvfirstName = v.findViewById(R.id.tv_firstname);
        TextView tvlastName = v.findViewById(R.id.tv_lastname);
        TextView tvAddress = v.findViewById(R.id.tv_address);
        TextView tvphone = v.findViewById(R.id.tv_contact);

        final PersonClass person = persons.get(position);
        tvfirstName.setText(person.getFirstname());
        tvlastName.setText(person.getLastname());
        tvAddress.setText(person.getAddress());
        tvphone.setText(person.getPhone_number());



        return v;
    }

    private void loadpersons() {

/*
        String sql = "SELECT * FROM employees";
        Cursor cursor = mDatabase.rawQuery(sql, null);

 */
        Cursor cursor = mDatabase.getAllPersons();

        if(cursor.moveToFirst()){
          persons.clear();
            do{
                persons.add(new PersonClass(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4)
                ));
            }while (cursor.moveToNext());

            cursor.close();
        }
        notifyDataSetChanged();



    }



    public void updatePerson(final PersonClass person) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.dialog_update, null);
        builder.setView(v);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();


        final EditText ETfirstname = v.findViewById(R.id.edittextfirstname);
        final EditText ETlastname = v.findViewById(R.id.edittextlastname);
        final EditText ETaddress = v.findViewById(R.id.edittextaddress);
        final EditText ETphone = v.findViewById(R.id.edittextcontact);


       ETfirstname.setText(person.getFirstname());
        ETlastname.setText(person.getLastname());
        ETaddress.setText(person.getAddress());
        ETphone.setText(person.getPhone_number());

        v.findViewById(R.id.btn_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstname1 = ETfirstname.getText().toString().trim();
                String lastname1 = ETlastname.getText().toString().trim();
                String address1 = ETaddress.getText().toString().trim();
                String phone1 = ETphone.getText().toString().trim();

                if (firstname1.isEmpty()){
                   ETfirstname.setError("firstname field is empty");
                    ETfirstname.requestFocus();
                    return;
                }
                if (lastname1.isEmpty()){
                    ETlastname.setError("lastname field is empty");
                   ETlastname.requestFocus();
                    return;
                }
                if (address1.isEmpty()){
                    ETaddress.setError("address field is empty");
                  ETaddress.requestFocus();
                    return;
                }
                if (phone1.isEmpty()){
                   ETphone.setError("contact number field is empty");
                   ETphone.requestFocus();
                    return;
                }
/*
                String sql = " UPDATE employees SET name =?,salary =?,department=? WHERE id = ?";
              mDatabase.execSQL(sql,new String[]{ name,salary,dept, String.valueOf(employee.getId())});
                Toast.makeText(mContext, "employee update", Toast.LENGTH_SHORT).show();

 */
                if(mDatabase.updateperson(person.getId(), firstname1, lastname1, address1,phone1)){
                    Toast.makeText(mContext, "person update", Toast.LENGTH_SHORT).show();
                    loadpersons();
                }
                else
                    Toast.makeText(mContext, "person not update", Toast.LENGTH_SHORT).show();

                // loadEmployees();
                alertDialog.dismiss();

            }
        });


    }


//    @Override
//    public Filter getFilter() {
//        Filter filter = new Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence constraint) {
//
//                FilterResults filterResults = new FilterResults();
//                if(constraint == null || constraint.length() == 0){
//                    filterResults.count = persons.size();
//                    filterResults.values = persons;
//
//                }else{
//                    List<PersonClass> resultsModel = new ArrayList<>();
//                    String searchStr = constraint.toString().toLowerCase();
//
//                    for(PersonClass  person1 : persons){
//                        if(person1.getFirstname().contains(searchStr) ){
//                            resultsModel.add(person1);
//
//                        }
//                        filterResults.count = resultsModel.size();
//                        filterResults.values = resultsModel;
//                    }
//
//
//                }
//
//                return filterResults;
//            }
//
//            @Override
//            protected void publishResults(CharSequence constraint, FilterResults results) {
////              PersonClass  itemsModelListFiltered = (List<PersonClass>) results.values;
//                notifyDataSetChanged();
//
//            }
//        };
//        return filter;
//    }

    }
