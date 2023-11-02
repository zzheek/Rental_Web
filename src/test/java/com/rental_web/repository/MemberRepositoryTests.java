package com.rental_web.repository;

import com.rental_web.domain.Member;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class MemberRepositoryTests {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void insertMembers() {

        IntStream.rangeClosed(1,10).forEach(i -> {

            Member member = Member.builder()
                    .memberid("test" + i)
                    .membername("김철수")
                    .memberpass("111" + i)
                    .memberemail("test" + i + "@abc.com")
                    .memberphone(Long.valueOf("0101234567" + i))
                    .memberaddr("경기도 남양주시")
                    .build();

            memberRepository.save(member);
        });
    }
}
