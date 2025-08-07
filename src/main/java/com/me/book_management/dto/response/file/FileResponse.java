package com.me.book_management.dto.response.file;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.core.io.InputStreamResource;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class FileResponse implements Serializable {

    String filename;
    String contentType;
    Long fileSize;
    Date createdTime;
    transient InputStreamResource stream;
}
