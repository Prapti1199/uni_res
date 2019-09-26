package com.example.prapti.uni_res;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.text.DateFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class Book_now extends AppCompatActivity implements DatePickerDialog.OnDateSetListener , TimePickerDialog.OnTimeSetListener{

    Spinner sp;
    Spinner sp1 , sp2;
    ImageView home;

    TextView display_data;
    TextView display_data1;
    TextView date , time;

    String names[] = {"CSPIT", "Depstar"};
    String names1[] = {"304-a", "304-b", "305-a",};
    String names2[] = {"301-a","301-b","303-a" };

    ArrayAdapter<String> adapter;
   ArrayAdapter <String> adapter1;
   ArrayAdapter <String> adapter2;

    String record = "";
    String record1 = "";


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_now);

        sp = (Spinner)findViewById(R.id.spinner);
        sp1 = (Spinner)findViewById(R.id.spinner1);
//        sp2 = (Spinner) findViewById(R.id.spinner1);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names);
       // adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names1);
      //  adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names2);

        display_data = (TextView)findViewById(R.id.display_result);
        display_data1 = (TextView)findViewById(R.id.display_result1);

        home = findViewById(R.id.home);

        sp.setAdapter(adapter);
       // sp1.setAdapter(adapter1);
       // sp2.setAdapter(adapter1);

        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
       home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Book_now.this,HomeActivity.class);
                startActivity(intent);
            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatepickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimepickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");

            }
        });


        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                record = names[position];

                Toast.makeText(Book_now.this , "Selected item : "+ record, Toast.LENGTH_SHORT).show();

                if(position == 0){
                     adapter1  = new  ArrayAdapter<String>(Book_now.this , android.R.layout.simple_spinner_dropdown_item, names1);
                     sp1.setAdapter(adapter1);

                    sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            record = names1[position];

                            Toast.makeText(Book_now.this , "Selected item : "+ record, Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                }

                if(position == 1){
                    adapter2  = new  ArrayAdapter<String>(Book_now.this , android.R.layout.simple_spinner_dropdown_item, names2);
                    sp1.setAdapter(adapter2);

                    sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            record = names2[position];

                            Toast.makeText(Book_now.this , "Selected item : "+ record, Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                }

               /* switch (position)
                {
                    case 0:
                        record = "CSPIT";
                        break;

                    case 1:
                        record = "Depstar";
                        break;
                }*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }
    public void displayResult(View view)
    {
        display_data.setTextSize(18);
        display_data.setText(record);
    }
    public void displayResult1(View view)
    {
        display_data1.setTextSize(18);
        display_data1.setText(record1);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance().format(c.getTime());

        TextView textView = (TextView) findViewById(R.id.date);
        date.setText(currentDateString);
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        TextView textView = (TextView)findViewById(R.id.middle);
        time.setText( hourOfDay + ":"+ minute);
        //middlename.setText("Hour : "+ hourOfDay + " Minute : "+ minute);
    }
}

