package org.springframework.data.demo.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.demo.domain.Author;
import org.springframework.data.demo.domain.Book;
import org.springframework.data.mongodb.core.CollectionCallback;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoException;

@Component
public class DbHelper {

	@Autowired
	MongoTemplate mongoTemplate;

	public String getDump(Class<?> entityClass) {
		final StringBuilder mongoData = new StringBuilder();
		mongoTemplate.execute(entityClass, 
			new CollectionCallback<String>() {
				public String doInCollection(DBCollection collection) throws MongoException, DataAccessException {
					for (DBObject dbo : collection.find()) {
						mongoData.append(dbo.toString());
						mongoData.append(" ");
					}
					return null;
				}
			});
		String ret = "";
		if (mongoData.length() > 0) {
			ret = mongoTemplate.getCollectionName(entityClass) + " collection: " + mongoData.toString();
		}
		return ret;
	}
	
	public void clear() {
		if (mongoTemplate.collectionExists(Book.class)) {
			mongoTemplate.dropCollection(Book.class);
		}
		if (mongoTemplate.collectionExists(Author.class)) {
			mongoTemplate.dropCollection(Author.class);
		}
	}
}
