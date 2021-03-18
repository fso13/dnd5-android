package ru.drudenko.dnd.model.random;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

public class RandomEvent {
    final String title;
    final int max;
    final List<Event> eventList;

    public RandomEvent(String title, int max, List<Event> eventList) {
        this.title = title;
        this.max = max;
        this.eventList = eventList;
    }

    public String getTitle() {
        return title;
    }

    public int getMax() {
        return max;
    }

    public List<Event> getEventList() {
        return eventList;
    }

    public Event random() {
        int max = this.max;
        max -= 1;
        int value = (int) (Math.random() * ++max) + 1;

        for (Event event : eventList) {
            if (event.isRange(value)) {
                return event;
            }
        }
        System.out.println(value);
        return null;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<String> toListString() {

        List<String> strings = new ArrayList<>();
        eventList.sort((o1, o2) -> Integer.compare(o1.getMin(), o2.getMin()));
        for (Event event : eventList) {
            String p = event.getMin() == event.getMax() ? String.valueOf(event.getMin()) : event.getMin() + " - " + event.getMax();
            strings.add(p + ": " + event.getDescription());
        }
        return strings;
    }

    public static class Event {
        private final int min;
        private final int max;
        private final String description;

        public Event(int min, int max, String description) {
            this.min = min;
            this.max = max;
            this.description = description;
        }

        public int getMin() {
            return min;
        }

        public int getMax() {
            return max;
        }

        public boolean isRange(int value) {
            return value >= min && value <= max;
        }

        public String getDescription() {
            return description;
        }
    }
}
