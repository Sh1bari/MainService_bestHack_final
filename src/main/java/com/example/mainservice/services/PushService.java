package com.example.mainservice.services;

import com.example.mainservice.exceptions.PushHistoryNotFoundExc;
import com.example.mainservice.exceptions.PushNotFoundExc;
import com.example.mainservice.models.entities.Department;
import com.example.mainservice.models.entities.Push;
import com.example.mainservice.models.entities.PushHistory;
import com.example.mainservice.models.entities.User;
import com.example.mainservice.models.models.requests.PushSendDtoReq;
import com.example.mainservice.repositories.PushHistoryRepo;
import com.example.mainservice.repositories.PushRepo;
import lombok.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PushService {
    private final PushRepo pushRepo;
    private final PushHistoryRepo pushHistoryRepo;
    private final DepartmentService departmentService;
    private final UserService userService;
    private final FCMService fcmService;

    @Transactional
    public Push createPush(UUID departmentId, User fromUId,PushSendDtoReq req){
        Push push = new Push();
        push.setTitle(req.getTitle());
        push.setBody(req.getBody());
        if(departmentId!=null){
            Department dep = departmentService.findById(departmentId);
            push.setFromDepartment(dep);
        }
        User user = userService.findById(fromUId.getId());
        push.setCreatorUser(user);
        pushRepo.save(push);
        Set<User> userSet = userService.getUsersForSend(req.getToDepartmentRoles(), req.getToUserId())
                .stream()
                .filter(o->o.getPushToken()!=null)
                .collect(Collectors.toSet());
        Set<PushHistory> pushSet = new HashSet<>();
        LocalDateTime time = LocalDateTime.now();
        userSet.forEach(o->{
            PushHistory ph = new PushHistory();
            ph.setPushTime(time);
            ph.setPush(push);
            ph.setToUser(o);
            pushSet.add(ph);
        });
        fcmService.sendNotification(userSet.stream()
                .map(User::getPushToken).toList(),
                req.getTitle(),
                req.getBody());
        pushHistoryRepo.saveAll(pushSet);
        return push;
    }
    public PushHistory findById(Long id){
        return pushHistoryRepo.findById(id)
                .orElseThrow(PushHistoryNotFoundExc::new);
    }

    public Push findById(UUID id){
        return pushRepo.findById(id)
                .orElseThrow(PushNotFoundExc::new);
    }
}
