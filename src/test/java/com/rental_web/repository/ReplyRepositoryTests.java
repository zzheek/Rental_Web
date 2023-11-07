package com.rental_web.repository;

import com.rental_web.domain.Renboard;
import com.rental_web.domain.Reply;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class ReplyRepositoryTests {

    @Autowired
    private ReplyRepository replyRepository;

    @Test
    public void testInsert() {
        Long rennum = 10L;

        Renboard renboard = Renboard.builder().rennum(rennum).build();

        Reply reply = Reply.builder()
                .renboard(renboard)
                .replyText("댓글...")
                .replyer("replyer1")
                .build();

        replyRepository.save(reply);
    }
}
