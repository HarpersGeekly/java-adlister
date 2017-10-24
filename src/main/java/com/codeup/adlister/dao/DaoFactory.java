package com.codeup.adlister.dao;

public class DaoFactory {
    private static Ads adsDao;
    private static Users usersDao;

    public static Ads getAdsDao() {
        if (adsDao == null) {
            adsDao = new MySQLAdsDao();
        }
        return adsDao;
    }

    public static Users getUsersDao() {
        //we're making sure we only have one MySQLUsersDao(), but each Dao will have it's own connection
        if (usersDao == null) {
            usersDao = new MySQLUsersDao();
        }
        return usersDao;
    }
}
