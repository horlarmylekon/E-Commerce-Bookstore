package com.intellisense.ebookstorev1.store.service;

import com.intellisense.ebookstorev1.store.model.Book;

import java.util.List;


public interface BookService {

    List<Book> findAll();

    Book findOne(Long id);

    List<Book> findByCategory(String category);

    List<Book> blurrySearch(String title);

    Book save(Book book);

    void removeOne(Long id);

//    boolean isFileValid(String fileName);
}
