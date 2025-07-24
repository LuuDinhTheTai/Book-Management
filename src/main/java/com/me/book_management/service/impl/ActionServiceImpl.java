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

    @Override
    public Action createIfNotExists(Action action) {
        log.info("(create) action: {}", action);

        Action existingAction = findByName(action.getName());
        if (existingAction != null) {
            return existingAction;
        }

        return actionRepository.save(action);
    }

    @Override
    public Action findByName(String name) {
        log.info("(find) action: {}", name);

        return actionRepository.findByName(name).
                orElse(null);
    }
}
