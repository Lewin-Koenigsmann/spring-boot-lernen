package com.example.tracker.issue;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface IssueRepository extends JpaRepository<Issue, Long> {

    List<Issue> findByStatus(IssueStatus status);

    List<Issue> findByTitleContainingIgnoreCase(String searchTerm);

    boolean existsByTitle(String title);
}
