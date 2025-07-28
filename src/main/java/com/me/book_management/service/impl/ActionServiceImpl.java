package com.me.book_management.service.impl;

import com.me.book_management.entity.rbac0.Action;
import com.me.book_management.exception.NotFoundException;
import com.me.book_management.repository.rbac0.ActionRepository;
import com.me.book_management.service.ActionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ActionServiceImpl implements ActionService {

    private final ActionRepository actionRepository;
}
