package com.tedd.dummyDB;

import com.tedd.Publisher;
import com.tedd.Book;
import com.tedd.BookGenre;

import java.util.ArrayList;
import java.util.List;

public class dummyBookstoreDB {
    public static List<Publisher> getPublishersFromTempDb() {
        return new ArrayList<Publisher>() {
            {
                add(Publisher.newBuilder().setPublisherID(1).setPublisherName("Penguin Random House").setCountry("USA").setRating("A+").build());
                add(Publisher.newBuilder().setPublisherID(2).setPublisherName("HarperCollins").setCountry("USA").setRating("A").build());
                add(Publisher.newBuilder().setPublisherID(3).setPublisherName("Simon & Schuster").setCountry("USA").setRating("A").build());
                add(Publisher.newBuilder().setPublisherID(4).setPublisherName("Macmillan Publishers").setCountry("UK").setRating("A").build());
                add(Publisher.newBuilder().setPublisherID(5).setPublisherName("Hachette Livre").setCountry("France").setRating("A").build());
            }
        };
    }

    public static List<Book> getBooksFromTempDb() {
        return new ArrayList<Book>() {
            {
                add(Book.newBuilder().setBookID(1).setPublisherID(1).setGenre(BookGenre.FICTION).setIsbn("978-0-14-028333-4").setBookName("The Great Gatsby").setPrice(12.99f).setStock(500).build());
                add(Book.newBuilder().setBookID(2).setPublisherID(1).setGenre(BookGenre.FICTION).setIsbn("978-0-14-028334-1").setBookName("To Kill a Mockingbird").setPrice(14.99f).setStock(300).build());
                add(Book.newBuilder().setBookID(3).setPublisherID(2).setGenre(BookGenre.SCI_FI).setIsbn("978-0-06-085052-4").setBookName("Dune").setPrice(16.99f).setStock(1000).build());
                add(Book.newBuilder().setBookID(4).setPublisherID(3).setGenre(BookGenre.MYSTERY).setIsbn("978-1-5011-1234-5").setBookName("The Girl with the Dragon Tattoo").setPrice(15.99f).setStock(200).build());
                add(Book.newBuilder().setBookID(5).setPublisherID(3).setGenre(BookGenre.FICTION).setIsbn("978-1-5011-1235-2").setBookName("The Catcher in the Rye").setPrice(13.99f).setStock(150).build());
                add(Book.newBuilder().setBookID(6).setPublisherID(4).setGenre(BookGenre.FANTASY).setIsbn("978-0-330-25864-1").setBookName("The Hobbit").setPrice(18.99f).setStock(800).build());
                add(Book.newBuilder().setBookID(7).setPublisherID(5).setGenre(BookGenre.NON_FICTION).setIsbn("978-0-316-76948-0").setBookName("Sapiens: A Brief History of Humankind").setPrice(19.99f).setStock(400).build());
                add(Book.newBuilder().setBookID(8).setPublisherID(5).setGenre(BookGenre.ROMANCE).setIsbn("978-0-316-76949-7").setBookName("Pride and Prejudice").setPrice(11.99f).setStock(250).build());
            }
        };
    }
}

