package com.b3.ddarelro.domain.checklist.repository;

import com.b3.ddarelro.domain.checklist.entity.CheckList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckListRepository extends JpaRepository<CheckList, Long> {

}
