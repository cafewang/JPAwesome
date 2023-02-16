package com.example.jpawesome.event;

import org.springframework.context.ApplicationEvent;

public class TeacherCreatedEvent extends ApplicationEvent {
    public TeacherCreatedEvent(Object source) {
        super(source);
    }
}
