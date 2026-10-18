package com.example.tracker.comment;

import java.time.LocalDateTime;

import com.example.tracker.issue.Issue;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String author;

    @Column(nullable = false, length = 1000)
    private String text;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // LAZY verhindert, dass das Issue bei jeder Kommentar-Abfrage ungefragt
    // per SQL-JOIN mitgeladen wird (Schutz vor Performance-Einbußen).
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "issue_id", nullable = false)
    private Issue issue;

    public Comment() {
    }

    public Comment(String author, String text, Issue issue) {
        this.author = author;
        this.text = text;
        this.issue = issue;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return this.id;
    }

    public String getAuthor() {
        return this.author;
    }

    public String getText() {
        return this.text;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public Issue getIssue() {
        return this.issue;
    }
}
