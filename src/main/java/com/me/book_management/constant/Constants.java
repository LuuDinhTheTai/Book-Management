package com.me.book_management.constant;

public class Constants {

    public static class ROLE {

        public static final String ADMIN = "ADMIN";
        public static final String USER = "USER";
    }

    public static class PERMISSION {

        public static final String CREATE_BOOK = ACTION.CREATE + RESOURCE.BOOK;
        public static final String READ_BOOK = ACTION.READ + RESOURCE.BOOK;
        public static final String UPDATE_BOOK = ACTION.UPDATE + RESOURCE.BOOK;
        public static final String DELETE_BOOK = ACTION.DELETE + RESOURCE.BOOK;
        public static final String CREATE_COMMENT = ACTION.CREATE + RESOURCE.COMMENT;
        public static final String READ_COMMENT = ACTION.READ + RESOURCE.COMMENT;
        public static final String UPDATE_COMMENT = ACTION.UPDATE + RESOURCE.COMMENT;
        public static final String DELETE_COMMENT = ACTION.DELETE + RESOURCE.COMMENT;
        public static final String CREATE_ACCOUNT = ACTION.CREATE + RESOURCE.ACCOUNT;
        public static final String READ_ACCOUNT = ACTION.READ + RESOURCE.ACCOUNT;
        public static final String UPDATE_ACCOUNT = ACTION.UPDATE + RESOURCE.ACCOUNT;
        public static final String DELETE_ACCOUNT = ACTION.DELETE + RESOURCE.ACCOUNT;
        public static final String CREATE_CART = ACTION.CREATE + RESOURCE.CART;
        public static final String READ_CART = ACTION.READ + RESOURCE.CART;
        public static final String UPDATE_CART = ACTION.UPDATE + RESOURCE.CART;
        public static final String DELETE_CART = ACTION.DELETE + RESOURCE.CART;
        public static final String CREATE_ROLE = ACTION.CREATE + RESOURCE.ROLE;
        public static final String READ_ROLE = ACTION.READ + RESOURCE.ROLE;
        public static final String UPDATE_ROLE = ACTION.UPDATE + RESOURCE.ROLE;
        public static final String DELETE_ROLE = ACTION.DELETE + RESOURCE.ROLE;
        public static final String CREATE_CATEGORY = ACTION.CREATE + RESOURCE.CATEGORY;
        public static final String READ_CATEGORY = ACTION.READ + RESOURCE.CATEGORY;
        public static final String UPDATE_CATEGORY = ACTION.UPDATE + RESOURCE.CATEGORY;
        public static final String DELETE_CATEGORY = ACTION.DELETE + RESOURCE.CATEGORY;
    }

    public static class ACTION {

        public static final String CREATE = "CREATE";
        public static final String READ = "READ";
        public static final String UPDATE = "UPDATE";
        public static final String DELETE = "DELETE";
    }

    public static class RESOURCE {

        public static final String BOOK = "BOOK";
        public static final String COMMENT = "COMMENT";
        public static final String ACCOUNT = "ACCOUNT";
        public static final String CART = "CART";
        public static final String ROLE = "ROLE";
        public static final String CATEGORY = "CATEGORY";
    }
}
