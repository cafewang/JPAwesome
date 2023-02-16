package com.example.jpawesome.eventhandler;

import com.example.jpawesome.entity.Teacher;
import com.example.jpawesome.event.TeacherCreatedEvent;
import com.example.jpawesome.service.MessageService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import javax.annotation.Resource;

@Component
public class TeacherCreationHandler {
    @Resource
    private MessageService messageService;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void teacherCreated(TeacherCreatedEvent teacherCreatedEvent) {
        messageService.sendMessage((Teacher) teacherCreatedEvent.getSource());
    }
}
