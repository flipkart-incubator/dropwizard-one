package com.flipkart.utils.dwone.commons.exceptions;

import com.flipkart.utils.dwone.commons.CommonConstants;
import lombok.Getter;

import static java.lang.String.format;

@Getter
public class DuplicateRecordException extends RuntimeException {
    private final String recordId;
    private final Class clazz;

    public DuplicateRecordException(Class clazz, String recordId) {
        super(format(CommonConstants.DUPLICATE_RECORD_MESSAGE, clazz.getSimpleName(), recordId));
        this.clazz = clazz;
        this.recordId = recordId;
    }
}
