package org.springframework.data.demo.repository;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.demo.domain.Author;
import org.springframework.data.demo.domain.Book;
import org.springframework.data.demo.domain.QBook;
import org.springframework.stereotype.Repository;

@Repository
public class MongoBookShelf implements BookShelf {

	@Autowired
	AuthorRepository authorRepository;

	@Autowired
	BookRepository bookRepository;

	@Override
	public void add(Book book) {
		save(book);
	}
	
	@Override
	public void save(Book book) {
		lookUpAuthor(book);
		bookRepository.save(book);
	}
	
	@Override
	public Book find(String isbn) {
		return bookRepository.findOne(isbn);
	}
	
	@Override
	public void remove(String isbn) {
		bookRepository.delete(isbn);
	}
	
	@Override
	public List<Book> findAll() {
		 return bookRepository.findAll();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Book> findByCategoriesOrYear(Set<String> categories, String year) {
		Date startDate = null;
		if (year != null && year.length() == 4) {
			DateFormat formatter = new SimpleDateFormat("yyyy-dd-MM");
			try {
				startDate = formatter.parse(year + "-01-01");
			} catch (ParseException e) {}
		}
		
		QBook book = new QBook("book");
		if (startDate != null) {
			if (categories != null && categories.size() > 0) {
				return bookRepository.findAll(
						book.published.after(startDate).and(book.categories.in(categories)));
			}
			else {
				return bookRepository.findAll(book.published.after(startDate));
			}
		}
		else {
			if (categories != null && categories.size() > 0) {
				return bookRepository.findAll(book.categories.in(categories));
			}
			else {
				return findAll();
			}
		}
	}

	private void lookUpAuthor(Book book) {
		Author existing = authorRepository.findByName(book.getAuthor().getName());
		if (existing != null) {
			book.setAuthor(existing);
		}
		else {
			authorRepository.save(book.getAuthor());
		}
	}

}
