package com.sorsix.interns.finalproject.wats.persistence;

import com.sorsix.interns.finalproject.wats.domain.forum.ForumQuestion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ForumQuestionsDAO extends JpaRepository<ForumQuestion, Long>{

    Page<ForumQuestion> findByLocationId(Long id, Pageable pageable);
}
