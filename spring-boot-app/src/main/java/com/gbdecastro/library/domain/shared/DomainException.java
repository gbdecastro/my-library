package com.gbdecastro.library.domain.shared;

import com.gbdecastro.library.domain.shared.message.MessageCode;
import lombok.Getter;

@Getter
public class DomainException extends RuntimeException {


    private final int code;

    public DomainException(int code, String message) {
        super(message);
        this.code = code;
    }

    public DomainException(MessageCode code, String message) {
        this(code.getCode(), message);
    }

}
