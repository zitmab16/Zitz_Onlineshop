/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BL;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 *
 * @author vizug
 */
public class Ordering {
    private int id;
    private int customerID;
    private Timestamp datetime;

    public Ordering(int id, int customerID, Timestamp datetime) {
        this.id = id;
        this.customerID = customerID;
        this.datetime = datetime;
    }

    public int getCustomerID() {
        return customerID;
    }

    public Timestamp getDatetime() {
        return datetime;
    }

    public int getId() {
        return id;
    }
    
    
    
}
