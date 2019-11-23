/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BL;

import java.time.LocalDateTime;

/**
 *
 * @author vizug
 */
public class Ordering {
    private int id;
    private int customerID;
    private LocalDateTime datetime;

    public Ordering(int id, int customerID, LocalDateTime datetime) {
        this.id = id;
        this.customerID = customerID;
        this.datetime = datetime;
    }

    public int getCustomerID() {
        return customerID;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public int getId() {
        return id;
    }
    
    
    
}
