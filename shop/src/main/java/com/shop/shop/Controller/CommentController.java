package com.shop.shop.Controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
//import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.shop.Dtos.CommentRequest;
import com.shop.shop.Dtos.CommentResponse;
import com.shop.shop.Model.CommentModel;
import com.shop.shop.Model.UserModel;
import com.shop.shop.Repository.CommentRepository;
import com.shop.shop.Repository.UserRepository;


@RestController
@RequestMapping("/comments")
@CrossOrigin // Allow frontend access
public class CommentController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private CommentRepository commentRepo;

    @PostMapping("/add")
    public ResponseEntity<CommentResponse> addComment(@RequestBody CommentRequest request) {
        UserModel user = userRepo.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        CommentModel comment = new CommentModel();
        comment.setContent(request.getContent());
        comment.setUser(user);
        comment.setCreatedAt(LocalDateTime.now());
        commentRepo.save(comment);

        return ResponseEntity.ok(
                new CommentResponse(comment.getContent(), user.getUsername(), comment.getCreatedAt())
        );
    }

    @GetMapping("/get")
    public List<CommentResponse> getAllComments() {
        return commentRepo.findAll(Sort.by(Sort.Direction.DESC, "createdAt"))
                .stream()
                .map(c -> new CommentResponse(c.getContent(), c.getUser().getUsername(), c.getCreatedAt()))
                .collect(Collectors.toList());
    }
}

