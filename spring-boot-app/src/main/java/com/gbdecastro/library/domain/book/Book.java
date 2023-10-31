package com.gbdecastro.library.domain.book;


import com.gbdecastro.library.domain.bookAuthor.BookAuthor;
import com.gbdecastro.library.domain.bookSubject.BookSubject;
import com.gbdecastro.library.domain.shared.Audit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table
public class Book extends Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 40)
    private String title;

    @Column(nullable = false)
    private Integer edition;

    @Column(nullable = false, length = 4)
    private String publicationYear;

    @OneToMany(mappedBy = "book")
    @Builder.Default
    private Set<BookAuthor> bookAuthors = new HashSet<>();

    @OneToMany(mappedBy = "book")
    @Builder.Default
    private Set<BookSubject> bookSubjects = new HashSet<>();

}
