package com.gbdecastro.library.domain.author;

import com.gbdecastro.library.domain.shared.BaseDomainTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static com.gbdecastro.library.domain.book.BookConstant.BOOK_EDITION;
import static com.gbdecastro.library.domain.book.BookConstant.BOOK_ID;
import static com.gbdecastro.library.domain.book.BookConstant.BOOK_PRICE;
import static com.gbdecastro.library.domain.book.BookConstant.BOOK_PUB_YEAR;
import static com.gbdecastro.library.domain.book.BookConstant.BOOK_TITLE;
import static com.gbdecastro.library.domain.shared.AuthorConstant.AUTHOR_ID;
import static com.gbdecastro.library.domain.shared.AuthorConstant.AUTHOR_NAME;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AuthorTest extends BaseDomainTest {


    @BeforeEach
    public void setUp() {
        givenAuthor(AUTHOR_ID, AUTHOR_NAME);
        authorTest.setBooks(new HashSet<>());
    }

    @Test
    @DisplayName("Add a book to the author's collection")
    void addBook_shouldAddBook() {
        givenBook(BOOK_ID, BOOK_TITLE, BOOK_EDITION, BOOK_PUB_YEAR, BOOK_PRICE);
        givenAnAuthorToBook(bookTest, authorTest);

        assertTrue(authorTest.getBooks().contains(bookTest));
        assertTrue(bookTest.getAuthors().contains(authorTest));
    }

    @Test
    @DisplayName("Remove a book from the author's collection")
    void removeBook_shouldRemoveBook() {
        givenBook(BOOK_ID, BOOK_TITLE, BOOK_EDITION, BOOK_PUB_YEAR, BOOK_PRICE);
        givenAnAuthorToBook(bookTest, authorTest);

        authorTest.removeBook(bookTest);

        assertFalse(authorTest.getBooks().contains(bookTest));
        assertFalse(bookTest.getAuthors().contains(authorTest));
    }

}
