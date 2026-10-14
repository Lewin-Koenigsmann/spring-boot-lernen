package com.example.tracker.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateCommentRequest(
    @NotBlank 
    @Size (max = 100)
    String author,

    @NotBlank 
    @Size (max = 1000)
    String text
) {

}
