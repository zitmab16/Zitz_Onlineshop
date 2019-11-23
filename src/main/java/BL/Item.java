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
public class Item {
    private int id;
    private int name;
    private int price;

    public Item(int id, int name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public int getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
    
}
