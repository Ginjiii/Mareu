package com.example.mareu;

        import android.app.DatePickerDialog;
        import android.app.Dialog;
        import android.app.TimePickerDialog;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.graphics.Color;
        import android.graphics.drawable.ColorDrawable;
        import android.os.Bundle;
        import android.util.Patterns;
        import android.view.LayoutInflater;
        import android.view.MotionEvent;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.DatePicker;
        import android.widget.EditText;
        import android.widget.ImageButton;
        import android.widget.TextView;
        import android.widget.TimePicker;

        import androidx.annotation.NonNull;
        import androidx.annotation.Nullable;
        import androidx.appcompat.widget.Toolbar;
        import androidx.fragment.app.DialogFragment;

        import DI.DI;
        import Event.AddMeetingEvent;
        import Model.Meeting;
        import Service.MeetingApiService;


        import com.google.android.material.textfield.TextInputEditText;
        import com.google.android.material.textfield.TextInputLayout;


        import org.greenrobot.eventbus.EventBus;

        import java.text.ParseException;
        import java.text.SimpleDateFormat;
        import java.util.ArrayList;
        import java.util.Arrays;
        import java.util.Calendar;
        import java.util.Date;



public class AddMeetingFragment extends DialogFragment {
    private DatePickerDialog.OnDateSetListener mOnDateSetListener;
    private TimePickerDialog.OnTimeSetListener mOnTimeSetListener;
    private MeetingApiService mApiService;
    private ArrayList<Meeting> mMeetings;
    private ArrayList<String> mDelegates;


    public static AddMeetingFragment newDialogInstance(){
        AddMeetingFragment dialog = new AddMeetingFragment();
        return dialog;
    }



    private Toolbar mToolbar;
    private ImageButton mReturnButton;
    private TextView mTextView;

    private TextInputLayout mSubjectInput;
    private TextInputLayout mDateLayout;
    private TextInputEditText mDateInput;
    private TextInputLayout mTimeLayout;
    private TextInputEditText mTimeInput;
    private TextInputLayout mLocationInput;
    private TextInputLayout mDelegatesInput;
    private ImageButton mAddDelegatesButton;
    private Button mAddButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meeting_add, container, false);
        mToolbar = view.findViewById(R.id.toolbar_new);
        mReturnButton = view.findViewById(R.id.back_button);
        mTextView = view.findViewById(R.id.toolbar_txt);

        mSubjectInput = view.findViewById(R.id.input_subject);
        mDateLayout = view.findViewById(R.id.date_layout);
        mDateInput = view.findViewById(R.id.input_date);
        mTimeInput = view.findViewById(R.id.input_time);
        mTimeLayout = view.findViewById(R.id.time_layout);
        mLocationInput = view.findViewById(R.id.input_location);
        mDelegatesInput = view.findViewById(R.id.input_delegate);
        mAddDelegatesButton = view.findViewById(R.id.add_delegate_btn);



        mAddButton = view.findViewById(R.id.add_btn);

        mApiService = DI.getNewInstanceApiService();

        mDateInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog dialog = new DatePickerDialog(getDialog().getContext(), R.style.Theme_AppCompat_Light_Dialog_MinWidth,
                        mOnDateSetListener,year, month, day );

                dialog.show();

            }
        });
        mOnDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = dayOfMonth + "/" + (month+1) + "/" + year ;
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
                String dateText = null;
                try {
                    Date date1 = sdf.parse(date);
                    dateText = sdf.format(date1);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                mDateInput.setText(dateText);
            }

        };

        mTimeInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int min = calendar.get(Calendar.MINUTE);
                TimePickerDialog mDialog = new TimePickerDialog(getDialog().getContext(), R.style.Theme_AppCompat_Dialog_MinWidth,
                        mOnTimeSetListener, hour, min, true);
                mDialog.show();
            }
        });

        mOnTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String time = hourOfDay + ":" + minute;
                mTimeInput.setText(time);
            }
        };

        mDelegates = new ArrayList<String>();

        mAddDelegatesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = mDelegatesInput.getEditText().getText().toString();
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    mDelegatesInput.setError("invalid email");
                }else if (mDelegates.contains(email)){
                    mDelegatesInput.setError("delegate already exists");
                } else{
                    mDelegatesInput.setErrorEnabled(false);
                    mDelegates.add(email);
                    mDelegatesInput.getEditText().setText("");
                }


            }
        });


        mReturnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exit();
            }
        });

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirm(view);

            }

        });
        return view;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme_Full_screen_dialogue);
    }

    @Override
    public void onStart() {
        super.onStart();
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme_Full_screen_dialogue);
        Dialog dialog = getDialog();

    }


    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);

    }

    public void exit(){
        getDialog().cancel();
    }

    private Boolean validateSubject(){
        String subject = mSubjectInput.getEditText().getText().toString();

        if (subject.isEmpty()){
            mSubjectInput.setError("subject can't be empty");
            return false;
        }else if (subject.length() >15){
            mSubjectInput.setError("subject too long");
            return false;

        }else {
            mSubjectInput.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validateEmails(){


        if (mDelegates.size() <2){

            mDelegatesInput.setError("please, add at least" + " " + (2-mDelegates.size())+ " " + "delegates");
            return false;
        }else {
            mDelegatesInput.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validateLocation(){
        String location = mLocationInput.getEditText().getText().toString();
        if (location.isEmpty()){
            mLocationInput.setError("location can't be empty");
            return false;
        }else if (location.length() >8){
            mLocationInput.setError("location too long");
            return false;

        }else {
            mLocationInput.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateDate(){
        String date = mDateInput.getText().toString();
        if (date.isEmpty()){
            mDateLayout.setError("Date can't be empty");
            return false;
        }else {
            mDateLayout.setErrorEnabled(false);
            return true;
        }

    }
    private Boolean validateTime(){
        String time = mTimeInput.getText().toString();
        if (time.isEmpty()){
            mTimeLayout.setError("Time can't be empty");
            return false;
        }else {
            mTimeLayout.setErrorEnabled(false);
            return true;
        }
    }

    public void confirm(View v){
        if (!validateSubject() | !validateDate()|!validateTime() | !validateEmails() |!validateLocation()){
            return;
        }else {
            Meeting newMeeting = new Meeting(mDateInput.getText().toString(), mTimeInput.getText().toString() ,mLocationInput.getEditText().getText().toString(), mSubjectInput.getEditText().getText().toString(), mDelegates);
            EventBus.getDefault().post(new AddMeetingEvent(newMeeting));

            dismiss();
        }
    }
}

