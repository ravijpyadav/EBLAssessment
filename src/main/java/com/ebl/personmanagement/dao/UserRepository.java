package com.ebl.personmanagement.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ebl.personmanagement.dao.model.ApplicationUser;

@Repository
public interface UserRepository extends JpaRepository<ApplicationUser, Long> {

	ApplicationUser findByEmail(String email);
}
