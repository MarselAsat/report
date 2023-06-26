package com.nppgks.reportingsystem.reportgeneration.acts;

import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.LinkedHashMap;

class ActOfAcceptanceGeneratorTest {

    @Test
    void getCurrentShift() {
        LocalTime currentTime1 = LocalTime.parse("10:01");
        LocalTime currentTime2 = LocalTime.parse("22:00");
        LocalTime currentTime3 = LocalTime.parse("05:38");
        LinkedHashMap<String, String> shiftNumToSTartTime = new LinkedHashMap<>();
        shiftNumToSTartTime.put("1", "10:00");
        shiftNumToSTartTime.put("2", "18:00");
        shiftNumToSTartTime.put("3", "02:00");
        int currentShift1 = ActOfAcceptanceGenerator.getCurrentShift(currentTime1, shiftNumToSTartTime);
        int currentShift2 = ActOfAcceptanceGenerator.getCurrentShift(currentTime2, shiftNumToSTartTime);
        int currentShift3 = ActOfAcceptanceGenerator.getCurrentShift(currentTime3, shiftNumToSTartTime);
        System.out.println();
    }
}