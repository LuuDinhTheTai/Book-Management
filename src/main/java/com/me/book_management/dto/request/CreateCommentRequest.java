package com.me.book_management.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CreateCommentRequest {

    private String content;

    public void validate() {

    }
}
