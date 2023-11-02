package com.rental_web.repository;

import com.rental_web.domain.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,String> {

    @EntityGraph(attributePaths = "roleSet")
    @Query("select m from Member m where m.memberid = :memberid and m.social = false")
    Optional<Member> getWithRoles(String memberid);
}
