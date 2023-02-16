package com.example.jpawesome.service;

import com.example.jpawesome.entity.Message;
import com.example.jpawesome.entity.Teacher;
import com.example.jpawesome.repo.MessageRepo;
import com.example.jpawesome.repo.TeacherRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.Instant;

@Component
@Slf4j
public class MessageService {
    @Resource
    private TeacherRepo teacherRepo;
    @Resource
    private MessageRepo messageRepo;

    public void sendMessage(Teacher teacher) {
        Teacher teacherFetched = teacherRepo.findById(teacher.getId()).orElse(null);
        Message message = new Message();
        message.setContent(teacherFetched.toString());
        message.setSentAt(Instant.now().plusSeconds(60L));
        log.info("message saved: {}", messageRepo.save(message));
    }
}
