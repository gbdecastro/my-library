package com.gbdecastro.library.domain.book;

import com.gbdecastro.library.domain.shared.annotation.BaseService;
import org.springframework.beans.factory.annotation.Autowired;

@BaseService
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository repository;
}
