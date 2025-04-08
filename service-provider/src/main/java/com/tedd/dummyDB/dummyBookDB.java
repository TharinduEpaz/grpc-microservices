package com.tedd.dummyDB;

import com.tedd.Author;
import com.tedd.Book;

import java.util.ArrayList;
import java.util.List;

public class dummyBookDB {
    public static List<Author> getAuthorsFromTempDb() {
        return new ArrayList<Author>() {
            {
                add(Author.newBuilder().setAuthorID(1).setBookID(1).setFirstName("Charles").setLastName("Dickens").setGender("male").build());
                add(Author.newBuilder().setAuthorID(2).setFirstName("William").setLastName("Shakespeare").setGender("male").build());
                add(Author.newBuilder().setAuthorID(3).setFirstName("JK").setLastName("Rowling").setGender("female").build());
                add(Author.newBuilder().setAuthorID(4).setFirstName("Virginia").setLastName("Woolf").setGender("female").build());
            }
        };
    }

    public static List<Book> getBooksFromTempDb() {
        return new ArrayList<Book>() {
            {
                add(Book.newBuilder().setBookID(1).setAuthorID(1).setTitle("Oliver Twist").setPrice(123.3f).setPages(100).build());
                add(Book.newBuilder().setBookID(2).setAuthorID(1).setTitle("A Christmas Carol").setPrice(223.3f).setPages(150).build());
                add(Book.newBuilder().setBookID(3).setAuthorID(2).setTitle("Hamlet").setPrice(723.3f).setPages(250).build());
                add(Book.newBuilder().setBookID(4).setAuthorID(3).setTitle("Harry Potter").setPrice(423.3f).setPages(350).build());
                add(Book.newBuilder().setBookID(5).setAuthorID(3).setTitle("The Casual Vacancy").setPrice(523.3f).setPages(450).build());
                add(Book.newBuilder().setBookID(6).setAuthorID(4).setTitle("Mrs. Dalloway").setPrice(623.3f).setPages(550).build());
            }
        };
    }


}
