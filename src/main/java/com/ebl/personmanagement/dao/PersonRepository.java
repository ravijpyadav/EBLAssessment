package com.ebl.personmanagement.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.ebl.personmanagement.dao.model.Person;

@Repository
public interface PersonRepository extends PagingAndSortingRepository<Person, Long> {

}
