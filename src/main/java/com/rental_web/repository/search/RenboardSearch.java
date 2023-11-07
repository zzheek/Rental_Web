package com.rental_web.repository.search;

import com.rental_web.domain.Renboard;
import com.rental_web.dto.RenboardListAllDTO;
import com.rental_web.dto.RenboardListReplyCountDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RenboardSearch {

    Page<Renboard> search2(Pageable pageable);

    Page<Renboard> searchAll(String[] types, String keyword, Pageable pageable);

    Page<RenboardListReplyCountDTO> searchWithReplyCount(String[] types,
                                                         String keyword,
                                                         Pageable pageable);

    Page<RenboardListAllDTO> searchWithAll(String[] types,
                                           String keyword,
                                           Pageable pageable);
}
