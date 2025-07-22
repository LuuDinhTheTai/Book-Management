package com.me.book_management.service;

import com.me.book_management.entity.rbac0.Action;

public interface ActionService {

    Action createIfNotExists(Action action);
    Action findByName(String name);
}
