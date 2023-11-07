package com.rental_web.repository;

import com.rental_web.domain.Reply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    @Query("select r from Reply r where r.renboard.rennum = :rennum")
    Page<Reply> listOfRenboard(Long rennum, Pageable pageable);

    void deleteByRenboard_Rennum(Long rennum);
}
