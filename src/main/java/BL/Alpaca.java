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
public class Alpaca {
    private int id;
    private String typ;
    private int price;
    private int amount;

    public Alpaca(int id, String typ, int price,int amount) {
        this.id = id;
        this.typ = typ;
        this.price = price;
        this.amount=amount;
    }

    public int getId() {
        return id;
    }

    public int getPrice() {
        return price;
    }

    public String getTyp() {
        return typ;
    }
    
 
}
