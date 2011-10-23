package org.springframework.data.demo.repository;

import java.util.List;

import org.springframework.data.demo.domain.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.mysema.query.types.Predicate;

public interface BookRepository extends MongoRepository<Book, String>, QueryDslPredicateExecutor<Book> {
	
	Book save(Book book);
	
	Book findOne(String isbn);
	
	void delete(String isbn);
	
	List<Book> findAll(); 

	List<Book> findAll(Predicate predicate); 

}
