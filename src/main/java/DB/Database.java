package DB;

import BL.OrderInformation;
import BL.Ordering;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Database {

    public static Connection conn = null;
    public static Database instance = null;

    private Database() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        conn = DriverManager.getConnection("jdbc:postgresql://localhost/onlineshopdb", "postgres", "postgres");

        instance = this;

    }

    public static Database getInstance() throws SQLException {
        if (instance == null) {
            new Database();
        }

        return instance;
    }

    /**
     * Methode für die Überprüfung des Passworts für den User
     *
     * @param username
     * @param pass
     * @return
     * @throws SQLException
     */
    public boolean login(String username, String pass) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM customer WHERE username = ? AND pass = ?");
        ps.setString(1, username);
        ps.setString(2, pass);

        ResultSet rs = ps.executeQuery();
        rs.next();

        return rs.getInt("count") == 1;
    }

    /**
     * Returned die CustomerID
     *
     * @param username
     * @return
     * @throws SQLException
     */
    public int getCustomerID(String username) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("SELECT customerid FROM customer WHERE username = ?");
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();

        rs.next();

        return rs.getInt("customerid");

    }

    /**
     * Rückgabe einer ArrayList mit den Artikeln
     *
     * @param cartid
     * @return
     * @throws SQLException
     */
    public ArrayList<BL.Alpaca> getItems(int cartid) throws SQLException {
        ArrayList<BL.Alpaca> items = new ArrayList();

        PreparedStatement ps = conn.prepareStatement("SELECT * FROM article;");
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            items.add(new BL.Alpaca(rs.getInt("articleid"), rs.getString("articlename"), rs.getDouble("price"), 0));
        }

        return items;
    }

    /**
     * Anzeigen eines bestehenden Carts mit der Menge der Artikel
     *
     * @param cartid
     * @param items
     * @return
     * @throws SQLException
     */
    public ArrayList<BL.Alpaca> getCartItems(int cartid, ArrayList<BL.Alpaca> items) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("SELECT articleid, amount FROM cartarticle WHERE cartid = ?");
        ps.setInt(1, cartid);

        ResultSet rs = ps.executeQuery();

        //Setzt der Amount der einzelnen Artikel auf das jeweilige Objekt
        while (rs.next()) {
            for (BL.Alpaca i : items) {
                if (i.getId() == rs.getInt("articleid")) {
                    i.setAmount(rs.getInt("amount"));
                }
            }
        }

        return items;
    }

    /**
     * Methode um das Cart des Users zu bekommen Wenn noch kein Cart vorhanden
     * ist wird ein Neues erstellt und nochmals rekursiv aufgerufen um die
     * CartID zu bekommen
     *
     * @param customerid
     * @return
     * @throws SQLException
     */
    public int getCustomerCart(int customerid) throws SQLException {
        //Anzahl der gesamten Carts
        PreparedStatement ps = conn.prepareCall("SELECT COUNT(*) FROM cart WHERE customerid = ?");
        ps.setInt(1, customerid);

        ResultSet rs = ps.executeQuery();
        rs.next();

        //wenn ein cart vorhanden ist wird die cartid zurückgegeben
        if (rs.getInt("count") == 1) {
            ps = conn.prepareStatement("SELECT cartid FROM cart WHERE customerid = ?");
            ps.setInt(1, customerid);
            rs = ps.executeQuery();
            rs.next();

            return rs.getInt("cartid");
        } else {
            //erstellt cart wenn es noch keines gibt und ruft die methode rekursiv auf 
            ps = conn.prepareStatement("INSERT INTO cart(customerFid) VALUES(?)");
            ps.setInt(1, customerid);
            ps.execute();

            return getCustomerCart(customerid);
        }
    }

    /**
     * Methode die die geänderte Anzahl züruckgibt
     *
     * @param articleid
     * @param cartid
     * @param amount
     * @return
     * @throws SQLException
     */
    public int updateCart(int articleid, int cartid, int amount) throws SQLException {
        //Anzahl der Cartarticles 
        PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*)"
                + " FROM cartarticle"
                + " WHERE articleid =? AND cartid =?;");
        ps.setInt(1, articleid);
        ps.setInt(2, cartid);
        ResultSet rs = ps.executeQuery();
        rs.next();

        //erneuert den Amount des cartarticles
        if (rs.getInt("count") == 1) {
            ps = conn.prepareStatement("SELECT amount FROM cartarticle WHERE articleid=? AND cartid=?;");
            ps.setInt(1, articleid);
            ps.setInt(2, cartid);

            rs = ps.executeQuery();
            rs.next();

            int currentamount = rs.getInt("amount") + amount;

            if (currentamount < 0) {
                return 0;
            }
            //updated den amount für das übergebene cartarticle
            ps = conn.prepareStatement("UPDATE cartarticle SET amount=? WHERE articleid=? AND cartid=?; ");
            ps.setInt(1, currentamount);
            ps.setInt(2, articleid);
            ps.setInt(3, cartid);

            ps.execute();
            return currentamount;

        } else {
            //erstellt ein neues cartarticle wenn keins vorhanden ist und übergibt den amount
            ps = conn.prepareStatement("INSERT INTO cartarticle(cartid,articleid,amount) VALUES(?,?,?)");
            ps.setInt(1, cartid);
            ps.setInt(2, articleid);
            ps.setInt(3, 0);

            ps.execute();
            return amount;
        }

    }

    /**
     * erstellt neue Order in der Datenbank
     *
     * @param customerid
     * @throws SQLException
     */
    public void insertOrderthing(int customerid) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("INSERT INTO orderthing(customerid) VALUES(?);");
        ps.setInt(1, customerid);
        ps.execute();

        //Statement um die orderid zu holen
        int orderid = -1;
        ps = conn.prepareStatement("SELECT orderid "
                + "FROM orderthing "
                + "WHERE customerid=? AND datetime=(SELECT MAX(datetime) "
                + "FROM orderthing "
                + "WHERE customerid=?);");
        ps.setInt(1, customerid);
        ps.setInt(2, customerid);

        ResultSet rs = ps.executeQuery();
        rs.next();
        orderid = rs.getInt("orderid");

        //Statement um die CartID zu bekommen
        int cartid = getCustomerCart(customerid);
        ps = conn.prepareStatement("SELECT * "
                + "FROM cartarticle "
                + "WHERE cartid=?; AND amount>0");
        ps.setInt(1, cartid);
        rs = ps.executeQuery();

        while (rs.next()) {
            //einfügen des artikels mit dem amount
            ps = conn.prepareStatement("INSERT INTO articleorder(articleid,orderid,amount) VALUES(?,?,?);");
            ps.setInt(1, rs.getInt("articleid"));
            ps.setInt(2, orderid);
            ps.setInt(3, rs.getInt("amount"));
            ps.execute();
        }
        //Löschen der cartarticle mit dem jeweiligen cartid
        ps = conn.prepareStatement("DELETE FROM cartarticle WHERE cartid=?");
        ps.setInt(1, cartid);
        ps.execute();

    }

    /**
     * Methode die die einzelnen Bestellungen zurückgibt
     *
     * @param customerid
     * @return
     * @throws SQLException
     */
    public ArrayList<Ordering> getOrders(int customerid) throws SQLException {
        ArrayList<Ordering> orders = new ArrayList();
        PreparedStatement ps = conn.prepareStatement("SELECT * from orderthing WHERE customerid=?;");
        ps.setInt(1, customerid);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Ordering order = new Ordering(rs.getInt("orderid"), customerid, rs.getTimestamp("datetime"));
            orders.add(order);
        }

        return orders;
    }

    /**
     * Methode um eine Liste der Artikel mit den BestellDetails zu bekommen
     *
     * @param orderid
     * @return
     * @throws SQLException
     */
    public ArrayList<OrderInformation> getOrderInformation(int orderid) throws SQLException {
        ArrayList<OrderInformation> informations = new ArrayList<>();
        PreparedStatement ps = conn.prepareStatement("SELECT a.articlename,a.price,ao.amount,a.articleid "
                + "FROM articleorder ao INNER JOIN article a ON ao.articleid= a.articleid "
                + "WHERE orderid=?;");
        ps.setInt(1, orderid);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            OrderInformation info = new OrderInformation(orderid, rs.getInt("articleid"), rs.getInt("price"), rs.getInt("amount"), rs.getString("articlename"));
            informations.add(info);
        }
        return informations;
    }
}
