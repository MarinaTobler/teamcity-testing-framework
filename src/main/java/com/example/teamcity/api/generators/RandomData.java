package com.example.teamcity.api.generators;

import org.apache.commons.lang3.RandomStringUtils;

public final class RandomData {
    private static final int LENGTH = 10;
    private static final int LENGTH_1 = 80;
    private static final int LENGTH_1_MORE = 81;
    private static final int LENGTH_2 = 225;
    private static final int LENGTH_2_MORE = 226;
    private static final int LENGTH_3 = 1025;
    private static final int LENGTH_4 = 10001;

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
        return RandomStringUtils.random(LENGTH);
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
        return RandomStringUtils.randomAlphabetic(LENGTH_2);
    }

    public static String getString226() {
        return RandomStringUtils.randomAlphabetic(LENGTH_2_MORE);
    }

    public static String getString80() {
        return RandomStringUtils.randomAlphabetic(LENGTH_1);
    }

    public static String getString81() {
        return RandomStringUtils.randomAlphabetic(LENGTH_1_MORE);
    }

    public static String getString1025() {
        return RandomStringUtils.randomAlphabetic(LENGTH_3);
    }    public static String getString10001() {
        return RandomStringUtils.randomAlphabetic(LENGTH_4);
    }

}
