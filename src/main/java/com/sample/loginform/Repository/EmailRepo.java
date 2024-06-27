package com.sample.loginform.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sample.loginform.Entity.Email;
@Repository
public interface EmailRepo extends JpaRepository<Email, Long>{

}
