package com.flipkart.utils.dwone.persistence.model;

import lombok.*;

@Getter
@EqualsAndHashCode
@Builder(toBuilder = true)
public class PageRequest {

    private Integer pageNumber;
    @Builder.Default
    private int pageSize = 10;
    private Integer offset;

    public int getOffset() {
        if (offset == null) {
            if (pageNumber == null) {
                pageNumber = 0;
            }
            offset = pageNumber * pageSize;
        }
        return offset;
    }

}
