package com.rental_web.repository;

import com.rental_web.domain.Member;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,String> {

    @EntityGraph(attributePaths = "roleSet")
    @Query("select m from Member m where m.memberid = :memberid and m.social = false")
    Optional<Member> getWithRoles(String memberid);

    @EntityGraph(attributePaths = "roleSet")    // 이메일 이용하여 회원정보 찾기
    Optional<Member> findByMemberemail (String memberemail);


    @Modifying
    @Transactional
    @Query("update Member m set m.memberpass =:memberpass where m.memberid = :memberid")
    void updatePassword(@Param("memberpass") String password, @Param("memberid") String memberid);

}
