package com.example.tracker.issue;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "issues")
public class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String title;

    @Column
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IssueStatus status;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public Issue() {
    }

    public Issue(String title, String description) {
        this.title = title;
        this.description = description;
        this.status = IssueStatus.OPEN;
        this.createdAt = LocalDateTime.now();
    }

    public void updateDetails(String newTitle, String newDescription) {
        if (newTitle != null && !newTitle.isEmpty())
            this.title = newTitle;

        if (newDescription != null)
            this.description = newDescription;
    }

    public void advanceStatus(IssueStatus newStatus) {
        if (this.status == IssueStatus.DONE)
            throw new IllegalStateException("Ein abgeschlossenes Issue kann nicht mehr geändert werden!");
        this.status = newStatus;
    }

    public Long getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public IssueStatus getStatus() {
        return this.status;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }
}
