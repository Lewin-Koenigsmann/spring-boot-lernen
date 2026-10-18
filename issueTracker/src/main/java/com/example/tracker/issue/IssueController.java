package com.example.tracker.issue;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.tracker.comment.CommentResponse;
import com.example.tracker.comment.CreateCommentRequest;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/issues")
public class IssueController {

    private final IssueService issueService;

    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }

    @PostMapping
    public ResponseEntity<IssueResponse> createIssue(@Valid @RequestBody CreateIssueRequest request) {
        IssueResponse issueResponse = issueService.createIssue(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(issueResponse);
    }

    @GetMapping("/{id}")
    public IssueResponse getIssue(@PathVariable Long id) {
        return issueService.getIssueById(id);
    }

    @GetMapping
    public List<IssueResponse> getIssuesByStatus(@RequestParam IssueStatus status) {
        return issueService.getIssuesByStatus(status);
    }

    @PatchMapping("/{id}/status")
    public IssueResponse updateIssueStatus(@PathVariable Long id, @RequestParam("status") IssueStatus newStatus) {
        return issueService.updateStatus(id, newStatus);
    }

    @PostMapping("/{issueId}/comments")
    public ResponseEntity<CommentResponse> createComment(@Valid @RequestBody CreateCommentRequest request,
            @PathVariable Long issueId) {
        CommentResponse response = issueService.addComment(issueId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{issueId}/comments")
    public List<CommentResponse> getCommentsForIssue(@PathVariable Long issueId) {
        return issueService.getCommentsForIssue(issueId);
    }
}
