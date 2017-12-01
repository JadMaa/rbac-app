package com.gti619.configurations;

//This class is a static class that shared by everyone. So the Admin configuration is the same for everyone.

public class AuthParam {

    private static long maxAttempt = 3;

    private static long lockTime = 30;

    private static boolean complexPassword = true;

    public static long getMaxAttempt() {
        return maxAttempt;
    }

    public static void setMaxAttempt(long maxAttempt) {
        AuthParam.maxAttempt = maxAttempt;
    }

    public static long getLockTime() {
        return lockTime;
    }

    public static void setLockTime(long lockTime) {
        AuthParam.lockTime = lockTime;
    }

    public static boolean isComplexPassword() {
        return complexPassword;
    }

    public static void setComplexPassword(boolean complexPassword) {
        AuthParam.complexPassword = complexPassword;
    }
}
