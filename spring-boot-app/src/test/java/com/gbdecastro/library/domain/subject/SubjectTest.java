package com.gbdecastro.library.domain.subject;

import com.gbdecastro.library.domain.shared.BaseDomainTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static com.gbdecastro.library.domain.book.BookConstant.BOOK_EDITION;
import static com.gbdecastro.library.domain.book.BookConstant.BOOK_ID;
import static com.gbdecastro.library.domain.book.BookConstant.BOOK_PUB_YEAR;
import static com.gbdecastro.library.domain.book.BookConstant.BOOK_TITLE;
import static com.gbdecastro.library.domain.subject.SubjectConstant.SUBJECT_DESCRIPTION;
import static com.gbdecastro.library.domain.subject.SubjectConstant.SUBJECT_ID;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
public class SubjectTest extends BaseDomainTest {


    @BeforeEach
    public void setUp() {
        givenSubject(SUBJECT_ID, SUBJECT_DESCRIPTION);
        subjectTest.setBooks(new HashSet<>());
    }

    @Test
    @DisplayName("Add a book to the subject's collection")
    void addBook_shouldAddBook() {
        givenBook(BOOK_ID, BOOK_TITLE, BOOK_EDITION, BOOK_PUB_YEAR);
        givenASubjectToBook(bookTest, subjectTest);

        assertTrue(subjectTest.getBooks().contains(bookTest));
        assertTrue(bookTest.getSubjects().contains(subjectTest));
    }

    @Test
    @DisplayName("Remove a book from the subject's collection")
    void removeBook_shouldRemoveBook() {
        givenBook(BOOK_ID, BOOK_TITLE, BOOK_EDITION, BOOK_PUB_YEAR);
        givenASubjectToBook(bookTest, subjectTest);

        subjectTest.removeBook(bookTest);

        assertFalse(subjectTest.getBooks().contains(bookTest));
        assertFalse(bookTest.getSubjects().contains(subjectTest));
    }

}
