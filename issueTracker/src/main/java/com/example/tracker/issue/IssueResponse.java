package com.example.tracker.issue;

import java.time.LocalDateTime;

public record IssueResponse(Long id, String title, String description, IssueStatus status, LocalDateTime createdAt) {

}
