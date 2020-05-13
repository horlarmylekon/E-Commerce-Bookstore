package com.intellisense.ebookstorev1.store.repository;

import com.intellisense.ebookstorev1.store.model.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {

    List<Book> findByCategory(String category);

    List<Book> findByTitleContaining(String title);
}
