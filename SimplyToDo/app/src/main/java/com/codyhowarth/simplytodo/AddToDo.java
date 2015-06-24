package com.codyhowarth.simplytodo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.support.v4.app.FragmentActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class AddToDo extends ActionBarActivity {

    public static int input_hour;
    public static int input_minute;
    public static int input_month;
    public static int input_day;
    public static int input_year;
    public static boolean date_chosen = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_do);
        date_chosen = false;

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_add_to_do, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        super.onOptionsItemSelected(item);
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    false);
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user
            input_hour = hourOfDay;
            input_minute = minute;

            // Show the date picker dialog
            DialogFragment newFragment = new DatePickerFragment();
            newFragment.show(getFragmentManager(), "datePicker");


        }

    }


    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            input_month = month;
            input_day = day;
            input_year = year;

            setDueDate(getActivity());

        }
    }

    public void showTimePickerDialog(View view) {
        android.app.DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "timePicker");
        date_chosen = true;
    }

    public static void setDueDate(Activity activity) {
        Calendar input_date = new GregorianCalendar(input_year, input_month, input_day, input_hour, input_minute);
        Date time = input_date.getTime();
        String due_date = DateFormat.getDateTimeInstance().format(time);
        TextView textview_duedate = (TextView) activity.findViewById(R.id.textView4);
        textview_duedate.setText(due_date);
    }

    public void gotoListView(View view) {

        //Get Info to save
        EditText edittext_todoitem = (EditText) findViewById(R.id.editText1);
        String todoStr = edittext_todoitem.getText().toString();
        String due_date;

        if (!date_chosen) {
            due_date = "";
        } else {
            Calendar input_date = new GregorianCalendar(input_year, input_month, input_day,
                                                        input_hour, input_minute);
            Date time = input_date.getTime();
            due_date = DateFormat.getDateTimeInstance().format(time);
        }

        // Create and add todoitem
        TodoItem newtdItem = new TodoItem(todoStr, due_date);
        MainActivity.tdList.addItem(newtdItem);


        // Switch back to the main
        Intent intent = new Intent(AddToDo.this, MainActivity.class);
        startActivity(intent);
    }

    // Override the back button so it doesn't go back to adding a todoitem
    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onStop() {
        Log.d("MY APP LOG:", "App stopped");

        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d("MY APP LOG:", "App destoryed");

        super.onDestroy();
    }




    // use following function

//    public void TimeSet(View v) {
//        final View dialogView = View.inflate(this, R.layout.date_time_picker, null);
//        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
//
//        dialogView.findViewById(R.id.date_time_set).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                DatePicker datePicker = (DatePicker) dialogView.findViewById(R.id.date_picker);
//                TimePicker timePicker = (TimePicker) dialogView.findViewById(R.id.time_picker);
//
//                Calendar calendar = new GregorianCalendar(datePicker.getYear(),
//                        datePicker.getMonth(),
//                        datePicker.getDayOfMonth(),
//                        timePicker.getCurrentHour(),
//                        timePicker.getCurrentMinute());
//
//                Date time = calendar.getTime();
//                String due_date = DateFormat.getDateTimeInstance().format(time);
//                TextView textview_duedate = (TextView) findViewById(R.id.textView4);
//                textview_duedate.setText(due_date);
//
//                alertDialog.dismiss();
//            }
//        });
//        alertDialog.setView(dialogView);
//        alertDialog.show();
//    }

}

