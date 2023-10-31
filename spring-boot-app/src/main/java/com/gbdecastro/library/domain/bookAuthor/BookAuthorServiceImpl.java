package com.gbdecastro.library.domain.bookAuthor;

import com.gbdecastro.library.domain.shared.annotation.BaseService;
import org.springframework.beans.factory.annotation.Autowired;

@BaseService
public class BookAuthorServiceImpl implements BookAuthorService {

    @Autowired
    private BookAuthorRepository repository;
}
