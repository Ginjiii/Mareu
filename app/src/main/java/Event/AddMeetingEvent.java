package Event;

import Model.Meeting;

public class AddMeetingEvent {

    public Meeting mMeeting;

    public AddMeetingEvent(Meeting meeting) {
        mMeeting = meeting;
    }
}
