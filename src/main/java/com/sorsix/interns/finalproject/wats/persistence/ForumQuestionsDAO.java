package com.sorsix.interns.finalproject.wats.persistence;

import com.sorsix.interns.finalproject.wats.domain.Forum.ForumQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ForumQuestionsDAO extends JpaRepository<ForumQuestion, Long>{
    Collection<ForumQuestion> findByLocationId(long id);
}
