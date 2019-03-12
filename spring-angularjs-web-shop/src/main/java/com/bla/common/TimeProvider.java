package com.bla.common;

import java.util.Date;

public class TimeProvider {

    private TimeProvider() {
    }

    public static Date now() {
        return new Date();
    }
}
