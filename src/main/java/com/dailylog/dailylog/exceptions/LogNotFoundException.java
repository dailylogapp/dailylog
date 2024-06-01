package com.dailylog.dailylog.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogNotFoundException extends RuntimeException {
    public LogNotFoundException() {
    }
}
