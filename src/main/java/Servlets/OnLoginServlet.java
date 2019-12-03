/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import BL.Alpaca;
import BL.PassEncryption;
import DB.Database;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author vizug
 */
@WebServlet(name = "OnLoginServlet", urlPatterns = {"/OnLoginServlet"})
public class OnLoginServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response){
        response.setContentType("text/html;charset=UTF-8");
        PassEncryption passenc = new PassEncryption();
        String username = request.getParameter("username");
        String pw = passenc.encryptString(request.getParameter("pw"));

        String forward = "/shop.jsp";
        String errorstring = "";
        try {
            Database db = Database.getInstance();
            if (db.login(username, pw)) {
                Cookie c = new Cookie("userID", "" + db.getCustomerID(username));
                response.addCookie(c);
                ArrayList<Alpaca> alpacas = db.getCartItems(db.getCustomerCart(db.getCustomerID(username)), db.getItems(db.getCustomerCart(db.getCustomerID(username))));

                request.setAttribute("alpacas", alpacas);
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/shop.jsp");
                rd.forward(request, response);

            }
        } catch (Exception ex) {
//            StackTraceElement[] errors = ex.getStackTrace();
//            for (int i = 0; i < errors.length; i++) {
//                errorstring += errors[i].toString() + "\n";
//            }
//            forward = "/error.jsp";
        }
//        request.setAttribute("errors", errorstring);

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
        processRequest(request, response);
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
        processRequest(request, response);
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
