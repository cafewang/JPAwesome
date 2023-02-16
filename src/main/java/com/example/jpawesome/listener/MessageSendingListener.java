package com.example.jpawesome.listener;

import com.example.jpawesome.entity.Teacher;
import com.example.jpawesome.event.TeacherCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.persistence.PostPersist;

@Slf4j
@Component
public class MessageSendingListener implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        MessageSendingListener.applicationContext = applicationContext;
    }

    @PostPersist
    public void postPersist(Teacher teacher) {
        applicationContext.publishEvent(new TeacherCreatedEvent(teacher));
    }
}
