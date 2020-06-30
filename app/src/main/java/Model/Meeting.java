package Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Locale;

public class Meeting {
    private String date;
    private String time;
    private String location;
    private String subject;
    private ArrayList<String> delegates;


    public Meeting(String date, String time, String location, String subject, ArrayList<String> delegates) {
        this.date = date;
        this.time = time;
        this.location = location;
        this.subject = subject;
        this.delegates = delegates;
    }

    public static final Comparator<Meeting> byAlpha = new Comparator<Meeting>() {
        @Override
        public int compare(Meeting r1, Meeting r2) {
            return r1.subject.compareTo(r2.subject);
        }
    };

    public static final Comparator<Meeting> byDate  = new Comparator<Meeting>() {
        @Override
        public int compare(Meeting r1, Meeting r2) {
            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("dd/MM/yy hh:mm", Locale.FRANCE);
            String dateStr1 = (r1.getDate().toString()+ " " + r1.getTime().toString()).toString() ;
            String dateStr2 = (r2.getDate().toString()+ " " + r2.getTime().toString()).toString() ;


            try {
                return mSimpleDateFormat.parse(dateStr1).compareTo(mSimpleDateFormat.parse(dateStr2));
            }catch (ParseException e){
                throw new IllegalArgumentException(e);
            }

        }
    };

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getLocation() {
        return location;
    }

    public String getSubject() {
        return subject;
    }

    public ArrayList getDelegates() {
        return delegates;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setDelegates(ArrayList delegates) {
        this.delegates = delegates;
    }
}