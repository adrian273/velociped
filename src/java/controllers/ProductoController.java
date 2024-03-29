/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import models.ProductModel;

/**
 *
 * @author adrian
 */
@MultipartConfig
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

    /**
     * 
     * @param request
     * @param response
     * @param id
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws ServletException 
     */
    protected void deleteProduct(HttpServletRequest request, HttpServletResponse response, String id) throws IOException, ClassNotFoundException, SQLException, ServletException {
        ProductModel pm = new ProductModel();
        boolean delete = pm.delete(id);
        if (delete == false) {
            //RequestDispatcher rq = request.getRequestDispatcher("producto?action=view");
            //rq.forward(request, response);
            response.sendRedirect("producto?action=view&msg=delete");
            //viewProduct(request, response);
        } else {
            response.sendRedirect("producto?action=view&msg=delete_fail");
        }
    }

    /**
     * 
     * @param request
     * @param response
     * @param id
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws ServletException 
     */
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
        switch (action) {
            case "view":
                try {
                    if (request.getParameter("msg") != null) {
                        String getMessage = request.getParameter("msg");
                        String color = "";
                        String message = "";
                        switch (getMessage) {
                            case "delete":
                                message = "Eliminado con exito!";
                                message += " <i class='fas fa-trash'></i>";
                                color = "danger";
                                break;
                            case "delete_fail":
                                message = "<b>[No Eliminado]</b> Este producto esta siendo usado!";
                                message += " <i class=\"far fa-dizzy\"></i>";
                                color = "danger";
                                break;
                            case "store":
                                message = "Agregado con exito!";
                                message += " <i class='fas fa-check'></i>";
                                color = "success";
                                break;
                            case "update":
                                message = "Guardado con exito!";
                                message += " <i class='fas fa-save'></i>";
                                color = "primary";
                                break;
                            default:
                                message = "Ninguna accion realizada!";
                                message += "";
                                color = "warning";
                                break;
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
                break;
            //processRequest(request, response);
            case "delete":
                try {
                    String id = request.getParameter("id");
                    deleteProduct(request, response, id);
                } catch (ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(ProductoController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "edit":
                String id = request.getParameter("id");
                try {
                    viewDetailProduct(request, response, id);
                } catch (ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(ProductoController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "prod":
                String category = "";
                category = request.getParameter("category");
                int categories_id = 0;
                switch (category) {
                    case "bicicletas":
                        categories_id = 1;
                        break;
                    case "accesorios":
                        categories_id = 2;
                        break;
                    case "componentes":
                        categories_id = 3;
                        break;
                    case "indumentaria":
                        categories_id = 4;
                        break;
                    default:
                        break;
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
        //processRequest(request, response);
        String type = request.getParameter("type");
        String name = request.getParameter("name");
        String slug = request.getParameter("slug");
        String price = "";
        price = request.getParameter("precio");
        String image = "";
        image = request.getParameter("image");
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
        } else if (type.equals("update")) {
            try {
                ProductModel pro = new ProductModel();
                String id = request.getParameter("id");
                boolean resp = pro.update(id, name, slug, description, price, image, visible, stock, categories_id, created_at);
                if (resp == false) {
                    response.sendRedirect("producto?action=view&msg=update");
                }
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
