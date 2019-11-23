/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vizug
 */
public class Database {

    private Connection conn;
    private static Database instance;

    private Database() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (Exception ex) {
            ex.getStackTrace();
        }

        try {
            getConnection();
        } catch (Exception ex) {
            ex.getStackTrace();
        }
    }

    public synchronized static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public void getConnection() throws SQLException {
        conn = DriverManager.getConnection("jdbc:postgresql://localhost/onlineshopdb", "postgres", "postgres");
    }

    public void getPassword(String username) {
        try {
            Statement stat = conn.createStatement();
            String sql = "SELECT * FROM customer WHERE username='" + username + "';";
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        instance = new Database();
    }
}
