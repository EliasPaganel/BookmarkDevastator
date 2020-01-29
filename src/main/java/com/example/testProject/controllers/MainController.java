package com.example.testProject.controllers;

import com.example.testProject.domain.Bookmark;
import com.example.testProject.domain.User;
import com.example.testProject.repos.BookmarkRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Controller
public class MainController {

    @Autowired
    private BookmarkRepo bookmarkRepo;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/")
    public String greeting(Map<String, Object> model) {
        return "greeting";
    }

    @GetMapping("/main")
    public String filter(@RequestParam(required = false, defaultValue = "") String filter, Model model) {
        Iterable<Bookmark> bookmarks;
        if (Objects.nonNull(filter) && !filter.isEmpty()) {
            bookmarks = bookmarkRepo.findByTextContaining(filter);
        } else {
            bookmarks = bookmarkRepo.findAll();
        }

        model.addAttribute("bookmarks", bookmarks);
        model.addAttribute("filter", filter);
        return "main";
    }

    @PostMapping("/main")
    public String addBookmark(@AuthenticationPrincipal User user,
                              @RequestParam String link,
                              @RequestParam String text,
                              @RequestParam MultipartFile file,
                              Map<String, Object> model) throws IOException {

        if (Objects.nonNull(link) && !link.isEmpty() ||
                Objects.nonNull(text) && !text.isEmpty() ||
                Objects.nonNull(file) && !file.isEmpty()) {
            Bookmark bookmark = new Bookmark(link, text, user);

            if (file != null && !file.isEmpty()) {
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }
                String resultFileName = UUID.randomUUID() + "." + file.getOriginalFilename();
                file.transferTo(new File(uploadPath + "/" + resultFileName));
                bookmark.setFileName(resultFileName);
            }
            bookmarkRepo.save(bookmark);
        }
        Iterable<Bookmark> bookmarks = bookmarkRepo.findAll();
        model.put("bookmarks", bookmarks);

        return "main";
    }


}
