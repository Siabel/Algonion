package com.e1i5.backend.global.util;

public class TierUtil {
    public static int calculateTier(int userScore) {
        if (userScore < 1) {
            return 0;
        } else if (userScore < 101) {
            return 1;
        } else if (userScore < 801) {
            return 2;
        } else if (userScore < 4001) {
            return 3;
        } else if (userScore < 20001) {
            return 4;
        } else if (userScore < 100001) {
            return 5;
        } else if (userScore < 300000) {
            return 6;
        } else {
            return 7;
        }
    }
}