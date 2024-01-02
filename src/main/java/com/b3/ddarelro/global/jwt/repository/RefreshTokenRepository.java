package com.b3.ddarelro.global.jwt.repository;

import com.b3.ddarelro.global.jwt.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {

}
