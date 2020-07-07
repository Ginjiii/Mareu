package Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Delegate implements Parcelable {

        /** Id */
        private long id;

        /** Email */
        private String email;

        /**
         * Constructor
         */
        public Delegate(long id, String email) {
            this.id = id;
            this.email = email;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }


        /**
         * Parcelable
         */
        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeLong(id);
            dest.writeString(email);
        }

        public static final Creator<Delegate> CREATOR = new Creator<Delegate>() {
            @Override
            public Delegate createFromParcel(Parcel in) {
                return new Delegate(in);
            }

            @Override
            public Delegate[] newArray(int size) {
                return new Delegate[size];
            }
        };

        private Delegate(Parcel in) {
            this.id = in.readLong();
            this.email = in.readString();
        }


    }
