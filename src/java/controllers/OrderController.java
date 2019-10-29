/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.AddressModel;
import models.OrderModel;

/**
 *
 * @author adrian
 */
public class OrderController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws java.lang.ClassNotFoundException
     * @throws java.sql.SQLException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String type = request.getParameter("type");

            switch (type) {
                case "checkout":
                    int orders_id = Integer.parseInt(String.valueOf(request.getParameter("orders_id")));
                    int shipping = Integer.parseInt(String.valueOf(request.getParameter("shipping")));
                    int payments_id = Integer.parseInt(String.valueOf(request.getParameter("payments_type")));
                    OrderModel order = new OrderModel();
                    boolean updated_order = order.updated(orders_id, shipping, payments_id);
                    if (updated_order == false) {
                        String region = request.getParameter("region");
                        String comuna = request.getParameter("comuna");
                        String num = request.getParameter("num");
                        String phone = request.getParameter("phone");
                        String adrs = request.getParameter("address");
                        AddressModel adress = new AddressModel();
                        boolean insert_adress = adress.insert(orders_id, region, comuna, adrs, num, phone);
                        if (insert_adress == false) {
                            HttpSession ses = request.getSession();
                            String id_user = (String) ses.getAttribute("id_user");
                            boolean insert_new_order = order.insert(id_user);
                            
                            if (insert_new_order == false) {
                                ResultSet order_row = order.getOrderByUser(id_user);
                                order_row.last();
                                //out.print(order_row.getString("id"));
                                ses.setAttribute("order_id", order_row.getString("id"));
                                out.print("success,"+ order_row.getString("id"));
                            } else {
                                out.print("fail_new_order");
                            }

                        } else {
                            out.print("fail_insert_adress");
                        }
                    } else {
                        out.print("fail_updated_order");
                    }
                    break;
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(OrderController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(OrderController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(OrderController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(OrderController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
