package org.springframework.data.demo.repository;

import org.springframework.data.demo.domain.Author;
import org.springframework.data.repository.Repository;

public interface AuthorRepository extends Repository<Author, String> {
	
	Author save(Author author);
	
	Author findByName(String name);
	
}
