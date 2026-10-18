package com.example.tracker.issue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.tracker.comment.CommentRepository;

// Reiner Unit-Test ohne Spring-Container für maximale Geschwindigkeit.
// @Mock erzeugt Attrappen, @InjectMocks übergibt sie in den Konstruktor.
@ExtendWith(MockitoExtension.class)
class IssueServiceTest {
    @Mock
    private IssueRepository issueRepository;

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private IssueService issueService;

    @Test
    void shouldThrowExceptionWhenTitleAlreadyExists() {
        CreateIssueRequest request = new CreateIssueRequest("Bug 1", "Test Beschreibung");
        Mockito.when(issueRepository.existsByTitle("Bug 1")).thenReturn(true);

        Assertions.assertThrows(IllegalArgumentException.class, () -> issueService.createIssue(request));

        Mockito.verify(issueRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void shouldCreateIssueSuccessfully() {
        CreateIssueRequest request = new CreateIssueRequest("Bug 2", "Test Beschreibung");
        Mockito.when(issueRepository.existsByTitle("Bug 2")).thenReturn(false);

        Issue issue = new Issue(request.title(), request.description());
        Mockito.when(issueRepository.save(Mockito.any(Issue.class))).thenReturn(issue);

        IssueResponse response = issueService.createIssue(request);

        Assertions.assertEquals(request.title(), response.title());
        Assertions.assertEquals(IssueStatus.OPEN, response.status());
    }
}
