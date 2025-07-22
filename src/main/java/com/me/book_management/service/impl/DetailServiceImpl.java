package com.me.book_management.service.impl;

import com.me.book_management.dto.request.CreateBookRequest;
import com.me.book_management.entity.book.Detail;
import com.me.book_management.repository.book.DetailRepository;
import com.me.book_management.service.DetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DetailServiceImpl implements DetailService {

    private final DetailRepository detailRepository;

    @Override
    public Detail create(CreateBookRequest.Detail detail) {
        log.info("(create) detail: {}", detail);

        Detail detailEntity = new Detail();
        detailEntity.setIsbn(detail.getIsbn());
        detailEntity.setTitle(detail.getTitle());
        detailEntity.setSubtitle(detail.getSubtitle());
        detailEntity.setAuthor(detail.getAuthor());
        detailEntity.setPublisher(detail.getPublisher());
        detailEntity.setPublishedDate(detail.getPublishedDate());
        detailEntity.setDescription(detail.getDescription());
        detailEntity.setPageCount(detail.getPageCount());
        detailEntity.setLanguage(detail.getLanguage());
        detailEntity.setFormat(detail.getFormat());

        return detailRepository.save(detailEntity);
    }
}
