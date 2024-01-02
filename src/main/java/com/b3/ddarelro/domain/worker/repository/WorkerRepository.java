package com.b3.ddarelro.domain.worker.repository;

import com.b3.ddarelro.domain.card.entity.*;
import com.b3.ddarelro.domain.worker.entity.*;
import java.util.*;
import org.springframework.data.jpa.repository.*;

public interface WorkerRepository extends JpaRepository<Worker, Long> {

    Optional<Worker> findByWorkerNameAndCard(String workerName, Card card);

    Worker findByWorkerName(String workerName);
}
