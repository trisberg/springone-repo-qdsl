package org.springframework.data.demo.repository;

import java.util.List;

import org.springframework.data.demo.domain.Book;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.Repository;

import com.mysema.query.types.Predicate;

public interface BookRepository extends Repository<Book, String>, QueryDslPredicateExecutor<Book> {
	
	Book save(Book book);
	
	Book findOne(String isbn);
	
	void delete(String isbn);
	
	List<Book> findAll(); 

	List<Book> findAll(Predicate predicate); 

}
