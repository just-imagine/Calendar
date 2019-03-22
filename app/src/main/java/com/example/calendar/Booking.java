package com.example.calendar;

import java.util.Date;

public class Booking {
    String date;
    String time;
    String Patient;
    int Validity;

    public Booking(String date,String time,String Patient,int Validity){
        this.date=date;
        this.time=time;
        this.Patient=Patient;
        this.Validity=Validity;
    }


    boolean Blocked(){
        if(this.Patient.equals("null") && Validity==0){

            return true;
        }

        return  false;
    }

    public String getDate(){
        return date;
    }

    public String getTime(){
        return time;
    }

    public String getPatient(){
        return  Patient;
    }

    boolean Booked(){
        if(Validity==0 && !Blocked()){
            return  true;
        }

        return  false;
    }

    boolean Free(){
        if(Patient.equals("null") && Validity==1){
            return  true;
        }

        return  false;
    }



}
