package com.intellisense.ebookstorev1.store.controller;

import com.cloudinary.utils.ObjectUtils;
import com.intellisense.ebookstorev1.store.model.Book;
import com.intellisense.ebookstorev1.store.service.BookService;
import com.intellisense.ebookstorev1.store.service.FileUploader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/admin/book")
public class BookController {

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BookService bookService;

    @Autowired
    @Qualifier("cloudinary")
    FileUploader uploaderSvc;


    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addBook(Model model) {
        Book book = new Book();
        model.addAttribute("book", book);
        return "admin/addBook";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addBookPost(@ModelAttribute("book") Book book, HttpServletRequest request) {

        byte[] bookImage = book.getBookImage().getBytes();

        try {

            //Clo..
            Map res = uploaderSvc.upload(bookImage, book.getBookImage().toLowerCase());
            String disposableUrl = (String) res.get("url");
            String publicId = (String) res.get("public_id");
            String height = (String) res.get("height");
            String width = (String) res.get("width");
            String type = (String) res.get("format");
            String size = (String) res.get("bytes"); //byte
            //Clo.. end

            book.setBookImage(disposableUrl);
            book.setPublicId(publicId);
            bookService.save(book);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:bookList";
    }

    @RequestMapping("/bookInfo")
    public String bookInfo(@RequestParam("id") Long id, Model model) {
        Book book = bookService.findOne(id);
        model.addAttribute("book", book);

        return "admin/bookInfo";
    }

    @RequestMapping("/updateBook")
    public String updateBook(@RequestParam("id") Long id, Model model) {
        Book book = bookService.findOne(id);
        model.addAttribute("book", book);

        return "admin/updateBook";
    }

    @RequestMapping(value="/updateBook", method=RequestMethod.POST)
    public String updateBookPost(@ModelAttribute("book") Book book, HttpServletRequest request) {

        byte[] raw = book.getBookImage().getBytes();

        if(!book.getBookImage().isEmpty()) {
            try {

                //Delete
                String pk_bk = bookService.findOne(book.getId()).getPublicId();
                uploaderSvc.destroy(pk_bk);

                //Save
                Map res = uploaderSvc.upload(raw, book.getBookImage().toLowerCase());
                String disposableUrl = (String) res.get("url");
                String publicId = (String) res.get("public_id");

                book.setBookImage(disposableUrl);
                book.setPublicId(publicId);
                bookService.save(book);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        bookService.save(book);

        return "redirect:/admin/book/bookInfo?id="+book.getId();
    }

    @RequestMapping("/bookList")
    public String bookList(Model model) {
        List<Book> bookList = bookService.findAll();
        model.addAttribute("bookList", bookList);

        return "admin/bookList";
    }

    @RequestMapping(value="/remove", method=RequestMethod.POST)
    public String remove(
            @ModelAttribute("id") String id, Model model
    ) {
        bookService.removeOne(Long.parseLong(id.substring(8)));
        List<Book> bookList = bookService.findAll();
        model.addAttribute("bookList", bookList);

        return "redirect:/admin/book/bookList";
    }
}
