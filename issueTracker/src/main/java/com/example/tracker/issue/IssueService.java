package com.example.tracker.issue;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.tracker.comment.Comment;
import com.example.tracker.comment.CommentRepository;
import com.example.tracker.comment.CommentResponse;
import com.example.tracker.comment.CreateCommentRequest;

// readOnly = true auf Klassenebene optimiert Lesezugriffe (kein Dirty
// Checking durch Hibernate). Schreibmethoden überschreiben dies gezielt.
@Service
@Transactional(readOnly = true)
public class IssueService {

    private final CommentRepository commentRepository;
    private final IssueRepository issueRepository;

    public IssueService(IssueRepository issueRepository, CommentRepository commentRepository) {
        this.issueRepository = issueRepository;
        this.commentRepository = commentRepository;
    }

    @Transactional(readOnly = false)
    public IssueResponse createIssue(CreateIssueRequest request) {
        if (issueRepository.existsByTitle(request.title()))
            throw new IllegalArgumentException("Titel existiert bereits");

        Issue issue = new Issue(request.title(), request.description());
        Issue saved = issueRepository.save(issue);
        return this.toResponse(saved);
    }

    public IssueResponse getIssueById(Long id) {
        return this.toResponse(this.getIssueByIdOrThrow(id));
    }

    public List<IssueResponse> getIssuesByStatus(IssueStatus status) {
        List<Issue> issues = issueRepository.findByStatus(status);

        return issues.stream().map(this::toResponse).toList();
    }

    // Kein repository.save() nötig: Hibernate erkennt Änderungen an geladenen
    // Entities innerhalb der Transaktion automatisch (Dirty Checking).
    @Transactional(readOnly = false)
    public IssueResponse updateStatus(Long id, IssueStatus newStatus) {
        Issue issue = this.getIssueByIdOrThrow(id);
        issue.advanceStatus(newStatus);
        return this.toResponse(issue);
    }

    @Transactional(readOnly = false)
    public CommentResponse addComment(Long issueId, CreateCommentRequest request) {
        Issue issue = this.getIssueByIdOrThrow(issueId);
        Comment comment = new Comment(request.author(), request.text(), issue);
        Comment saved = commentRepository.save(comment);

        return this.toComment(saved);
    }

    public List<CommentResponse> getCommentsForIssue(Long issueId) {
        if (!issueRepository.existsById(issueId))
            throw new NoSuchElementException("Issue mit ID " + issueId + " nicht gefunden.");

        List<Comment> comments = commentRepository.findByIssueId(issueId);

        return comments.stream().map(this::toComment).toList();
    }

    private IssueResponse toResponse(Issue issue) {
        return new IssueResponse(issue.getId(), issue.getTitle(), issue.getDescription(), issue.getStatus(),
                issue.getCreatedAt());
    }

    private CommentResponse toComment(Comment comment) {
        return new CommentResponse(comment.getId(), comment.getAuthor(), comment.getText(), comment.getCreatedAt());
    }

    private Issue getIssueByIdOrThrow(Long id) {
        return issueRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Issue mit ID " + id + " nicht gefunden."));
    }
}
