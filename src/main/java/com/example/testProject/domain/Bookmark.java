package com.example.testProject.domain;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Bookmark {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String link;
    private String text;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

    private String fileName;

    public Bookmark(String link, String text, User author) {
        this.link = link;
        this.text = text;
        this.author = author;
    }

//    @OneToMany(fetch = FetchType.EAGER)
//    @JoinColumn(name = "tag_id")
//    private Set<Tag> tags;


//    private LocalDate createDateTime;
//    private LocalDate sendingDateTime;
//    private Boolean isSend;


    public String getAuthorName() {
        return author != null ? author.getUsername() : "<none>";
    }


}
