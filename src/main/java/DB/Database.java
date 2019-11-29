package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Database
{
    public static Connection conn = null;
    public static Database instance = null;
    
    private Database() throws SQLException
    {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        conn = DriverManager.getConnection("jdbc:postgresql://localhost/onlineshopdb", "postgres", "postgres");
        
        instance = this;

    }
    
    public static Database getInstance() throws SQLException
    {
        if(instance == null)
            new Database();
        
        return instance;
    }
    
    public boolean login(String username, String pass) throws SQLException
    {
        PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM customer WHERE username = ? AND pass = ?");
        ps.setString(1, username);
        ps.setString(2, pass);
        
        ResultSet rs = ps.executeQuery();
        rs.next();
        
        return rs.getInt("count") == 1;
    }
    
    
    public int getCustomerID(String username) throws SQLException
    {
        PreparedStatement ps = conn.prepareStatement("SELECT customerid FROM customer WHERE username = ?");
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();
        
        rs.next();
        
        return rs.getInt("customerid");
        
    }
    
    public ArrayList<BL.Alpaca> getItems(int cartid) throws SQLException
    {
        ArrayList<BL.Alpaca> items = new ArrayList();
        
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM article;");
        ResultSet rs = ps.executeQuery();

        
        while(rs.next())
        {
            items.add(new BL.Alpaca(rs.getInt("articleid"), rs.getString("articlename"), rs.getDouble("price"), 0));
        }
        
        return  items;
    }
    
    public ArrayList<BL.Alpaca> getCartItems(int cartid, ArrayList<BL.Alpaca> items) throws SQLException
    {
        PreparedStatement ps = conn.prepareStatement("SELECT articleid, amount FROM cartarticle WHERE cartid = ?");
        ps.setInt(1, cartid);
        
        ResultSet rs = ps.executeQuery();
        
        while(rs.next())
        {
            for(BL.Alpaca i: items)
            {
                if(i.getId() == rs.getInt("articleid"))
                    i.setAmount(rs.getInt("amount"));
            }
        }
        
        
        return items;
    }
    
    public int getCustomerCart(int customerid) throws SQLException
    {
        PreparedStatement ps = conn.prepareCall("SELECT COUNT(*) FROM cart WHERE customerid = ?");
        ps.setInt(1, customerid);
        
        ResultSet rs = ps.executeQuery();
        rs.next();
                
        if(rs.getInt("count") == 1)
        {
            ps = conn.prepareStatement("SELECT cartid FROM cart WHERE customerid = ?");
            ps.setInt(1, customerid);
            rs = ps.executeQuery();
            rs.next();
            
            return rs.getInt("cartid");
        }
        else
        {
            ps = conn.prepareStatement("INSERT INTO cart(customerid) VALUES(?)");
            ps.setInt(1, customerid);
            ps.execute();
            
            return getCustomerCart(customerid);
        }
    }
}