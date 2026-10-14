package com.example.tracker.issue;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateIssueRequest(
    @NotBlank 
    @Size (max = 150)
    String title,

    @Size (max = 150)
    String description
) {

}
