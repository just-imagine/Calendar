package com.example.calendar;

import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

public class WeekView extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    HorizontalScrollView TopHorizontal;
    HorizontalScrollView BottomHorizontal;
    TableRow TopWeekdays;
    TableRow BottomWeekdays;
    String CurrentMonth;
    String CurrenDate,CheckedDate;
    Intent currentIntent;
    MaterialCalendarView DropDownCalendar;
    TextView WeekDays[];
    TextView MaskWeekdays[];
    ArrayList<Booking>Schedule;
    LinearLayout WeekEvents[];
    boolean tryFirst;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Schedule=new ArrayList<>();
        TopHorizontal=(HorizontalScrollView)findViewById(R.id.Tophorizontal);
        BottomHorizontal=(HorizontalScrollView)findViewById(R.id.horizontalScroll);
        TopWeekdays=(TableRow)findViewById(R.id.TopWeekdays);
        BottomWeekdays=(TableRow)findViewById(R.id.WeekDays);

        TopHorizontal.scrollTo(BottomHorizontal.getScrollX(),BottomHorizontal.getScrollY());
        BottomHorizontal.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
              TopHorizontal.scrollTo(scrollX,scrollY);
            //  AddEvents();
            }
        });


        TopHorizontal.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                BottomHorizontal.scrollTo(scrollX,scrollY);
            }
        });

        currentIntent=getIntent();
        CurrentMonth=currentIntent.getStringExtra("Month");
        CurrenDate=currentIntent.getStringExtra("CurrentDate");
        CheckedDate=currentIntent.getStringExtra("CheckedDate");


        DropDownCalendar=(MaterialCalendarView)findViewById(R.id.DropDownCalendar);
        String formateddate=CheckedDate.substring(0,4)+"/"+CheckedDate.substring(4,6)+"/"+CheckedDate.substring(6,8);

        DropDownCalendar.setDateSelected(Calendar.getInstance().getTime(), true);
        DropDownCalendar.setCurrentDate(ToDate(CheckedDate));
        DropDownCalendar.setDateSelected(ToDate(formateddate), true);
        DropDownCalendar.setCurrentDate(Calendar.getInstance());
        DropDownCalendar.setSelectionColor(Color.RED);
        DropDownCalendar.addDecorator(new CurrentDateDecorator(this));

        DropDownCalendar.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView materialCalendarView, @NonNull CalendarDay calendarDay, boolean b) {
                int year=calendarDay.getYear();
                int month=calendarDay.getMonth()+1;
                int day=calendarDay.getDay();
                String date=""+calendarDay.getDate();

                CheckedDate=AssignVariables(month,day,year,date);

                setDays();

                AddEvents();


            }
        });

        MaskWeekdays=new TextView[7];
        WeekDays=Weekdays();
        setDays();
        setTitle(getMonth(CurrentMonth)+" "+CheckedDate.substring(0,4));
        toolbar.setTitleTextColor(Color.BLACK);
        WeekEvents=Events();
        AddEvents();

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
        getMenuInflater().inflate(R.menu.week_view, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id==R.id.item1){

            ShowCalendar();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public  String[] Time() {
        int value = 15;
        int hour = 8;
        String Times[]=new String[40];
        String Time="";
        String properTime="";
        for (int i = 0; i < 40; ++i) {

            if (value == 60) {
                value = 0;
                ++hour;
            }
            properTime = "" + (hour) + ":" + value;
            Time = "";
            if (hour < 10) {
                if (value != 0) {
                    Time = "0" + properTime;
                } else {
                    Time = "0" + properTime + "0";
                }
            } else {
                if (value != 0) {
                    Time = "" + properTime;
                } else {
                    Time = "" + properTime + "0";
                }
            }
            String hint = Time;
            if (hour < 12) {
                Time = Time + " AM";
            } else {
                Time = Time + " PM";
            }
            value=value+15;
            Times[i]=Time;
        }
        return Times;

    }

    public  void ShowCalendar(){



        TextView ChangeCalendar=(TextView)findViewById(R.id.ChangeCalendar);
        if(DropDownCalendar.getLayoutParams().height==0){
            DropDownCalendar.setLayoutParams(TopWeekdays.getLayoutParams());
        }

        else{
            DropDownCalendar.setLayoutParams(ChangeCalendar.getLayoutParams());
        }

    }

    public  String getMonth(String x){

        if(x.equals("Apr")){
            return  "April";
        }

        else if(x.equals("Mar")){
            return  "March";
        }

        else if(x.equals("Jan")){
            return  "January";
        }

        else if(x.equals("Feb")){
            return  "February";
        }

        else if(x.equals("May")){

            return  "May";
        }

        else if(x.equals("Jun")){
            return "June";
        }

        else if(x.equals("Jul")){
            return  "Jul";

        }

        else if(x.equals("Aug")){
            return  "August";
        }

        else if(x.equals("Sep")){
            return  "September";}

        else if(x.equals("Oct")){
            return  "October";}

        else if(x.equals("Nov")){
            return  "November";}

        else{
            return  "December";}
    }

    public  LinearLayout[] Events(){
        Resources r = getResources();
        String name = getPackageName();
        String EventNames[]={"M","T","W","TH","FR","SA","SU"};
        LinearLayout Events[]=new LinearLayout[77];
        int  value=0;
        for(int i=0;i<7;++i){
            String column=EventNames[i];
            for(int j=8;j<=18;++j){
                LinearLayout temp=(LinearLayout)findViewById(r.getIdentifier(column+""+j,"id",name));
                Events[value]=temp;
                ++value;

                temp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), "you clicked me", Toast.LENGTH_SHORT).show();
                    }
                });
            }

        }
        return Events;
    }


    public void AddEvents(){
        final ArrayList<Booking>tempSchedule=Schedule;

        Schedule.clear();
        String line1=WeekDays[0].getText().toString();
        String line2=WeekDays[WeekDays.length-1].getText().toString();
        String Order[]=DateOrder(line1.split("\n")[1],line2.split("\n")[1]);
        ContentValues Params=new ContentValues();
        Params.put("LDATE",Order[0]);
        Params.put("UDATE",Order[1]);
        final AsyncHTTPPost WeekSchedule=new AsyncHTTPPost("http://lamp.ms.wits.ac.za/~s1611821/ConsultationWeek.php",Params) {
            @Override
            protected void onPostExecute(String output) {
                try {
                    JSONArray results = new JSONArray(output);
                    for (int i = 0; i < results.length(); ++i) {


                         JSONObject obj = results.getJSONObject(i);
                        String Name=obj.getString("NAME");
                        String Surname = obj.getString("SURNAME");
                       String Identity = obj.getString("ID_NUMBER");
                       String Email = obj.getString("EMAIL_ADDRESS");
                        String Contact = obj.getString("CONTACT_NO");
                        String Date = obj.getString("DATE");
                        String Time = obj.getString("TIME").substring(0, 5);
                        int State = obj.getInt("STATE");
                        Booking temp = new Booking(Name, Surname, Identity, Contact, Email, Date, Time, State);
                        Schedule.add(temp);
                    }

                        PopulateDays(WeekDays);


                } catch (JSONException e) {
                     PopulateDays(WeekDays);
                    e.printStackTrace();
                }

            }
        };

        WeekSchedule.execute();
    }

    public TextView[] Weekdays(){

        Resources r = getResources();
        String name = getPackageName();
        String WeekDays[]={"Mon","Tue","Wed","Thu","Fri","Sat","Sun"};
        String DummyWeekDays[]={"F1","F2","F3","F4","F5","F6","F7"};
        TextView Weekdays[]=new TextView[7];
        DropDownCalendar.setCurrentDate(Calendar.getInstance());
        for(int i=0;i<WeekDays.length;++i) {
            TextView weekday,dummyWeekday;
            weekday = (TextView) findViewById(r.getIdentifier(WeekDays[i], "id", name));
            dummyWeekday=(TextView)findViewById(r.getIdentifier(DummyWeekDays[i],"id",name));
            Weekdays[i]=weekday;
            MaskWeekdays[i]=dummyWeekday;

        }
        return  Weekdays;
    }

    public Date ToDate(String line){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        try {
            Date d = sdf.parse(line);
            return  d;
        } catch (ParseException ex) {

            Log.v("Exception", ex.getLocalizedMessage());
            return  null;
        }


    }

    public  int Offset(String line){
        if(line.equals("Mon")){
            return  0;
        }

        else if(line.equals("Tue")){
            return 1;
        }

        else if(line.equals("Wed")){
            return  2;
        }

        else if(line.equals("Thu")){
            return  3;
        }

        else if(line.equals("Fri")){
            return  4;
        }

        else if(line.equals("Sat")){
            return  5;
        }

        else{
            return  6;
        }
    }



    public void setDays(){

        CalendarDay Day=DropDownCalendar.getSelectedDate();
        Date selectedDate=Day.getDate();

        String LongDate=""+selectedDate;
        String DayOfWeek=LongDate.substring(0,3);
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(selectedDate);

        int compare=Integer.parseInt(CurrenDate.substring(6,8).trim());
        int comparemonth=Integer.parseInt(CurrenDate.substring(4,6));
        int startNumber=Integer.parseInt(CheckedDate.substring(6,8).trim())-Offset(DayOfWeek);
        int max=calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        GregorianCalendar date= (GregorianCalendar) GregorianCalendar.getInstance();
        date.set(selectedDate.getYear(),1,1);

        boolean Feb=false;
        int priormax=30;

        if(max==30){
            priormax=31;
        }

        else if( max==31){
            priormax=30;
        }


        if(selectedDate.getMonth()==2){
            if(date.isLeapYear(selectedDate.getYear())){
                priormax=29;
            }

            else{
                priormax=28;
            }
            Feb=true;
        }

        for(int i=0;i<WeekDays.length;++i){
            String line=WeekDays[i].getText().toString().substring(0,3);


            if(startNumber<=max){

                if(startNumber==compare && comparemonth==Day.getMonth()+1){
                    WeekDays[i].setTextColor(Color.BLUE);
                }

                else{
                    WeekDays[i].setTextColor(Color.BLACK);
                }

                if(startNumber<=0){
                    int val=startNumber+priormax;
                    if(Feb){
                        if(val==priormax){
                            startNumber=max;
                        }
                    }

            WeekDays[i].setText(line+"\n"+val);}

                else{
                    WeekDays[i].setText(line+"\n"+startNumber);
                }

            }

            else{
                startNumber=1;
                WeekDays[i].setText(line+"\n"+startNumber);
            }

            MaskWeekdays[i].setText(line+"\n"+startNumber);
            ++startNumber;
        }

        DropDownCalendar.setDateSelected(Calendar.getInstance().getTime(), true);

    }

    public  String AssignVariables(int month,int day,int year,String LongDate){

        String g;
        if(month<10){
            if(day<10)
                g=""+year+"0"+month+"0"+day;

            else {
                g=""+year+"0"+month+""+day;
            }
        }


        else{
            if(day<10)
                g=""+year+month+"0"+day;

            else {
                g=""+year+month+""+day;
            }
        }
        CheckedDate=g;
        String MonthOfYear=LongDate.substring(4,8).trim();
        setTitle(getMonth(MonthOfYear)+" "+CheckedDate.substring(0,4));
        return  g;
    }

    public  String[] DateOrder(String DateOne,String DateTwo){


        int DateOneValue=Integer.parseInt(DateOne);
        int DateTwoValue=Integer.parseInt(DateTwo);

        if(DateOneValue<DateTwoValue){
            if(DateOneValue<10){
            DateOne=CheckedDate.substring(0,4)+CheckedDate.substring(4,6)+"0"+DateOneValue;}

            else{
                DateOne=CheckedDate.substring(0,4)+CheckedDate.substring(4,6)+""+DateOneValue;
            }
            if(DateTwoValue<10){
                DateTwo=CheckedDate.substring(0,4)+CheckedDate.substring(4,6)+"0"+DateTwoValue;
            }

            else{
                DateTwo=CheckedDate.substring(0,4)+CheckedDate.substring(4,6)+""+DateTwoValue;
            }
            String Dates[]={DateOne,DateTwo};
            return  Dates;
        }

        else{
            int nextmonth=Integer.parseInt(CheckedDate.substring(4,6))+1;
            int year=Integer.parseInt(CheckedDate.substring(0,4));
            if(nextmonth==13){
                year=year+1;
                nextmonth=1;

            }
            String month="";


            if(nextmonth<10){
                month="0"+nextmonth;
            }

            else{
                month=""+nextmonth;
            }

            if(DateOneValue<10){
                DateOne=CheckedDate.substring(0,4)+CheckedDate.substring(4,6)+"0"+DateOneValue;}

            else{
                DateOne=CheckedDate.substring(0,4)+CheckedDate.substring(4,6)+""+DateOneValue;
            }
            if(DateTwoValue<10){
                DateTwo=""+year+""+month+"0"+DateTwoValue;
            }

            else{
                DateTwo=year+""+month+""+DateTwoValue;
            }


            String Dates[]={DateOne,DateTwo};
            return  Dates;
        }

    }

    public void PopulateDays(TextView[] Days){
        for(int i=0;i<Days.length;++i){
            TextView day=Days[i];
            String info[]=day.getText().toString().split("\n");
            String line=info[0];
            String line2=info[1];
            PopulateHours(line,line2);
        }

    }

    public void  PopulateHours(String day,String date){
        for(int i=8;i<=18;++i){
            String Id=Identifier(day)+""+i;
            LinearLayout L=getLinearLayout(Id);
            L.removeAllViews();
            ArrayList<Booking>Hour=getBookings(i,date);
            if(!Hour.isEmpty()){
            for(int j=0;j<Hour.size();++j){
                Booking temp=Hour.get(j);
                LinearLayout t=(LinearLayout) View.inflate(this,R.layout.booking_textview,null);
                if(temp.Booked()) {
                    String time = temp.getTime().substring(1, 5);
                    String duration = "";
                    int value = Integer.parseInt(time.substring(2, 4)) + 15;
                    if (value < 60) {
                        duration = time.substring(0, 2) + "" + value;

                    } else {
                        int nexthour = Integer.parseInt(time.substring(0, 1)) + 1;
                        duration = "" + nexthour + "00";
                    }
                    TextView a = (TextView) t.findViewById(R.id.hourdivision);
                    a.setText("APPOINTMENT" + "\n" + time + "-" + duration);
                    L.addView(t);

                }
            }

        }

        else{
             LinearLayout t=(LinearLayout) View.inflate(this,R.layout.booking_textview,null);
            TextView a=(TextView)t.findViewById(R.id.hourdivision) ;
             a.setText("APPOINTMENT"+"\n"+"9:15-9:30");
               a.setTextColor(Color.TRANSPARENT);
               a.setVisibility(View.INVISIBLE);
               L.addView(t);

        }
        }

    }

    public ArrayList<Booking> getBookings(int time,String date){
        ArrayList<Booking>Hours=new ArrayList<>();
        for(int i=0;i<Schedule.size();++i){
            Booking temp=Schedule.get(i);   
             String Day=temp.getDate();
             int Datevalue=Integer.parseInt(Day.substring(8,10));
             int Value=Integer.parseInt(date);
            if(time<10) {
                if (temp.getTime().substring(0, 2).equals("0"+time) && Datevalue==Value){
                    Hours.add(temp);
                }

            }

            else{
                if (temp.getTime().substring(0, 2).equals(""+time) && Datevalue==Value){
                    Hours.add(temp);
                }
            }
        }

        return  Hours;
    }


    public  String Identifier(String day){
        if(day.equals("Mon")){
            return  "M";
        }

        else if(day.equals("Tue")){
            return  "T";
        }

        else if(day.equals("Wed")){
            return  "W";
        }

        else if(day.equals("Thu")){
            return  "TH";
        }

        else if(day.equals("Fri")){
            return  "FR";
        }

        else if(day.equals("Sat")){
            return  "SA";
        }

        else{
            return  "SU";
        }
    }

    public LinearLayout getLinearLayout(String line){

        Resources r = getResources();
        String name = getPackageName();
        LinearLayout L=(LinearLayout)findViewById(r.getIdentifier(line,"id",name));
        L.removeAllViews();
        return  L;
    }

    }
