package org.example.cleancode.catalog.defensive_copy;

import java.time.LocalDate;
import java.util.Date;

// klsaik date çrmeği
public class Event {

    private final LocalDate eventDate; // imuutable kullandık sorun çözülür
    private final Date date; // ama diyelim ki illaki muttable kullandık

    public Event(LocalDate eventDate,Date date) {
        this.eventDate = eventDate;
        this.date=new Date(date.getTime()); // böyle yaparak defensive copy yaptık
    }

    public LocalDate getEventDate() {
        return eventDate;
    }
    public Date getDate() {
        return new Date(date.getTime()); // defensive copy yaptık
    }


}
