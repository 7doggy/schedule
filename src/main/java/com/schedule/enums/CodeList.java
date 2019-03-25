package com.schedule.enums;

import java.util.Map;

public interface CodeList {
    Map<String, String> getMap(String bizType);

    Map<String, String> toMap();
}
