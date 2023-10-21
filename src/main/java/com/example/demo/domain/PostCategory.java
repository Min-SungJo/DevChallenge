package com.example.demo.domain;

public enum PostCategory {// [전체 게시글, 질문 글, 일반 글]
    ALL("전체 게시글"),
    QNA("질문 게시글"),
    NORMAL("일반 게시글");

    private final String category;

    public String getCategory() {
        return category;
    }

    PostCategory(String category) {
        this.category = category;
    }
}
