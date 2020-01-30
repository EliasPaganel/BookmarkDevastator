package com.example.testProject.controllers;

import com.example.testProject.domain.Bookmark;
import com.example.testProject.domain.User;
import com.example.testProject.repos.BookmarkRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

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
                              @Valid Bookmark bookmark,
                              BindingResult bindingResult,
                              Model model,
                              @RequestParam MultipartFile file) throws IOException {
        bookmark.setAuthor(user);

        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorsMap);
            model.addAttribute("message", bookmark);
        } else {
            if (Objects.nonNull(file) && !file.isEmpty()) {
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }
                String resultFileName = UUID.randomUUID() + "." + file.getOriginalFilename();
                file.transferTo(new File(uploadPath + "/" + resultFileName));
                bookmark.setFileName(resultFileName);
            }
            model.addAttribute("message", null);
            bookmarkRepo.save(bookmark);
        }
        Iterable<Bookmark> bookmarks = bookmarkRepo.findAll();
        model.addAttribute("bookmarks", bookmarks);

        return "main";
    }


}
