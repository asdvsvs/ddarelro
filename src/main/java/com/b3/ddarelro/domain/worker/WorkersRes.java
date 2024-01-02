package com.b3.ddarelro.domain.worker;

import com.b3.ddarelro.domain.worker.entity.*;
import lombok.*;

@Builder
public record WorkersRes(Long workerid, String workerName) {

    public static WorkersRes formWith(Worker worker) {
        return WorkersRes.builder()
            .workerid(worker.getId())
            .workerName(worker.getWorkerName())
            .build();
    }
}
