package com.makersharks.assessment.security.repository;

import com.makersharks.assessment.security.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token,Long> {

    Token findByToken(String token);
}
