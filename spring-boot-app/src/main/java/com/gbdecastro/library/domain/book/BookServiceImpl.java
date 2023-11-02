package com.gbdecastro.library.domain.book;

import com.gbdecastro.library.application.rest.controller.author.AuthorMapper;
import com.gbdecastro.library.application.rest.controller.book.BookMapper;
import com.gbdecastro.library.application.rest.controller.book.BookRequest;
import com.gbdecastro.library.application.rest.controller.subject.SubjectMapper;
import com.gbdecastro.library.domain.author.AuthorService;
import com.gbdecastro.library.domain.shared.DomainException;
import com.gbdecastro.library.domain.shared.annotation.BaseService;
import com.gbdecastro.library.domain.shared.message.MessageContext;
import com.gbdecastro.library.domain.shared.utils.StringUtils;
import com.gbdecastro.library.domain.subject.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static com.gbdecastro.library.domain.shared.utils.StringUtils.COMMA;

@BaseService
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository repository;

    @Autowired
    private BookMapper mapper;

    @Autowired
    private AuthorMapper authorMapper;

    @Autowired
    private SubjectMapper subjectMapper;

    @Autowired
    private MessageContext messageContext;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private SubjectService subjectService;

    @Override
    public List<Book> findAll() {
        return this.repository.findAll();
    }

    @Override
    public Book findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(messageContext.getMessage("book_not_found")));
    }

    @Override
    @Transactional
    public Book create(BookRequest bookRequest) {

        Book book = Book.builder().edition(bookRequest.getEdition()).publicationYear(bookRequest.getPublicationYear()).price(bookRequest.getPrice())
            .title(bookRequest.getTitle()).build();

        this.validate(book);

        this.repository.save(book);

        book.setAuthors(authorService.findAllOrCreate(authorMapper.responseToAuthorList(bookRequest.getAuthors())));
        book.setSubjects(subjectService.findAllOrCreate(subjectMapper.responseToSubjectList(bookRequest.getSubjects())));

        return this.repository.save(book);
    }

    @Override
    @Transactional
    public Book update(Long id, BookRequest bookRequest) {

        Book book = findById(id);
        book.setAuthors(new HashSet<>());
        book.setSubjects(new HashSet<>());
        repository.save(book);


        book.setAuthors(authorService.findAllOrCreate(authorMapper.responseToAuthorList(bookRequest.getAuthors())));
        book.setSubjects(subjectService.findAllOrCreate(subjectMapper.responseToSubjectList(bookRequest.getSubjects())));

        book.setEdition(bookRequest.getEdition());
        book.setTitle(bookRequest.getTitle());
        book.setPublicationYear(bookRequest.getPublicationYear());
        book.setPrice(bookRequest.getPrice());

        this.validate(book);


        return this.repository.saveAndFlush(book);

    }

    @Override
    public void delete(Long id) {
        Book book = findById(id);
        this.repository.delete(book);
    }

    private void validate(Book book) {
        List<String> errors = new ArrayList<>();

        if (StringUtils.isEmpty(book.getTitle())) {
            errors.add(messageContext.getMessage("title_required"));
        }

        if (StringUtils.isGreaterThan(book.getTitle(), 40)) {
            errors.add(messageContext.getMessage("title_max_length"));
        }

        if (StringUtils.isEmpty(book.getPublicationYear())) {
            errors.add(messageContext.getMessage("publication_year_required"));
        }

        if (StringUtils.isGreaterThan(book.getPublicationYear(), 4)) {
            errors.add(messageContext.getMessage("publication_year_max_length"));
        }

        if (book.getPrice() <= 0) {
            errors.add(messageContext.getMessage("price_greater_than_0"));
        }

        if (!errors.isEmpty()) {
            throw new DomainException(HttpStatus.BAD_REQUEST.value(), String.join(COMMA, errors));
        }
    }
}
