package com.flipkart.utils.dwone.persistence.model;

import lombok.Data;

import java.util.List;

@Data
public class Page<T> {

    private List<T> content;
    private Long totalCount;
    /**
     * Approximation:
     * If false it means there are no more results. If true, it can mean either
     * 1. There is more content in the next page. OR
     * 2. Its exactly equal to the current pageSize. Next page will have zero content.
     */
    private boolean hasMore;

    public Page(long totalCount, List<T> content) {
        this(totalCount, content, false);
    }

    public Page(Long totalCount, List<T> content, boolean hasMore) {
        this.totalCount = totalCount;
        this.content = content;
        this.hasMore = hasMore;
    }
}
