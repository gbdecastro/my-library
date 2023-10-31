package com.gbdecastro.library.domain.author;

import com.gbdecastro.library.application.rest.controller.author.AuthorMapper;
import com.gbdecastro.library.domain.shared.DomainException;
import com.gbdecastro.library.domain.shared.annotation.BaseService;
import com.gbdecastro.library.domain.shared.message.MessageContext;
import com.gbdecastro.library.domain.shared.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.gbdecastro.library.domain.shared.utils.StringUtils.COMMA;

@BaseService
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    private AuthorRepository repository;
    @Autowired
    private AuthorMapper mapper;

    @Autowired
    private MessageContext messageContext;


    @Override
    public Author create(Author author) {

        this.validate(author);

        return this.repository.save(author);
    }

    @Override
    public List<Author> findAll() {
        return this.repository.findAll();
    }

    @Override
    public List<Author> findAllByIds(Set<Long> ids) {
        return this.repository.findAllById(ids);
    }

    @Override
    public Author findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(messageContext.getMessage("author_not_found")));
    }

    @Override
    public Author update(Long id, Author toUpdate) {
        Author author = findById(id);

        author = mapper.update(author, toUpdate);

        this.validate(author);

        repository.saveAndFlush(author);

        return author;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Author author = findById(id);

        if(!author.getBooks().isEmpty()){
            throw new DomainException(HttpStatus.BAD_REQUEST.value(),messageContext.getMessage("author_already_linked_a_book"));
        }

        repository.delete(author);
    }

    private void validate(Author author) {
        List<String> errors = new ArrayList<>();

        if (StringUtils.isEmpty(author.getName())) {
            errors.add(messageContext.getMessage("name_required"));
        }

        if (StringUtils.isGreaterThan(author.getName(), 40)) {
            errors.add(messageContext.getMessage("name_max_length"));
        }

        if (!errors.isEmpty()) {
            throw new DomainException(HttpStatus.BAD_REQUEST.value(), String.join(COMMA, errors));
        }
    }
}
