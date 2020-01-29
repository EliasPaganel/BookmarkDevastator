package com.example.testProject.repos;

import com.example.testProject.domain.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepo extends JpaRepository<Bookmark, Long> {

    Iterable<Bookmark> findByTextContaining(String Text);
}
