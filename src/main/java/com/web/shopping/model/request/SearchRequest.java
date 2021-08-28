package com.web.shopping.model.request;

import lombok.Data;

@Data
public class SearchRequest {
    private String keyword;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
