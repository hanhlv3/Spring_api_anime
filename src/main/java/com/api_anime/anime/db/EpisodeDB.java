package com.api_anime.anime.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EpisodeDB {

    public static int getEpisodeCurrent(Connection conn, Long filmId) throws SQLException {
        int e = 0;

        String query = "select COUNT(film_id) from episodes where film_id =" + filmId;
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(query);
        while (rs.next()) {
           e = rs.getInt(1);
        }
        return e;
    }
}
