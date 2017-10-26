package com.codeup.adlister.dao;
import com.codeup.adlister.Config;
import com.codeup.adlister.models.Ad;
import com.codeup.adlister.models.User;
import com.mysql.cj.jdbc.Driver;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLAdsDao implements Ads {
    private Connection connection = null;

    public MySQLAdsDao() {
        try {
            DriverManager.registerDriver(new Driver());
            connection = DriverManager.getConnection(
                    Config.url,
                    Config.username,
                    Config.password
            );
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database!", e);
        }
    }

    @Override
    public List<Ad> all() {
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            // need to specify all the columns because id column will be ambigous since its the same name in both tables
            ResultSet rs = stmt.executeQuery(
                    "SELECT * FROM ads a inner join users u ON a.user_id = u.id ");
            return createAdsFromResults(rs);
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving all ads.", e);
        }
    }

    @Override
    public Ad showOneAd(Long id) {

        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM ads a inner join users u ON a.user_id = u.id where a.id = ?");
            //bind the '?' parameters with a specific value by using their indexes:
            statement.setLong(1, id);

            ResultSet rs = statement.executeQuery();
            rs.next(); //move to the first row of result set
            return extractAd(rs);
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving all ads.", e);
        }
    }

    @Override
    public List<Ad> showUsersAds(Long id) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM ads a inner join users u ON a.user_id = u.id where u.id = ?");
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            return createAdsFromResults(rs);
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving all ads.", e);
        }
    }

    @Override
    public Long insert(Ad ad) {

        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO ads(user_id, title, description) VALUES (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            //bind the '?' parameters with a specific value by using their indexes:
            statement.setLong(1, ad.getUserId());
            statement.setString(2, ad.getTitle());
            statement.setString(3, ad.getDescription());
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            generatedKeys.next();

            return generatedKeys.getLong(1);
        } catch (SQLException e) {
            throw new RuntimeException("Error creating a new ad.", e);
        }
    }

    private Ad extractAd(ResultSet rs) throws SQLException {
        return new Ad(
                rs.getLong("id"),
                //rs.getLong("user_id"), but instead create a new User:
                new User(rs.getLong("id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("password")),

                rs.getString("title"),
                rs.getString("description")
        );
    }

    private List<Ad> createAdsFromResults(ResultSet rs) throws SQLException {
        List<Ad> ads = new ArrayList<>();
        while (rs.next()) {
            ads.add(extractAd(rs));
        }
        return ads;
    }


    @Override
    public List<Ad> search(String searchQuery) {

        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM ads a inner join users u ON a.user_id = u.id " +
                            "WHERE title LIKE ? OR description like ?");
            //bind the '?' parameters with a specific value by using their indexes:
            statement.setString(1, "%" + searchQuery + "%");
            statement.setString(2, "%" + searchQuery + "%");

            ResultSet rs = statement.executeQuery();
            return createAdsFromResults(rs);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }








    //    public Long deleteAd(Ad ad) {
//        try {
//            PreparedStatement statement = connection.prepareStatement(
//                    "DELETE from ads (user_id, title, description) VALUES (?, ?, ?)",
//                    Statement.RETURN_GENERATED_KEYS
//            );
//            statement.setLong(1, ad.getUserId());
//            statement.setString(2, ad.getTitle());
//            statement.setString(3, ad.getDescription());
//            statement.executeUpdate();
//
//            ResultSet generatedKeys = statement.getGeneratedKeys();
//            generatedKeys.next();
//
//            return generatedKeys.getLong(1);
//
//        } catch (SQLException e) {
//            throw new RuntimeException("Error DELETING Ad!");
//        }
//    }
}


