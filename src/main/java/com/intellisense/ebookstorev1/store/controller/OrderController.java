package com.intellisense.ebookstorev1.store.controller;

import com.intellisense.ebookstorev1.store.model.Book;
import com.intellisense.ebookstorev1.store.model.User;
import com.intellisense.ebookstorev1.store.service.BookService;
import com.intellisense.ebookstorev1.store.service.UserService;
import com.intellisense.ebookstorev1.ultility.MailConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Locale;

@Controller
@RequestMapping("/book/order")
public class OrderController {

    @Autowired
    private BookService bookService;
    @Autowired
    private UserService userService;
    @Autowired
    private MailConstructor mailConstructor;
    @Autowired
    private JavaMailSender mailSender;

    @RequestMapping("/new")
    public String showOrderPage(@RequestParam("id") Long id, Model model) {
        Book book = bookService.findOne(id);
        model.addAttribute("book", book);

        return "bookstore/order";
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String sendOrderRequest(
            @ModelAttribute("title") String title,
            @ModelAttribute("author") String author,
            @ModelAttribute("publisher") String publisher,
            @ModelAttribute("email") String userEmail,
            @ModelAttribute("number") String phoneNumber,
            @ModelAttribute("quatity") String orderQty,
            BindingResult result, ModelMap model
    ) {

        model.addAttribute("title", title);
        model.addAttribute("author", author);
        model.addAttribute("publisher", publisher);
        model.addAttribute("email", userEmail);
        model.addAttribute("phoneNumber", phoneNumber);
        model.addAttribute("orderQty", orderQty);


        if (result.hasErrors()) {
            return "error";
        }

        User user = userService.findByEmail(userEmail);

        String newOrder = "USER ORDER==> \n" +
                                "Title: "+ title + "\n" +
                                "Author: " +author + "\n" +
                                "Publisher: "+publisher +"\n" +
                                "Email: "+userEmail + "\n" +
                               "Phone Number: "+phoneNumber +"\n" +
                               "Quatity: "+orderQty +"\n";
        System.out.println(newOrder);

        if(user == null) {
            model.addAttribute("emailNotExist", true);
            return "bookstore/order";
        }

       String order = newOrder;


        mailSender.send(mailConstructor.constructOrderEmail(user, order, Locale.ENGLISH));
        mailSender.send(mailConstructor.constructOrderConfirmationEmail(user, order, Locale.ENGLISH));



        return "bookstore/success";
    }

}
