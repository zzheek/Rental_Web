package com.rental_web.repository;

import com.rental_web.domain.Renboard;
import com.rental_web.repository.search.RenboardSearch;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RenboardRepository extends JpaRepository<Renboard, Long>, RenboardSearch {

    @EntityGraph(attributePaths = {"imageSet"}) // imageSet을 함께 로딩
    @Query(value = "select r from Renboard r where r.rennum = :rennum")
    Optional<Renboard> findByIdWithImages(Long rennum);
}
