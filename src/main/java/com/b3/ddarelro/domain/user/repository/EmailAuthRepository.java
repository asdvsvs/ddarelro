package com.b3.ddarelro.domain.user.repository;

import com.b3.ddarelro.domain.user.entity.EmailAuth;
import org.springframework.data.repository.CrudRepository;

public interface EmailAuthRepository extends CrudRepository<EmailAuth, String> {

}
