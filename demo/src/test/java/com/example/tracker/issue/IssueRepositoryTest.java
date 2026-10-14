package com.example.tracker.issue;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

@DataJpaTest 
class IssueRepositoryTest {
    
    @Autowired 
    private IssueRepository issueRepository;

    @Test 
    void shouldFindIssuesByTitleIgnoreCase()
    {
        issueRepository.save(new Issue("Login Button kaputt", "Der Login Button funktioniert nicht mehr " +
        "seit Update."));

        issueRepository.save(new Issue("Datenbank Fehler", "Fehler in der Datenbank"));

        List<Issue> found = issueRepository.findByTitleContainingIgnoreCase("button");

        Assertions.assertEquals(1, found.size());
        Assertions.assertEquals("Login Button kaputt", found.get(0).getTitle());
    }
}
