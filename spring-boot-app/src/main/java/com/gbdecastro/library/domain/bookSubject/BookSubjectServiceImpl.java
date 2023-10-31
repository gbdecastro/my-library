package com.gbdecastro.library.domain.bookSubject;

import com.gbdecastro.library.domain.shared.annotation.BaseService;
import org.springframework.beans.factory.annotation.Autowired;

@BaseService
public class BookSubjectServiceImpl implements BookSubjectService {

    @Autowired
    private BookSubjectRepository repository;
}
