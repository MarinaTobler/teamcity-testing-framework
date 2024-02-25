package com.example.teamcity.api.generators;

import org.apache.commons.lang3.RandomStringUtils;

public final class RandomData {
    private static final int LENGTH = 10;

    private RandomData() {

    }

    public static String getString() {
        return "test_" + RandomStringUtils.randomAlphabetic(LENGTH);
    }

    public static String getValidID() {
        return "test_" + RandomStringUtils.randomAlphanumeric(LENGTH);
    }

    public static String getValidName() {
        return "test_" + RandomStringUtils.randomAlphabetic(LENGTH);
    }

    // String contains any characters
    public static String getRandomString() {
        return RandomStringUtils.random(10);
    }

    public static String getOnlyLatinLetters() {
        return RandomStringUtils.randomAlphabetic(LENGTH);
    }

    public static String getOnlyNumbers() {
        return RandomStringUtils.randomNumeric(LENGTH);
    }

    public static String getLettersAndNumbers() {
        return RandomStringUtils.randomAlphanumeric(LENGTH);
    }

    public static String getString225() {
        return RandomStringUtils.randomAlphabetic(225);
    }

    public static String getString226() {
        return RandomStringUtils.randomAlphabetic(226);
    }

    public static String getString80() {
        return RandomStringUtils.randomAlphabetic(80);
    }

    public static String getString81() {
        return RandomStringUtils.randomAlphabetic(81);
    }
    public static String getString256() {
        return RandomStringUtils.randomAlphabetic(256);
    }
    public static String getString257() {
        return RandomStringUtils.randomAlphabetic(257);
    }

    public static String getString1025() {
        return RandomStringUtils.randomAlphabetic(1025);
    }    public static String getString10001() {
        return RandomStringUtils.randomAlphabetic(10001);
    }

}
