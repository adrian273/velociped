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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.UserModel;

/**
 *
 * @author adrian
 */
public class UserController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

        }
    }
    
    protected void viewGridUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            UserModel user = new UserModel();
            ResultSet rs = user.getAll();
            request.setAttribute("data", rs);
            RequestDispatcher rq = request.getRequestDispatcher("/user/");
            rq.forward(request, response);
        }
    }

    /**
     *
     * @param request
     * @param response
     * @param id
     * @throws ServletException
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    protected void viewInfoUser(HttpServletRequest request, HttpServletResponse response, String id)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            HttpSession ses = request.getSession();
            String id_auth = (String) ses.getAttribute("id_user");
            String type = (String) ses.getAttribute("typeProfile");
            
            if (id.equals(id_auth) || type.equals("admin")) {
                UserModel userData = new UserModel();
                ResultSet rs = userData.getUserById(id);
                ArrayList row = new ArrayList<>();
                rs.first();

                row.add(rs.getString("name"));
                row.add(rs.getString("email"));
                row.add(rs.getString("user"));
                row.add(rs.getString("password"));
                row.add(rs.getString("adress"));

                request.setAttribute("data", row);
                RequestDispatcher rq = request.getRequestDispatcher("/user/info.jsp");
                rq.forward(request, response);
            } else {
                RequestDispatcher rq = request.getRequestDispatcher("/errors/error_401.jsp");
                rq.forward(request, response);
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
        //processRequest(request, response);
        String view = request.getParameter("view");
        switch (view) {
            case "create":
                response.sendRedirect("user/create.jsp");
                break;
            case "info":
                String id = request.getParameter("id");
                try {
                    viewInfoUser(request, response, id);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
                }   break;
            case "grid":
        {
            try {
                viewGridUser(request, response);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
                break;
            default:
                break;
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

        String type = request.getParameter("type");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String username = request.getParameter("user");
        String pass = request.getParameter("pass");
        String adress = request.getParameter("adress");
        // ---------------------------------------------------------------------
        // para crear nuevos registros
        // ---------------------------------------------------------------------
        if (type.equals("create")) {
            UserModel userData = null;
            try {
                userData = new UserModel();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                boolean insert = userData.insert(name, email, username, pass, adress);
                if (insert == false) {
                    response.sendRedirect("index.jsp");
                }
            } catch (SQLException ex) {
                Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            }
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
