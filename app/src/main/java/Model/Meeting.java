package Model;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Locale;
import java.util.Objects;

public class Meeting implements Parcelable{

    private String date;
    private String time;
    private String location;
    private String subject;
    private ArrayList<String> delegates;

    /**
     * Constructor
     * @param date
     * @param time
     * @param location
     * @param subject
     * @param delegates
     */

    public Meeting(String date, String time, String location, String subject, ArrayList<String> delegates) {
        this.date = date;
        this.time = time;
        this.location = location;
        this.subject = subject;
        this.delegates = delegates;
    }

    public Meeting(String s, String s1, String mario, String test, String s2, String s3) {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime() {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public ArrayList<String> getDelegates() {
        return delegates;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(date);
        dest.writeString(time);
        dest.writeString(location);
        dest.writeString(subject);
        dest.writeStringList(delegates);
    }

    protected Meeting(Parcel in) {
        date = in.readString();
        time = in.readString();
        location = in.readString();
        subject = in.readString();
        delegates = in.createStringArrayList();
    }

    public static final Parcelable.Creator<Meeting> CREATOR = new Parcelable.Creator<Meeting>() {
        @Override
        public Meeting createFromParcel(Parcel in) {
            return new Meeting(in);
        }

        @Override
        public Meeting[] newArray(int size) {
            return new Meeting[0];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meeting meeting = (Meeting) o;
        return date == meeting.date && time ==meeting.time && location == meeting.location && subject == meeting.subject && delegates == meeting.delegates;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(date);
    }
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Comparator<Meeting> byAlpha = new Comparator<Meeting>() {
        @Override
        public int compare(Meeting r1, Meeting r2) {
            return r1.subject.compareTo(r2.subject);
        }
    };

    public static final Comparator<Meeting> byDate = new Comparator<Meeting>() {
        @Override
        public int compare(Meeting r1, Meeting r2) {
            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("dd/MM/yy hh:mm", Locale.FRANCE);
            String dateStr1 = (r1.getDate().toString() + " " + r1.getTime().toString()).toString();
            String dateStr2 = (r2.getDate().toString() + " " + r2.getTime().toString()).toString();


            try {
                return mSimpleDateFormat.parse(dateStr1).compareTo(mSimpleDateFormat.parse(dateStr2));
            } catch (ParseException e) {
                throw new IllegalArgumentException(e);
            }

        }
    };
}
