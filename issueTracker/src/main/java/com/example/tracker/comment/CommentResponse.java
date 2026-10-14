package com.example.tracker.comment;

import java.time.LocalDateTime;

public record CommentResponse(Long id, String author, String text, LocalDateTime createdAt) {

}
