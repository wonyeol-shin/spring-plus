package org.example.expert.domain.log.service;

import lombok.RequiredArgsConstructor;
import org.example.expert.domain.log.entity.ServiceLog;
import org.example.expert.domain.log.repository.ServiceLogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ServiceLogService {

    private final ServiceLogRepository serviceLogRepository;

    // 로그는 비즈니스 로직 실패 여부와 관계없이 별도로 저장이 되어야 함
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ServiceLog save(ServiceLog serviceLog) {
        return serviceLogRepository.save(serviceLog);
    }
}
