package com.rental_web.repository;

import com.rental_web.domain.Ascal;
import com.rental_web.domain.Reply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;



public interface AscalRepository extends JpaRepository<Ascal,Long> {
    @Query("select a from Ascal a where a.ascalnum = :ascalnum")
    Page<Reply> listOfAscal(Long ascalnum, Pageable pageable);

}