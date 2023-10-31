package com.gbdecastro.library.domain.subject;

import com.gbdecastro.library.domain.shared.annotation.BaseService;
import org.springframework.beans.factory.annotation.Autowired;

@BaseService
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    private SubjectRepository repository;
}
