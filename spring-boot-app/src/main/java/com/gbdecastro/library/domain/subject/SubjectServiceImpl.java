package com.gbdecastro.library.domain.subject;

import com.gbdecastro.library.application.rest.controller.subject.SubjectMapper;
import com.gbdecastro.library.domain.shared.DomainException;
import com.gbdecastro.library.domain.shared.annotation.BaseService;
import com.gbdecastro.library.domain.shared.message.MessageContext;
import com.gbdecastro.library.domain.shared.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.gbdecastro.library.domain.shared.utils.StringUtils.COMMA;

@BaseService
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    private SubjectRepository repository;

    @Autowired
    private SubjectMapper mapper;

    @Autowired
    private MessageContext messageContext;

    @Override
    public List<Subject> findAll() {
        return this.repository.findAll();
    }

    @Override
    public Set<Subject> findAllOrCreate(Set<Subject> subjects) {
        List<Subject> entities = repository.findAllById(subjects.stream().map(Subject::getId).collect(Collectors.toSet()));

        Set<Long> entityIds = entities.stream().map(Subject::getId).collect(Collectors.toSet());

        Set<Subject> entitiesToCreate = subjects.stream().filter(entity -> !entityIds.contains(entity.getId())).collect(Collectors.toSet());

        if (!entitiesToCreate.isEmpty()) {
            repository.saveAll(entitiesToCreate);
            entities.addAll(entitiesToCreate);
        }

        return new HashSet<>(entities);
    }

    @Override
    public Subject findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(messageContext.getMessage("subject_not_found")));
    }

    @Override
    public Subject create(Subject subject) {
        this.validate(subject);

        return this.repository.save(subject);
    }

    @Override
    public Subject update(Long id, Subject subjectToUpdate) {
        Subject subject = findById(id);

        subject = mapper.update(subject, subjectToUpdate);

        this.validate(subject);

        repository.saveAndFlush(subject);

        return subject;
    }

    @Override
    public void delete(Long id) {
        Subject subject = findById(id);

        if (!subject.getBooks().isEmpty()) {
            throw new DomainException(HttpStatus.BAD_REQUEST.value(), messageContext.getMessage("subject_already_linked_a_book"));
        }

        repository.delete(subject);
    }

    private void validate(Subject subject) {
        List<String> errors = new ArrayList<>();

        if (StringUtils.isEmpty(subject.getDescription())) {
            errors.add(messageContext.getMessage("description_required"));
        }

        if (StringUtils.isGreaterThan(subject.getDescription(), 40)) {
            errors.add(messageContext.getMessage("description_max_length"));
        }

        if (!errors.isEmpty()) {
            throw new DomainException(HttpStatus.BAD_REQUEST.value(), String.join(COMMA, errors));
        }
    }
}
