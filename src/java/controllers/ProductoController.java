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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.ProductModel;

/**
 *
 * @author adrian
 */
public class ProductoController extends HttpServlet {

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

    /**
     * 
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    protected void viewProduct(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            ProductModel promdl = new ProductModel();
            ResultSet rs = promdl.getProduct(false, 0);
            request.setAttribute("product_list", rs);
            RequestDispatcher rq = request.getRequestDispatcher("/product/index.jsp");
            rq.forward(request, response);
        }
    }

    protected void deleteProduct(HttpServletRequest request, HttpServletResponse response, String id) throws IOException, ClassNotFoundException, SQLException, ServletException {
        ProductModel pm = new ProductModel();
        boolean delete = pm.delete(id);
        if (delete == false) {
            //RequestDispatcher rq = request.getRequestDispatcher("producto?action=view");
            //rq.forward(request, response);
            response.sendRedirect("producto?action=view&msg=delete");
            //viewProduct(request, response);
        }
    }

    protected void viewDetailProduct(HttpServletRequest request, HttpServletResponse response, String id) throws IOException, ClassNotFoundException, SQLException, ServletException {
        ProductModel pm = new ProductModel();
        ResultSet rs = pm.getProductById(id);
        request.setAttribute("data", rs);
        RequestDispatcher rq = request.getRequestDispatcher("product/edit.jsp");
        rq.forward(request, response);
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
        String action = "";
        action = request.getParameter("action");
        if (action.equals("view")) {
            try {
                if (request.getParameter("msg") != null) {
                    String getMessage = request.getParameter("msg");
                    String color = "";
                    String message = "";
                    if (getMessage.equals("delete")) {
                        message = "Eliminado con exito!";
                        message += " <i class='fas fa-trash'></i>";
                        color = "danger";
                    }
                    else if (getMessage.equals("store")) {
                        message = "Agregado con exito!";
                        message += " <i class='fas fa-check'></i>";
                        color = "success";
                    }
                    else if (getMessage.equals("update")) {
                       message = "Guardado con exito!";
                        message += " <i class='fas fa-save'></i>";
                        color = "primary"; 
                    }
                    request.setAttribute("msg", message);
                    request.setAttribute("color", color);
                }
                viewProduct(request, response);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ProductoController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(ProductoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (action.equals("delete")) {
            try {
                String id = request.getParameter("id");
                deleteProduct(request, response, id);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ProductoController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(ProductoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (action.equals("edit")) {
            String id = request.getParameter("id");
            try {
                viewDetailProduct(request, response, id);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ProductoController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(ProductoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if (action.equals("prod")) {
            String category = "";
            category = request.getParameter("category");
            int categories_id = 0;
            
            if (category.equals("bicicletas")) {
                categories_id = 1;
            } 
            else if (category.equals("accesorios")) {
                categories_id = 2;
            } 
            else if (category.equals("componentes")) {
                categories_id = 3;
            }
            else if (category.equals("indumentaria")) {
                categories_id = 4;
            }
            
            ProductModel promdl = null;
            try {
                promdl = new ProductModel();
                ResultSet proData = promdl.getProdByCategory(categories_id);
                
                request.setAttribute("data", proData);
                request.setAttribute("title", category);
                RequestDispatcher rq = request.getRequestDispatcher("/prod-category.jsp");
                rq.forward(request, response);
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(ProductoController.class.getName()).log(Level.SEVERE, null, ex);
            }
          
        }

        //processRequest(request, response);
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
        //processRequest(request, response);
        String type = request.getParameter("type");
        String name = request.getParameter("name");
        String slug = request.getParameter("slug");
        String price = "";
        price = request.getParameter("precio");
        String image = "";
        String stock = "";
        stock = request.getParameter("stock");
        String categories_id = request.getParameter("categories_id");
        String description = request.getParameter("description");
        String visible = request.getParameter("visible");

        SimpleDateFormat date_f = new SimpleDateFormat("yyyy-MM-dd");
        String created_at = date_f.format(new Date());
        if (type.equals("store")) {
            try {
                ProductModel pro = new ProductModel();
                boolean resp = pro.insert(name, slug, description, price, image, visible, stock, categories_id);
                if (resp == false) {
                    response.sendRedirect("producto?action=view&msg=store");
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ProductoController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(ProductoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if (type.equals("update")) {
            try {
                ProductModel pro = new ProductModel();
                String id = request.getParameter("id");
                boolean resp = pro.update(id, name, slug, description, price, image, visible, stock, categories_id, created_at);
                if (resp == false)
                    response.sendRedirect("producto?action=view&msg=update");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ProductoController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(ProductoController.class.getName()).log(Level.SEVERE, null, ex);
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
