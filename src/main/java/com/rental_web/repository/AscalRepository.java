package com.rental_web.repository;

import com.rental_web.domain.Ascal;
import com.rental_web.domain.Reply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface AscalRepository extends JpaRepository<Ascal,Long> {
    @Query("select a from Ascal a where a.ascalnum = :ascalnum")
    Page<Ascal> listOfAscal(Long ascalnum, Pageable pageable);

    Optional<Ascal> findByAscaltime(String ascaltime);
    void deleteByAscaltime(String ascaltime);
}
