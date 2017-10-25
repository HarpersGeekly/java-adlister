package com.codeup.adlister.dao;

import com.codeup.adlister.models.Ad;

import java.util.List;

public interface Ads {
    // get a list of all the ads
    List<Ad> all();
    //show one ad
    Ad showOneAd(Long id);
    // insert a new ad and return the new ad's id
    Long insert(Ad ad);
    // delete a new ad
//    Long deleteAd(Ad ad);
}
