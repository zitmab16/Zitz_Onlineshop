/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;

import BL.Alpaca;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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

    public boolean checkPassword(String username, String pw) throws SQLException {

        Statement stat = conn.createStatement();
        String sql = "SELECT * FROM customer WHERE username='" + username + "';";
        ResultSet rs = stat.executeQuery(sql);

        while (rs.next()) {
            System.out.println(pw + "++" + rs.getString("password"));
            return pw.equals(rs.getString("password"));
        }

        return false;
    }

    public int getUID(String username) throws SQLException {
        int id = 0;

        Statement stat = conn.createStatement();
        String sql = "SELECT id FROM customer WHERE username='" + username + "';";
        ResultSet rs = stat.executeQuery(sql);

        while (rs.next()) {
            id = rs.getInt("id");
        }

        return id;
    }

    public int getcaID(int userid) throws SQLException {
        PreparedStatement stat = conn.prepareStatement("SELECT COUNT(id) FROM cart WHERE customerid=?;");
        stat.setInt(1, userid);
        ResultSet rs = stat.executeQuery();

        rs.next();
        if (rs.getInt("count") == 1) {
            stat = conn.prepareStatement("SELECT id FROM cart WHERE customerid=?;");
            stat.setInt(1, userid);
            rs = stat.executeQuery();
            rs.next();
            return rs.getInt("id");
        } else {
            stat = conn.prepareStatement("INSERT INTO cart(customerid) VALUES (?);");
            stat.setInt(1, userid);
            stat.execute();
            return getcaID(userid);
        }
    }

    public ArrayList<Alpaca> getAlpacas(int cartID) throws SQLException {
        ArrayList<Alpaca> alpacas = new ArrayList();

        String sql = "SELECT * FROM alpaca a LEFT OUTER JOIN cartposition cp ON a.id =cp.alpacaid"
                + "WHERE cp.cartid=?;";
        PreparedStatement stat = conn.prepareStatement(sql);
        stat.setInt(1, cartID);
        ResultSet rs = stat.executeQuery();

        while (rs.next()) {
            Alpaca a = new Alpaca(rs.getInt("id"), rs.getString("typ"), rs.getInt("price"), rs.getInt("amount"));
            alpacas.add(a);
        }
        System.out.println(alpacas);
        return alpacas;
    }

    public static void main(String[] args) {
        instance = new Database();
    }
}
