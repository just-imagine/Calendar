package com.example.calendar;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewTreeObserver;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateLongClickListener;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.util.Calendar;
import java.util.Date;

public class HomeScreen extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //global variables that we need access to anywhere
    Intent currentIntent;
    MaterialCalendarView Calendar;
    Month calendarMonth;
    ImageView monthTheme;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        monthTheme=(ImageView)findViewById(R.id.display);

        final java.util.Calendar calendar = java.util.Calendar.getInstance();

        //initialize the material calendar to be used and give it the current date
        Calendar = (MaterialCalendarView) findViewById(R.id.thing);
        Calendar.setDateSelected(calendar.getTime(), true);
        Calendar.setSelectionColor(Color.RED);
        Calendar.setCurrentDate(calendar);
        Calendar.addDecorator(new CurrentDateDecorator(this));

        //now create a month object that will respond and make updates when date changes
        calendarMonth=new Month();


        //everytime we change the viewing date we need to update the viewed date for month object
        Calendar.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView materialCalendarView, @NonNull CalendarDay calendarDay, boolean b) {
                calendarMonth.setCheckedDate(calendarMonth.changeCheckedDate(calendarDay));
                calendarMonth.setDate(calendarDay.getDate());
                Calendar.setDateSelected(calendar.getTime(), true);

            }
        });

        //everytime we change the month we need to change the month theme i.e the picture and the title on top
        Calendar.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView materialCalendarView, CalendarDay calendarDay) {
                calendarMonth.setCheckedDate(calendarMonth.changeCheckedDate(calendarDay));
                calendarMonth.setDate(calendarDay.getDate());
                setTitle(calendarMonth.getMonth()+" "+calendarMonth.getYear());
                changeTheme();

            }
        });

        Calendar.setOnDateLongClickListener(new OnDateLongClickListener() {
            @Override
            public void onDateLongClick(@NonNull MaterialCalendarView materialCalendarView, @NonNull CalendarDay calendarDay) {
                calendarMonth.setCheckedDate(calendarMonth.changeCheckedDate(calendarDay));
                calendarMonth.setDate(calendarDay.getDate());
                Intent DailyView=new Intent(getApplicationContext(),DailyView.class);
                //pass data to next intent
                DailyView.putExtra("checkedDate",calendarMonth.getCheckedDate());
                startActivity(DailyView);

            }
        });

        //this should rebase the calendar to the current date
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar.setSelectedDate(calendar);
                Calendar.setCurrentDate(calendar);
            }
        });

        //toolbar should write in black
        toolbar.setTitleTextColor(Color.BLACK);

        //set the title first time around
        setTitle(calendarMonth.getMonth()+" "+calendarMonth.getYear());

        //retrieve any data that was passed from previous intent
        currentIntent=getIntent();


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_day) {
            Intent DailyView=new Intent(getApplicationContext(),DailyView.class);
            DailyView.putExtra("checkedDate",calendarMonth.getCheckedDate());
            startActivity(DailyView);


        } else if (id == R.id.nav_week) {
            Intent weekView=new Intent(getApplicationContext(),WeekView.class);
            weekView.putExtra("Month",calendarMonth.getMonth());
            weekView.putExtra("CurrentDate",calendarMonth.getCurrentDate());
            weekView.putExtra("CheckedDate",calendarMonth.getCheckedDate());
            weekView.putExtra("LongCurrentDate",""+Calendar.getSelectedDate().getDate());
            startActivity(weekView);

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //Update image this is a ui update therefore must be done here
    void changeTheme(){
        String Month=calendarMonth.getMonthName(calendarMonth.getMonthIndex());
        if(Month.equals("April")){
            monthTheme.setImageResource(R.drawable.april);
        }
        else if(Month.equals("March")){
            monthTheme.setImageResource(R.drawable.march);
        }
        else if(Month.equals("January")){
            monthTheme.setImageResource(R.drawable.january);
        }
        else if(Month.equals("February")){
            monthTheme.setImageResource(R.drawable.february);
        }
        else if(Month.equals("May")){
            monthTheme.setImageResource(R.drawable.may);
        }
        else if(Month.equals("June")){
            monthTheme.setImageResource(R.drawable.june);
        }
        else if(Month.equals("July")){
            monthTheme.setImageResource(R.drawable.july);
        }
        else if(Month.equals("August")){
            monthTheme.setImageResource(R.drawable.august);
        }
        else if(Month.equals("September")){
            monthTheme.setImageResource(R.drawable.september);}
        else if(Month.equals("October")){
            monthTheme.setImageResource(R.drawable.october);}
        else if(Month.equals("November")){
            monthTheme.setImageResource(R.drawable.november);}
        else{
            monthTheme.setImageResource(R.drawable.december);}
    }


}
