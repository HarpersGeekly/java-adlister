package com.codeup.adlister.dao;

import com.codeup.adlister.models.Ad;

import java.util.List;

public interface Ads {
    // get a list of all the ads
    List<Ad> all();
    //show one ad
    Ad showOneAd(Long id);
    // show users ads
    List<Ad> showUsersAds(Long id);
    // findbyID
    Ad findById(Long id);
    // insert a new ad and return the new ad's id
    Long insert(Ad ad);
    // edit/replace ad with a new ad
    void update(Ad ad);


    // delete ad
    void deleteAd(Long id);

    //search bar
    List<Ad> search(String searchQuery);
}
