/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author adrian
 */
public class OrderModel {

    DBModel db;

    public OrderModel() throws ClassNotFoundException, SQLException {
        db = new DBModel();
    }

    public ResultSet getOrderByUser(String id_user) throws SQLException {
        String query = "SELECT * FROM orders WHERE users_id=" + id_user;
        ResultSet rs = db.result(query);
        return rs;
    }

    /**
     * Creacion de orden incial
     *
     * @param user_id
     * @return boolean
     * @throws SQLException
     */
    public boolean insert(String user_id) throws SQLException {
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //String created_at = sdf.format(new Date());
        String query = "INSERT INTO orders (subtotal, shipping, created_at, updated_at, users_id, status_order_id) "
                + "VALUES (0, 0, "
                + null + ", "
                + null + ", "
                + user_id + ","
                + 1 + ");";
        //System.out.println(query);
        //return false;
        return db.execute(query);
    }

}