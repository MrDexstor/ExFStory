package com.vuzz.forgestory.api.plotter.js.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;

public class EventManager {
    
    public HashMap<String,ArrayList<Consumer<EventJS>>> events = new HashMap<>();

    public EventManager on(String eventType,Consumer<EventJS> callBack) {
        if(!events.containsKey(eventType)) events.put(eventType,new ArrayList<>());
        ArrayList<Consumer<EventJS>> eventList = events.get(eventType);
        eventList.add(callBack);
        return this;
    }

    public void runEvent(String type, EventJS event) {
        if(!events.containsKey(type)) events.put(type,new ArrayList<>());
        ArrayList<Consumer<EventJS>> eventList = events.get(type);
        eventList.forEach((e) -> {
            e.accept(event);
        });
    }

}
