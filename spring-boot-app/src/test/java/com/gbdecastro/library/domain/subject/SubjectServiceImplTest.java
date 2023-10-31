package com.gbdecastro.library.domain.subject;


import com.gbdecastro.library.application.rest.controller.subject.SubjectMapper;
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
import static com.gbdecastro.library.domain.subject.SubjectConstant.SUBJECT_DESCRIPTION;
import static com.gbdecastro.library.domain.subject.SubjectConstant.SUBJECT_DESCRIPTION_LARGE;
import static com.gbdecastro.library.domain.subject.SubjectConstant.SUBJECT_DESCRIPTION_OTHER;
import static com.gbdecastro.library.domain.subject.SubjectConstant.SUBJECT_ID;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SubjectServiceImplTest extends BaseDomainTest {

    // region Data
    private final List<Subject> subjectListTest = new ArrayList<>();
    @InjectMocks
    private SubjectServiceImpl service;
    @Mock
    private SubjectRepository repository;
    @Mock
    private SubjectMapper mapper;
    private List<Subject> subjectListResult;
    private Subject subjectResult;

    // endregion

    // region Tests

    @Test
    @DisplayName("List all subjects")
    void findAll_shouldReturnAnSubjectList() {
        givenSubject(SUBJECT_ID, SUBJECT_DESCRIPTION);
        givenSubjectList(subjectTest);
        whenCalling_repositoryFindAll();
        whenCalled_findAll();
        thenShouldReturnAnSubjectList();
    }

    @Test
    @DisplayName("List all subjects by ids")
    void findAllByIds_shouldReturnAnSubjectList() {
        givenSubject(SUBJECT_ID, SUBJECT_DESCRIPTION);
        givenSubjectList(subjectTest);
        whenCalling_repositoryFindAllById(subjectListTest.stream().map(Subject::getId).collect(Collectors.toSet()));
        whenCalled_findAllByIds(subjectListTest.stream().map(Subject::getId).collect(Collectors.toSet()));
        thenShouldReturnAnSubjectListByIds(subjectListTest.stream().map(Subject::getId).collect(Collectors.toSet()));
    }

    @Test
    @DisplayName("List a subject by id")
    void findById_shouldReturnAnSubject() {
        givenSubject(SUBJECT_ID, SUBJECT_DESCRIPTION);
        whenCalling_repositoryFindById(subjectTest.getId(), Optional.of(subjectTest));
        whenCalled_findById(subjectTest.getId());
        thenShouldReturnAnSubject(subjectTest.getId());
    }

    @Test
    @DisplayName("Finding a subject by id but an exception is expected: subject not found")
    void findById_throwEntityNotFoundException() {
        givenSubject(SUBJECT_ID, SUBJECT_DESCRIPTION);
        whenCalling_repositoryFindById(subjectTest.getId(), Optional.empty());
        whenCalling_messageContext(SUBJECT_NOT_FOUND);
        thenThrowEntityNotFoundException(() -> service.findById(SUBJECT_ID), SUBJECT_NOT_FOUND);
    }

    @Test
    @DisplayName("Deleting an subject with linked books should throw DomainException")
    void delete_throwDomainExceptionWhenBooksLinked() {
        // Given
        givenSubject(SUBJECT_ID, SUBJECT_DESCRIPTION);
        givenBook(BOOK_ID, BOOK_TITLE, BOOK_EDITION, BOOK_PUB_YEAR);
        givenASubjectToBook(bookTest, subjectTest);

        // When
        whenCalling_repositoryFindById(SUBJECT_ID, Optional.of(subjectTest));
        whenCalling_messageContext("subject_already_linked_a_book");

        // Then
        thenThrowDomainException(()-> service.delete(subjectTest.getId()), HttpStatus.BAD_REQUEST.value(), "subject_already_linked_a_book");
        verify(repository, never()).delete(any());
    }

    @Test
    @DisplayName("Deleting an subject with no linked books should succeed")
    void delete_shouldSucceedWhenNoBooksLinked() {
        // Given
        givenSubject(SUBJECT_ID, SUBJECT_DESCRIPTION);

        // When
        whenCalling_repositoryFindById(SUBJECT_ID, Optional.of(subjectTest));
        whenCalled_deleteById(SUBJECT_ID);

        // Then
        verify(repository, times(1)).delete(subjectTest);
    }

    @Test
    @DisplayName("Deleting a non-existent subject should throw EntityNotFoundException")
    void delete_throwEntityNotFoundExceptionForNonExistentSubject() {
        // Given
        givenSubject(SUBJECT_ID, SUBJECT_DESCRIPTION);

        // When
        whenCalling_repositoryFindById(subjectTest.getId(), Optional.empty());
        whenCalling_messageContext(SUBJECT_NOT_FOUND);

        // Then
        thenThrowEntityNotFoundException(() -> service.delete(SUBJECT_ID), SUBJECT_NOT_FOUND);

        // Verify
        verify(repository, never()).delete(any());
    }

    @Test
    @DisplayName("Creating an subject with valid data should succeed")
    void create_shouldSucceedWithValidData() {
        givenSubject(SUBJECT_ID, SUBJECT_DESCRIPTION);
        whenCalling_repositorySave(subjectTest);
        whenCalled_create(subjectTest);
        assertNotNull(subjectResult);
        assertEquals(SUBJECT_DESCRIPTION, subjectResult.getDescription());
    }

    @Test
    @DisplayName("Creating an subject with missing description should throw DomainException")
    void create_throwDomainExceptionWhenNameMissing() {
        // Given
        givenSubject(SUBJECT_ID, null);

        whenCalling_messageContext("description_required");

        // Then
        thenThrowDomainException(()-> service.create(subjectTest), HttpStatus.BAD_REQUEST.value(), "description_required");

        // Verify
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Creating an subject with description exceeding 40 characters should throw DomainException")
    void create_throwDomainExceptionWhenNameTooLong() {
        // Given
        givenSubject(SUBJECT_ID, SUBJECT_DESCRIPTION_LARGE);

        whenCalling_messageContext("description_max_length");

        // Then
        thenThrowDomainException(()-> service.create(subjectTest), HttpStatus.BAD_REQUEST.value(), "description_max_length");

        // Verify
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Updating an subject with valid data should succeed")
    void update_shouldSucceedWithValidData() {
        // Given
        givenSubject(SUBJECT_ID, SUBJECT_DESCRIPTION);
        givenSubject2(null, SUBJECT_DESCRIPTION_OTHER);

        // When
        whenCalling_repositoryFindById(SUBJECT_ID, Optional.of(subjectTest));
        whenCalling_mapperUpdate(subjectTest, subjectTest2);
        whenCalling_saveAndFlush(subjectTest2);
        whenCalled_update(SUBJECT_ID, subjectTest2);

        // Then
        assertNotNull(subjectResult);
        assertEquals(SUBJECT_DESCRIPTION_OTHER, subjectResult.getDescription());
    }

    // endregion

    // region given(s)
    private void givenSubjectList(Subject subject) {
        this.subjectListTest.add(subject);
    }

    // endregion

    // region when calling(s)
    private void whenCalling_repositoryFindAll() {
        when(repository.findAll()).thenReturn(subjectListTest);
    }

    private void whenCalling_repositoryFindAllById(Set<Long> ids) {
        when(repository.findAllById(ids)).thenReturn(subjectListTest);
    }

    private void whenCalling_repositoryFindById(Long id, Optional<Subject> subject) {
        when(repository.findById(id)).thenReturn(subject);
    }

    private void whenCalling_repositorySave(Subject subject) {
        when(repository.save(subject)).thenReturn(subject);
    }

    private void whenCalling_mapperUpdate(Subject subject, Subject toUpdate) {
        when(mapper.update(subject, toUpdate)).thenReturn(toUpdate);
    }

    private void whenCalling_saveAndFlush(Subject subject) {
        when(repository.saveAndFlush(subject)).thenReturn(subject);
    }

    // endregion

    // region when called(s)
    private void whenCalled_findAll() {
        subjectListResult = service.findAll();
    }

    private void whenCalled_findById(Long id) {
        subjectResult = service.findById(id);
    }

    private void whenCalled_findAllByIds(Set<Long> ids) {
        subjectListResult = service.findAllByIds(ids);
    }

    private void whenCalled_deleteById(Long subjectId) {
        service.delete(subjectId);
    }

    private void whenCalled_create(Subject subject) {
        subjectResult = service.create(subject);
    }
    private void whenCalled_update(Long id, Subject subject) {
        subjectResult = service.update(id, subject);
    }

    // endregion

    // region Then
    private void thenShouldReturnAnSubjectList() {
        assertAll(
                () -> verify(repository, times(1)).findAll(),
                () -> assertEquals(subjectListTest, subjectListResult)
        );
    }

    private void thenShouldReturnAnSubjectListByIds(Set<Long> ids) {
        assertAll(
                () -> verify(repository, times(1)).findAllById(ids),
                () -> assertEquals(subjectListTest, subjectListResult)
        );
    }

    private void thenShouldReturnAnSubject(Long id) {
        assertAll(
                () -> verify(repository, times(1)).findById(id),
                () -> assertEquals(subjectTest, subjectResult));
    }
    // endregion

}
