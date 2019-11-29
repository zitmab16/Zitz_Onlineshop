/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import DB.Database;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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
@WebServlet(name = "CartUpdateServlet", urlPatterns = {"/CartUpdateServlet"})
public class CartUpdateServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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
        Gson gson = new Gson();
        Information info = gson.fromJson(new InputStreamReader(request.getInputStream()), Information.class);
        int customerid = -1;
        for (Cookie c : request.getCookies()) {
            if (c.getName().equals("userID")) {
                customerid = Integer.parseInt(c.getValue());
            }
        }

        try {
            Database db = Database.getInstance();
            int cartid = db.getCustomerCart(customerid);
            Information inf = null;

            int amount = db.updateCart(info.getArticleid(), cartid, info.getAmount());
            inf=new Information(info.getArticleid(), amount);
            String jsonAnswer = gson.toJson(inf);
            OutputStreamWriter osw = new OutputStreamWriter(response.getOutputStream());
            osw.write(jsonAnswer);
            osw.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
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

    private class Information {

        private int amount;
        private int articleid;

        public Information(int articleid, int amount) {
            this.amount = amount;
            this.articleid = articleid;
        }

        public int getAmount() {
            return amount;
        }

        public int getArticleid() {
            return articleid;
        }

    }
}
