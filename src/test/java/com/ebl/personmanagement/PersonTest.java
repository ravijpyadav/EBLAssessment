package com.ebl.personmanagement;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.ebl.personmanagement.dao.model.Person;

/**
 * Unit test for simple App.
 */
public class PersonTest {
	
	@Test
	public void shouldCreatePerson() {
		Person person = new Person();
		assertNotNull(person);
	}

}
