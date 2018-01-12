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
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement("SELECT * FROM ads a inner join users u ON a.user_id = u.id");
            // I'm joining because on my index page for each ad I show what user that belong to. Starts at model Ad, add a User, go from there.
            // need to specify all the user_id column because id columns will be ambiguous since its the same name in both tables
            ResultSet rs = stmt.executeQuery();
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
            throw new RuntimeException("Error retrieving one ad.", e);
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
    public Ad findById(Long id) {
        String sql = "SELECT * FROM ads WHERE id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return new Ad(
                        rs.getLong("id"),
                        rs.getLong("user_id"),
                        rs.getString("title"),
                        rs.getString("description")
                );
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding user by id!", e);
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

    @Override
    public void update(Ad ad) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE ads SET title = ?, description = ? WHERE id= ?"
            );
            //bind the '?' parameters with a specific value by using their indexes:
            statement.setString(1, ad.getTitle());
            statement.setString(2, ad.getDescription());
            statement.setLong(3, ad.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error replacing an ad.", e);
        }
    }

    @Override
    public void deleteAd(Long id) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM ads WHERE id= ?"
            );

            statement.setLong(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error DELETING Ad!", e);
        }
    }

    private Ad extractAd(ResultSet rs) throws SQLException {
        return new Ad(
                rs.getLong("id"),
                //rs.getLong("user_id"), but instead create a new User:
                new User(rs.getLong("user_id"),
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
                            "WHERE title LIKE ? OR description LIKE ?");
            //bind the '?' parameters with a specific value by using their indexes:
            statement.setString(1, "%" + searchQuery + "%");
            statement.setString(2, "%" + searchQuery + "%");

            ResultSet rs = statement.executeQuery();
            return createAdsFromResults(rs);
        } catch (SQLException e) {
            throw new RuntimeException("Error searching the AdLister", e);
        }
    }



}