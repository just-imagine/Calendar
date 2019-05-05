package com.example.calendar;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.widget.TextView;

import java.util.ArrayList;

public class Booking {


   private String Name;
   private String Surname;
   private String Identity;
   private String Date;
   private String Time;
   private String Checkeouttime;
   private String Contact;
   private String Email;
   private int cardidentifier;
   private int state;
   private int Delay=0;

    public Booking(String Name, String Surname, String Identity,String Contact,String Email, String Date, String Time,String checkeouttime,int state){
        this.Name=Name;
        this.Surname=Surname;
        this.Identity=Identity;
        this.Contact=Contact;
        this.Email=Email;
        this.Date=Date;
        this.Time=Time;
        this.Checkeouttime=checkeouttime;
        this.state=state;
    }


    public void SetCardidentifier(int id){
        this.cardidentifier=id;
    }
    public String getName(){
        return  Name;
    }

    public  String getContact(){
        return  Contact;
    }

    public  String getEmail(){
        return  Email;
    }

    public String getIdentity() {
        return Identity;
    }

    public String getTime() {
        return Time;
    }

    public String getSurname() {
        return Surname;
    }

    public String getDate() {
        return Date;
    }

    public  String getCheckeouttime(){
        return  Checkeouttime;
    }

    public int getCardidentifier(){
        return  cardidentifier;
    }


    boolean Equal(Booking other){
        if(other!=null) {
            if (this.Time.equals(other.getTime()) && this.Date.equals(other.getDate()))
                return true;
        }
        return  false;
    }
    boolean Empty(){
        if(Identity.equals("")){
            return true;
        }

        else return false;
    }

    boolean Blocked(){
        if(Identity.equals("Admin")){
            return  true;
        }

        return  false;
    }



    boolean Booked(){
        if(!Identity.equals("null") && !Blocked() && state==0){
            return  true;
        }

        return  false;
    }

    boolean Completed(){
        if(state==1){
            return  true;
        }

        return  false;
    }

    int Difference(){
        String dataOne[]=Time.split(":");
        String dataTwo[]=Checkeouttime.split(":");

        String line1="";
        String line2="";
        String m1="";
        String m2="";


        line1=line1+dataOne[0];
        line2=line2+dataTwo[0];
        m1=dataOne[1];
        m2=dataTwo[1];

        int d=Math.abs(Integer.parseInt(m2)-Integer.parseInt(m1))/60;
        //int x=Math.abs(Integer.parseInt(m2)-Integer.parseInt(m1));

        int diff=(Integer.parseInt(line2)-Integer.parseInt(line1))+d;


        //String l=""+diff+""+x;

        return  diff;

    }

    public double getDelay(){
        return  this.Delay;
    }



    public void OccupySlots(ArrayList<TextView>SLots,Booking Other){
        for(int i=0;i< SLots.size();++i){
            TextView Slot=SLots.get(i);
            if(Slot.getHint().equals(Time)){

                if(this.Booked()){
                    Slot.setBackgroundColor(Color.parseColor("#4eacc8"));
                    Slot.setText("Appointment");
                    Slot.setTextColor(Color.WHITE);
                }

                else if(this.Completed() && !this.Equal(Other)){
                    Slot.setBackgroundColor(Color.parseColor("#003366"));
                    Slot.setText("Attended");
                    this.Delay=this.Difference();
                    Slot.setTextColor(Color.WHITE);
                }

                else if(this.Completed() && this.Equal(Other)){
                    Slot.setBackgroundColor(Color.parseColor("#003366"));
                    Slot.setText("Attended");
                    this.Delay=this.Difference();
                    Slot.setTextColor(Color.WHITE);
                    Other.SetCardidentifier(Slot.getId());
                }
                else if(this.Blocked()){
                    Slot.setBackgroundColor(Color.parseColor("#d13c04"));
                    Slot.setText("Blocked");
                    Slot.setTextColor(Color.WHITE);
                }

                else if(this.Empty()){
                    Slot.setBackgroundColor(Color.parseColor("#008577"));
                    Slot.setText("Free");
                    Slot.setTextColor(Color.WHITE);
                }

                break;
            }


        }
    }





}
