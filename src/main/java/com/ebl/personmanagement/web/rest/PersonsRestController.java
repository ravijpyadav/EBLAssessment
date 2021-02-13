package com.ebl.personmanagement.web.rest;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ebl.personmanagement.dao.PersonRepository;
import com.ebl.personmanagement.dao.model.Person;
import com.ebl.personmanagement.web.exception.AlreadyExists;
import com.ebl.personmanagement.web.exception.InvalidInput;
import com.ebl.personmanagement.web.exception.ObjectNotFound;

@RestController
public class PersonsRestController {
	private static final Logger log = LogManager.getLogger(PersonsRestController.class);
	@Autowired
	private PersonRepository personRepository;

	@PostMapping("/persons")
	@ResponseStatus(value = HttpStatus.CREATED)
	public Person save(@RequestBody Person person) throws AlreadyExists, InvalidInput {
		
		if(isInvalid(person)){
			log.error("Required fields are missing.");
			throw new InvalidInput("Required fields are missing.");
		}
		
		personRepository.save(person);
		return person;
	}

	@PutMapping("/persons/{id}")
	public Person update(@RequestBody Person personToUpdate, @PathVariable("id") Long id) throws ObjectNotFound {
		Optional<Person> findById = personRepository.findById(id);
		if(findById.isPresent()){
			Person person2 = findById.get();
			person2.setFirstName(personToUpdate.getFirstName());
			person2.setFirstName(personToUpdate.getLastName());
			person2.setAge(personToUpdate.getAge());
			person2.setFavouriteColour(personToUpdate.getFavouriteColour());
			personRepository.save(person2);
			return person2;
		}else{
			log.error("Person with id : {} does not exist", id);
			throw new ObjectNotFound("Person not found with given id: "+ id);
		}
		
	}
	
	@GetMapping("/persons")
	public List<Person> getPage(@RequestParam("page") Integer pageNum) throws InvalidInput {
		
		if(pageNum == null || pageNum < 0){
			log.error("Page number is not valid, pageNum: {}",pageNum);
			throw new InvalidInput("Page number is not valid, pageNum: "+ pageNum);
		}
		Pageable page = PageRequest.of(pageNum-1, 10);
		Page<Person> persons = personRepository.findAll(page);
		return persons.getContent();
	}

	@DeleteMapping("/persons/{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") Long id) throws AlreadyExists, ObjectNotFound {
		Optional<Person> findById = personRepository.findById(id);
		if(findById.isPresent()){
			personRepository.deleteById(id);
		}else{
			log.error("Person with id : {} does not exist", id);
			throw new ObjectNotFound("Person not found with given id: "+id);
		}
		
	}
	
	private boolean isInvalid(Person person) {
		return (person.getFirstName() == null || person.getLastName() == null || person.getAge() == null || person.getFavouriteColour() == null);
	}
}
