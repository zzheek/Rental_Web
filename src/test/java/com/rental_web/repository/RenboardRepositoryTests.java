package com.rental_web.repository;

import com.rental_web.domain.Renboard;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.parameters.P;

import java.util.Optional;
import java.util.stream.IntStream;


@SpringBootTest
@Log4j2
public class RenboardRepositoryTests {

    @Autowired
    private RenboardRepository renboardRepository;

    @Test
    public void testInsert() {

        IntStream.rangeClosed(1,10).forEach(i -> {
            Renboard renboard = Renboard.builder()
                    .rentitle("title.." + i)
                    .rencontent("content.." + i)
                    .renwriter("user" + i )
                    .build();

            Renboard result = renboardRepository.save(renboard);
            log.info("Rennum: " + result.getRennum());
        });

    }

    @Test
    public void testUpdate() {

        Long rennum = 10L;

        Optional<Renboard> result = renboardRepository.findById(rennum);

        Renboard renboard = result.orElseThrow();

        renboard.change("update..title 10", "update.. content 10");

        renboardRepository.save(renboard);
    }

    @Test
    public void testSearch2() {

        Pageable pageable = PageRequest.of(1,10, Sort.by("rennum").descending());

        renboardRepository.search2(pageable);
    }

    @Test
    public void testSearchAll() {

        String[] types = {"t","c","w"};

        String keywrod = "1";

        Pageable pageable = PageRequest.of(0,10, Sort.by("rennum").descending());

        Page<Renboard> result = renboardRepository.searchAll(types,keywrod,pageable);
    }
}
