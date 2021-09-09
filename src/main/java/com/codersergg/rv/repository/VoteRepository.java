package com.codersergg.rv.repository;

import com.codersergg.rv.model.User;
import com.codersergg.rv.model.Vote;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Tag(name = "Vote Controller")
public interface VoteRepository extends JpaRepository<Vote, Integer> {

    @Query("SELECT v FROM Vote v WHERE v.date = current_date AND v.user = ?1")
    Vote getByUserToday(User user);

}
