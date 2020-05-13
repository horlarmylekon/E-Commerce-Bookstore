package com.intellisense.ebookstorev1.store.service.implem;

import com.intellisense.ebookstorev1.store.model.Book;
import com.intellisense.ebookstorev1.store.repository.BookRepository;
import com.intellisense.ebookstorev1.store.service.BookService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookServiceImplementation implements BookService {

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BookRepository bookRepository;

    public List<Book> findAll() {
        List<Book> bookList = (List<Book>) bookRepository.findAll();
        List<Book> activeBookList = new ArrayList<>();

        for(Book book : bookList) {
            if(book.isActive()) {
                activeBookList.add(book);
            }
        }

        return activeBookList;
    }

    public Book findOne(Long id) {
        return bookRepository.findById(id).get();
    }

    public List<Book> findByCategory(String category) {
        List<Book> bookList = bookRepository.findByCategory(category);

        List<Book> activeBookList = new ArrayList<>();

        for(Book book : bookList) {
            if(book.isActive()) {
                activeBookList.add(book);
            }
        }

        return activeBookList;
    }

    public List<Book> blurrySearch(String title) {
        List<Book> bookList = bookRepository.findByTitleContaining(title);
        List<Book> activeBookList = new ArrayList<>();

        for(Book book : bookList) {
            if(book.isActive()) {
                activeBookList.add(book);
            }
        }

        return activeBookList;
    }

    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public void removeOne(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public boolean isFileValid(String fileName) {
        if(fileName!=null && !StringUtils.isEmpty(fileName)){
            int lastIndexOfDot = fileName.lastIndexOf('.');
            if(lastIndexOfDot != -1) {
                String fileExtension = fileName.substring(lastIndexOfDot, fileName.length());
                logger.info("the file extension {}", fileExtension);
                if (StringUtils.equals(fileExtension, ".png") || StringUtils.equals(fileExtension, ".jpg") || StringUtils.equals(fileExtension, ".pdf")) {
                    return true;
                }
            }
        }
        return false;
    }
}
