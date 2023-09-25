package com.api_anime.anime.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {

    private String userName ;
    private long filmCmtId;
    private String userAvatar;
    private String commentContent;
    private boolean status;
    private Date createdAt;
    private long filmCmtParentId;
}
