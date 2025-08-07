package com.me.book_management.constant;

import java.util.List;

public class PERMISSION {

    public static class ACTION {

        public static final String GET = "GET";
        public static final String POST = "POST";

        public static List<String> list() {
            return List.of(GET, POST);
        }
    }

    public static class RESOURCE {

        public static final String Account = "/accounts/";
        public static final String Account_Profile = Account + "profile";
        public static final String Account_Update = Account + "update/{id}";
        public static final String Account_Delete = Account + "delete/{id}";

        public static final String Address = "/addresses/";
        public static final String Address_Create = Address + "create";
        public static final String Address_List = Address + "list";
        public static final String Address_Update = Address + "update/{id}";
        public static final String Address_Delete = Address + "delete/{id}";

        public static final String Admin = "/admin/";
        public static final String Admin_Dashboard = Admin + "dashboard";

        public static final String Attachment = "/attachments/";
        public static final String Attachment_Upload = Attachment + "upload";

        public static final String Auth = "/auth/";
        public static final String Auth_Signin = Auth + "signin";
        public static final String Auth_Signup = Auth + "signup";

        public static final String Book = "/books/";
        public static final String Book_Create = Book + "create";
        public static final String Book_Detail = Book + "{id}";
        public static final String Book_List = Book + "list";
        public static final String Book_Update = Book + "update/{id}";
        public static final String Book_Delete = Book + "delete/{id}";
        public static final String Book_Search = Book + "search";

        public static final String Category = "/categories/";
        public static final String Category_Create = Category + "create";
        public static final String Category_List = Category + "list";
        public static final String Category_Update = Category + "update/{id}";
        public static final String Category_Delete = Category + "delete/{id}";

        public static final String Comment = "/comments/";
        public static final String Comment_Create = Comment + "create";

        public static final String Cart = "/carts/";
        public static final String Cart_Create = Cart + "create";
        public static final String Cart_List = Cart + "list";
        public static final String Cart_Delete = Cart + "delete";
        public static final String Cart_AddItem = Cart + "add-item";
        public static final String Cart_IncreaseItem = Cart + "increase-item/{id}";
        public static final String Cart_DecreaseItem = Cart + "decrease-item/{id}";
        public static final String Cart_Buy = Cart + "buy";

        public static final String Order = "/orders/";
        public static final String Order_List = Order + "list";

        public static final String Role = "/roles/";
        public static final String Role_Create = Role + "create";
        public static final String Role_List = Role + "list";
        public static final String Role_Update = Role + "update/{id}";
        public static final String Role_Delete = Role + "delete/{id}";

        public static List<String> list() {
            return List.of(
                    Account_Profile, Account_Update, Account_Delete,
                    Address_Create, Address_List, Address_Update, Address_Delete,
                    Admin_Dashboard,
                    Attachment_Upload,
                    Auth_Signin, Auth_Signup,
                    Book_Create, Book_Detail, Book_List, Book_Update, Book_Delete, Book_Search,
                    Category_Create, Category_List, Category_Update, Category_Delete,
                    Comment_Create,
                    Cart_Create, Cart_List, Cart_Delete, Cart_AddItem, Cart_IncreaseItem, Cart_DecreaseItem, Cart_Buy,
                    Order_List,
                    Role_Create, Role_List, Role_Update, Role_Delete
            );
        }
    }
}
