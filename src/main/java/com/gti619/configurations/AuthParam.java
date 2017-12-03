package com.gti619.configurations;

/**
 * Classe partagée par tous les utilisateurs afin que la configuration de l'administrateur soit la même pour tout le
 * monde.
 */

public class AuthParam {

    private static long maxAttempt = 3;

    private static long lockTime = 30;

    private static boolean complexPassword = true;

    /**
     * Accesseur de maxAttempt
     * @return maxAttempt le nombre maximal de tentatives d'authentification avant que le compte soit verrouillé
     */
    public static long getMaxAttempt() {
        return maxAttempt;
    }

    /**
     * Mutateur de maxAttempt
     * @param maxAttempt le nouveau nombre maximal de tentatives d'authentification avant que le compte soit verrouillé
     */
    public static void setMaxAttempt(long maxAttempt) {
        AuthParam.maxAttempt = maxAttempt;
    }

    /**
     * Accesseur de lockTime
     * @return lockTime la durée, en secondes, du verrouillage de compte
     */
    public static long getLockTime() {
        return lockTime;
    }

    /**
     * Mutateur de lockTime
     * @param lockTime la nouvelle durée, en secondes, du verrouillage de compte
     */
    public static void setLockTime(long lockTime) {
        AuthParam.lockTime = lockTime;
    }

    /**
     * Accesseur de complexPassword
     * @return complexPassword vrai si la complexité du mot de passe est vérifié
     */
    public static boolean isComplexPassword() {
        return complexPassword;
    }

    /**
     * Mutateur de complexPassword
     * @param complexPassword permettre d'activer (ou de désactiver) la vérification de la complexité du mot de passe
     */
    public static void setComplexPassword(boolean complexPassword) {
        AuthParam.complexPassword = complexPassword;
    }
}