package Service;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Model.Meeting;

public abstract class DummyMeetingGenerator {

    public static List<Meeting> DUMMY_MEETINGS = Arrays.asList(
            new Meeting("26/05/20", "13:00", "Mario", "Test", new ArrayList(Arrays.asList("alex@lamzone.com", "maxime@lamzone.com"))),
            new Meeting("26/05/20", "14:00", "Peach", "App", new ArrayList(Arrays.asList("alex@lamzone.com", "maxime@lamzone.com"))),
            new Meeting("26/05/20", "15:30", "Mario","Have a break",new ArrayList(Arrays.asList("alex@lamzone.com", "maxime@lamzone.com"))),
            new Meeting("27/05/20", "9:00", "Luigi","Room", new ArrayList(Arrays.asList("Paul@lamzone.com", "viviane@lamzone.com"))),
            new Meeting("27/05/20", "10:00", "Luigi","Test4", new ArrayList(Arrays.asList("amandine@lamzone.com", "luc@lamzone.com"))),
            new Meeting("27/05/20", "15:00", "Mario","Test5", new ArrayList(Arrays.asList("alex@lamzone.com", "viviane@lamzone.com"))),
            new Meeting("27/05/20", "10:00", "Luigi","Plus de reu", new ArrayList(Arrays.asList("amandine@lamzone.com", "luc@lamzone.com", "luc@lamzone.com"))),
            new Meeting("27/05/20", "15:00", "Mario","Encore", new ArrayList(Arrays.asList("alex@lamzone.com", "viviane@lamzone.com", "amandine@lamzone.com")))
    );
    static List<Meeting> generateDummyMeetings(){return new ArrayList<>(DUMMY_MEETINGS);}
    }
