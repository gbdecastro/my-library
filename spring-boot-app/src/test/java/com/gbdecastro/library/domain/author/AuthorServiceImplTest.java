package com.gbdecastro.library.domain.author;

import com.gbdecastro.library.application.rest.controller.author.AuthorMapper;
import com.gbdecastro.library.domain.shared.BaseDomainTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.gbdecastro.library.domain.book.BookConstant.BOOK_EDITION;
import static com.gbdecastro.library.domain.book.BookConstant.BOOK_ID;
import static com.gbdecastro.library.domain.book.BookConstant.BOOK_PUB_YEAR;
import static com.gbdecastro.library.domain.book.BookConstant.BOOK_TITLE;
import static com.gbdecastro.library.domain.shared.AuthorConstant.AUTHOR_ID;
import static com.gbdecastro.library.domain.shared.AuthorConstant.AUTHOR_NAME;
import static com.gbdecastro.library.domain.shared.AuthorConstant.AUTHOR_NAME_LARGE;
import static com.gbdecastro.library.domain.shared.AuthorConstant.AUTHOR_NAME_OTHER;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceImplTest extends BaseDomainTest {

    // region Data
    private final List<Author> authorListTest = new ArrayList<>();
    @InjectMocks
    private AuthorServiceImpl service;
    @Mock
    private AuthorRepository repository;
    @Mock
    private AuthorMapper mapper;
    private List<Author> authorListResult;
    private Author authorResult;

    // endregion

    // region Tests

    @Test
    @DisplayName("List all authors")
    void findAll_shouldReturnAnAuthorList() {
        givenAuthor(AUTHOR_ID, AUTHOR_NAME);
        givenAuthorList(authorTest);
        whenCalling_repositoryFindAll();
        whenCalled_findAll();
        thenShouldReturnAnAuthorList();
    }

    @Test
    @DisplayName("List all authors by ids")
    void findAllByIds_shouldReturnAnAuthorList() {
        givenAuthor(AUTHOR_ID, AUTHOR_NAME);
        givenAuthorList(authorTest);
        whenCalling_repositoryFindAllById(authorListTest.stream().map(Author::getId).collect(Collectors.toSet()));
        whenCalled_findAllByIds(authorListTest.stream().map(Author::getId).collect(Collectors.toSet()));
        thenShouldReturnAnAuthorListByIds(authorListTest.stream().map(Author::getId).collect(Collectors.toSet()));
    }

    @Test
    @DisplayName("List a author by id")
    void findById_shouldReturnAnAuthor() {
        givenAuthor(AUTHOR_ID, AUTHOR_NAME);
        whenCalling_repositoryFindById(authorTest.getId(), Optional.of(authorTest));
        whenCalled_findById(authorTest.getId());
        thenShouldReturnAnAuthor(authorTest.getId());
    }

    @Test
    @DisplayName("Finding a author by id but an exception is expected: author not found")
    void findById_throwEntityNotFoundException() {
        givenAuthor(AUTHOR_ID, AUTHOR_NAME);
        whenCalling_repositoryFindById(authorTest.getId(), Optional.empty());
        whenCalling_messageContext(AUTHOR_NOT_FOUND);
        thenThrowEntityNotFoundException(() -> service.findById(AUTHOR_ID), AUTHOR_NOT_FOUND);
    }

    @Test
    @DisplayName("Deleting an author with linked books should throw DomainException")
    void delete_throwDomainExceptionWhenBooksLinked() {
        // Given
        givenAuthor(AUTHOR_ID, AUTHOR_NAME);
        givenBook(BOOK_ID, BOOK_TITLE, BOOK_EDITION, BOOK_PUB_YEAR);
        givenAnAuthorToBook(bookTest, authorTest);

        // When
        whenCalling_repositoryFindById(AUTHOR_ID, Optional.of(authorTest));
        whenCalling_messageContext("author_already_linked_a_book");

        // Then

        thenThrowDomainException(() -> service.delete(authorTest.getId()), HttpStatus.BAD_REQUEST.value(), "author_already_linked_a_book");
        verify(repository, never()).delete(any());
    }

    @Test
    @DisplayName("Deleting an author with no linked books should succeed")
    void delete_shouldSucceedWhenNoBooksLinked() {
        // Given
        givenAuthor(AUTHOR_ID, AUTHOR_NAME);

        // When
        whenCalling_repositoryFindById(AUTHOR_ID, Optional.of(authorTest));
        whenCalled_deleteById(AUTHOR_ID);

        // Then
        verify(repository, times(1)).delete(authorTest);
    }

    @Test
    @DisplayName("Deleting a non-existent author should throw EntityNotFoundException")
    void delete_throwEntityNotFoundExceptionForNonExistentAuthor() {
        // Given
        givenAuthor(AUTHOR_ID, AUTHOR_NAME);

        // When
        whenCalling_repositoryFindById(authorTest.getId(), Optional.empty());
        whenCalling_messageContext(AUTHOR_NOT_FOUND);

        // Then
        thenThrowEntityNotFoundException(() -> service.delete(AUTHOR_ID), AUTHOR_NOT_FOUND);

        // Verify
        verify(repository, never()).delete(any());
    }

    @Test
    @DisplayName("Creating an author with valid data should succeed")
    void create_shouldSucceedWithValidData() {
        givenAuthor(AUTHOR_ID, AUTHOR_NAME);
        whenCalling_repositorySave(authorTest);
        whenCalled_create(authorTest);
        assertNotNull(authorResult);
        assertEquals(AUTHOR_NAME, authorResult.getName());
    }

    @Test
    @DisplayName("Creating an author with missing name should throw DomainException")
    void create_throwDomainExceptionWhenNameMissing() {
        // Given
        givenAuthor(AUTHOR_ID, null);

        whenCalling_messageContext("name_required");

        // Then
        thenThrowDomainException(() -> service.create(authorTest), HttpStatus.BAD_REQUEST.value(), "name_required");

        // Verify
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Creating an author with name exceeding 40 characters should throw DomainException")
    void create_throwDomainExceptionWhenNameTooLong() {
        // Given
        givenAuthor(AUTHOR_ID, AUTHOR_NAME_LARGE);

        whenCalling_messageContext("name_max_length");

        // Then
        thenThrowDomainException(() -> service.create(authorTest), HttpStatus.BAD_REQUEST.value(), "name_max_length");

        // Verify
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Updating an author with valid data should succeed")
    void update_shouldSucceedWithValidData() {
        // Given
        givenAuthor(AUTHOR_ID, AUTHOR_NAME);
        givenAuthor2(null, AUTHOR_NAME_OTHER);

        // When
        whenCalling_repositoryFindById(AUTHOR_ID, Optional.of(authorTest));
        whenCalling_mapperUpdate(authorTest, authorTest2);
        whenCalling_saveAndFlush(authorTest2);
        whenCalled_update(AUTHOR_ID, authorTest2);

        // Then
        assertNotNull(authorResult);
        assertEquals(AUTHOR_NAME_OTHER, authorResult.getName());
    }

    // endregion

    // region given(s)
    private void givenAuthorList(Author author) {
        this.authorListTest.add(author);
    }

    // endregion

    // region when calling(s)
    private void whenCalling_repositoryFindAll() {
        when(repository.findAll()).thenReturn(authorListTest);
    }

    private void whenCalling_repositoryFindAllById(Set<Long> ids) {
        when(repository.findAllById(ids)).thenReturn(authorListTest);
    }

    private void whenCalling_repositoryFindById(Long id, Optional<Author> author) {
        when(repository.findById(id)).thenReturn(author);
    }

    private void whenCalling_repositorySave(Author author) {
        when(repository.save(author)).thenReturn(author);
    }

    private void whenCalling_mapperUpdate(Author author, Author toUpdate) {
        when(mapper.update(author, toUpdate)).thenReturn(toUpdate);
    }

    private void whenCalling_saveAndFlush(Author author) {
        when(repository.saveAndFlush(author)).thenReturn(author);
    }

    // endregion

    // region when called(s)
    private void whenCalled_findAll() {
        authorListResult = service.findAll();
    }

    private void whenCalled_findById(Long id) {
        authorResult = service.findById(id);
    }

    private void whenCalled_findAllByIds(Set<Long> ids) {
        authorListResult = service.findAllByIds(ids);
    }

    private void whenCalled_deleteById(Long authorId) {
        service.delete(authorId);
    }

    private void whenCalled_create(Author author) {
        authorResult = service.create(author);
    }

    private void whenCalled_update(Long id, Author author) {
        authorResult = service.update(id, author);
    }

    // endregion

    // region Then
    private void thenShouldReturnAnAuthorList() {
        assertAll(() -> verify(repository, times(1)).findAll(), () -> assertEquals(authorListTest, authorListResult));
    }

    private void thenShouldReturnAnAuthorListByIds(Set<Long> ids) {
        assertAll(() -> verify(repository, times(1)).findAllById(ids), () -> assertEquals(authorListTest, authorListResult));
    }

    private void thenShouldReturnAnAuthor(Long id) {
        assertAll(() -> verify(repository, times(1)).findById(id), () -> assertEquals(authorTest, authorResult));
    }
    // endregion

}
