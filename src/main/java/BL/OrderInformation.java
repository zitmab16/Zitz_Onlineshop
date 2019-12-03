/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BL;

/**
 *
 * @author vizug
 */
public class OrderInformation {
    private int orderid;
    private int articleid;
    private int price;
    private int amount;
    private String name;

    /**
     * 
     * @param orderid
     * @param articleid
     * @param price
     * @param amount
     * @param name 
     * Klasse für das Anzeigen der OrderDetails
     */
    
    public OrderInformation(int orderid, int articleid, int price, int amount,String name) {
        this.orderid = orderid;
        this.articleid = articleid;
        this.price = price;
        this.amount = amount;
        this.name=name;
    }

    public int getAmount() {
        return amount;
    }

    public int getArticleid() {
        return articleid;
    }

    public int getOrderid() {
        return orderid;
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }
    

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setArticleid(int articleid) {
        this.articleid = articleid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
    
    
}
