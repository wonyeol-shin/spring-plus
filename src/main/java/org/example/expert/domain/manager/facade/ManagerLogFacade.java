package org.example.expert.domain.manager.facade;

import lombok.RequiredArgsConstructor;
import org.example.expert.domain.common.dto.AuthUser;
import org.example.expert.domain.log.entity.ServiceLog;
import org.example.expert.domain.log.enums.LogType;
import org.example.expert.domain.log.enums.Result;
import org.example.expert.domain.log.service.ServiceLogService;
import org.example.expert.domain.manager.dto.request.ManagerSaveRequest;
import org.example.expert.domain.manager.dto.response.ManagerSaveResponse;
import org.example.expert.domain.manager.service.ManagerService;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class ManagerLogFacade {

    private final ManagerService managerService;
    private final ServiceLogService serviceLogService;

    public ManagerSaveResponse  saveManager
            (AuthUser authUser, long todoId, ManagerSaveRequest managerSaveRequest)
    {
        try {
            ManagerSaveResponse managerSaveResponse = managerService.saveManager(authUser, todoId, managerSaveRequest);
            ServiceLog serviceLog = ServiceLog.of(LogType.MANAGER_REGISTER, Result.SUCCESS, todoId + " 일정에 등록 할 유저: " + managerSaveRequest.getManagerUserId());
            serviceLogService.save(serviceLog);
            return managerSaveResponse;
        }catch (Exception e){ // 공통 예외를 만들어서 공통 예외를 받게 수정 팔요
            ServiceLog serviceLog = ServiceLog.of(LogType.MANAGER_REGISTER, Result.FAILED, todoId + " 일정에 등록 하려던 유저: " + managerSaveRequest.getManagerUserId());
            serviceLogService.save(serviceLog);
            throw e;
        }

    }

}
