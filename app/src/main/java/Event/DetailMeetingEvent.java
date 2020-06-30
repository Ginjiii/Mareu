package Event;

import Model.Meeting;

public class DetailMeetingEvent {
    public Meeting mMeeting;

    public DetailMeetingEvent(Meeting meeting) {
        mMeeting = meeting;
    }
}