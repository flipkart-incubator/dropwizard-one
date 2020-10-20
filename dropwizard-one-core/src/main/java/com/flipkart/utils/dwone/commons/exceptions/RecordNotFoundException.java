package com.flipkart.utils.dwone.commons.exceptions;

import com.flipkart.utils.dwone.commons.CommonConstants;
import lombok.Getter;

import static java.lang.String.format;

@Getter
public class RecordNotFoundException extends RuntimeException {
    private final Class clazz;
    private final String id;

    public RecordNotFoundException(Class clazz, String id) {
        super(format(CommonConstants.RECORD_NOT_FOUND_MESSAGE, clazz.getSimpleName(), id));
        this.clazz = clazz;
        this.id = id;
    }
}
