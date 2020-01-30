package com.example.testProject.domain;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
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

    @NotBlank(message = "Please fill the bookmark text")
    @Length(max = 2048, message = "Text larger than 2048 characters.")
    private String text;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

    private String fileName;

    public Bookmark(String text, User author) {
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
