package com.study.Board.post.entity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Status {

    PENDING("pending", "승인 대기"),
    APPROVED("approved", "승인됨"),
    REJECTED("rejected", "거부됨");

    private final String value;
    private final String description;

}
