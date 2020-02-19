package com.example.finalass_manjinder_762203;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PersonList extends AppCompatActivity {

    DatabaseHelper mDatabase;
    List<PersonClass> personList;
//     ListView listView;
    SwipeMenuListView listView;
    PersonAdapter personAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_list);

        listView = findViewById(R.id.listview);
        personList = new ArrayList<>();
        mDatabase = new DatabaseHelper(this);
        loadPersons();


        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem editItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                editItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));
                // set item width
                editItem.setWidth((250));

                // set item title fontsize
                editItem.setTitleSize(18);
                // set item title font color
                editItem.setTitleColor(Color.WHITE);
                // add to menu

                editItem.setIcon(R.drawable.ic_update);
                menu.addMenuItem(editItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth((250));
                // set a icon
                deleteItem.setIcon(R.drawable.ic_delete);
                // add to menu
                menu.addMenuItem(deleteItem);

            }
        };

        listView.setMenuCreator(creator);



        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        // update

                        Toast.makeText(PersonList.this, "update clicked", Toast.LENGTH_SHORT).show();
                        PersonClass person1 = personList.get(position);
                        personAdapter.updatePerson(person1);
                        personList.clear();
//                        personList.addAll((Collection<? extends PersonClass>) mDatabase.getAllPersons());
                        personAdapter.notifyDataSetChanged();
                        listView.invalidateViews();
                        listView.refreshDrawableState();

                        break;

                    case 1:
                        // delete
                        Toast.makeText(PersonList.this, "delete clicked", Toast.LENGTH_SHORT).show();
                        PersonClass person2 = personList.get(position);
                        int id2 = person2.getId();
                        if(mDatabase.deletePerson(id2))
                            personList.remove(position);
                       personAdapter.notifyDataSetChanged();

                        break;
                }


                return true;
            }
        });

    }


    private void loadPersons() {
        /*
        String sql = "SELECT * FROM employees";


        Cursor cursor = mDataBase.rawQuery(sql, null);

         */
        Cursor cursor = mDatabase.getAllPersons();
        if(cursor.moveToFirst()){
            do {
                personList.add(new PersonClass(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4)
                ));


            }while (cursor.moveToNext());
            cursor.close();
            //show item in a listView
            //we use a custom adapter to show employees
            personAdapter = new PersonAdapter(this, R.layout.list_item_layout, personList, mDatabase);
            listView.setAdapter(personAdapter);

        }
    }


}
