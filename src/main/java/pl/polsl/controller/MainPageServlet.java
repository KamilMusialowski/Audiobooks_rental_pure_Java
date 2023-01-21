/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package pl.polsl.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import pl.polsl.model.Model;

/**
 * Main page servlet
 *
 * @author Kamil Musiałowski
 * @version 1.5
 */
@WebServlet(name = "MainPageServlet", urlPatterns = {"/MainPageServlet"})
public class MainPageServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods. Controls which options are aloowed to use for certain user.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        if (session.getAttribute("loggedUser") == null || (int) session.getAttribute("loggedUser") < 1) {
            response.setContentType("text/html;charset=UTF-8");
            try {
                ResultSet rs = ((Model) this.getServletContext().getAttribute("model")).
                        selectUserByLogInData(request.getParameter("Nickname"), request.getParameter("Password"));
                if (rs.first() == false) {
                    rs.close();
                    notSuccesfulLoggingIn(response);
                } else {
                    rs.first();
                    int userID = rs.getInt(1);
                    session.setAttribute("loggedUser", userID);
                    if (rs.getInt("STATUS") == 1 || rs.getInt("STATUS") == 2) {
                        userStandardPremiumMenu(response);
                    } else {
                        adminMenu(response);
                    }
                    rs.close();
                }
            } catch (Exception e) {
                notSuccesfulLoggingIn(response);
            }
        } else {
            int loggedUser = (int) session.getAttribute("loggedUser");
            response.setContentType("text/html;charset=UTF-8");
            Connection connection = (Connection) this.getServletContext().getAttribute("connection");
            try {
                int status = ((Model) this.getServletContext().getAttribute("model")).
                        selectUserStatusByID(Integer.toString(loggedUser));
                if (status == 1 || status == 2) {
                    userStandardPremiumMenu(response);
                } else {
                    adminMenu(response);
                }
            } catch (Exception e) {
                reportException(e, response);
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

    /**
     * Initializes apps Model and DB connection if it is not initialized yet.
     */
    @Override
    public void init() {
        Connection connection = (Connection) this.getServletContext().getAttribute("connection");
        if (connection == null) {
            Model model = new Model();
            connection = model.getConnection();
            this.getServletContext().setAttribute("model", model);
            this.getServletContext().setAttribute("connection", connection);
        }
    }
/**
 * Controls exception-handling process.
 * @param e Thrown exception.
 * @param response Servlet response.
 */
    private void reportException(Exception e, HttpServletResponse response) {
        try {
            PrintWriter out = response.getWriter();
            out.println("<html>\n<body>\n"
                    + "<h1>Not correct data</h1>\n"
                    + e.getMessage()
                    + "<button onclick=\"history.back()\">Go Back</button>"
                    + "</body>\n</html>");
        } catch (IOException f) {

        }
    }
/**
 * Displays information about unsuccesful loggin-in and let user try again.
 * @param response Servlet response.
 */
    private void notSuccesfulLoggingIn(HttpServletResponse response) {
        try {
            PrintWriter out = response.getWriter();
            out.println("<!DOCTYPE html>\n"
                    + "<html>\n"
                    + "    <head>\n"
                    + "        <title>Start Page</title>\n"
                    + "        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n"
                    + "    </head>\n"
                    + "    <body>\n"
                    + "        <h1>Audiobooks rental</h1>\n"
                    + "<h2>Uncorrect data</h2><br>Działa to tak i jest tak brzydkie, bo serwer nie chce załadować zmodyfikowanego index.html.<br>"
                    + "Wiedząc, jak dobrym i zaawansowanym IDE jest NetBeans (ta, jasne) to pewnie moja głupota. Niemniej, reszta apki działa.<br>"
                    + "Standard_User ... Standard_User<br>\n"
                    + "Premium ... Premium<br>\n"
                    + "Admin ... Admin<br>\n"
                    + "        <form action=\"MainPageServlet\">\n"
                    + "            <input type=\"text\" placeholder=\"Enter Username\" name=\"Nickname\" required/>\n"
                    + "            <input type=\"password\" placeholder=\"Enter Password\" name=\"Password\" required>\n"
                    + "            <input type=\"submit\" value=\"Log in\" />\n"
                    + "        </form>\n"
                    + "    </body>\n"
                    + "</html>\n");
        } catch (IOException e) {
            reportException(e, response);
        }
    }
/**
 * Displays menu page for standard and premium users.
 * @param response Servlet response.
 */
    private void userStandardPremiumMenu(HttpServletResponse response) {
        try {
            PrintWriter out = response.getWriter();
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Start Page</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Audiobooks rental</h1>");
            out.println("<form action=\"ShowAllAudiobooksServlet\">");
            out.println("<input type=\"submit\" value=\"Show all audiobooks\" />");
            out.println("</form>");
            out.println("<form action=\"ShowExactGenreMenuServlet\">");
            out.println("<input type=\"submit\" value=\"Show exact genre audiobooks\" />");
            out.println("</form>");
            out.println("<form action=\"LoggingOutServlet\">");
            out.println("<input type=\"submit\" value=\"Log out\" />");
            out.println("</form>");
            out.println("</body>");
            out.println("</html>");
        } catch (IOException e) {
            reportException(e, response);
        }
    }
/**
 * Displays menu page for admin.
 * @param response Servlet repsonse.
 */
    private void adminMenu(HttpServletResponse response) {
        try {
            PrintWriter out = response.getWriter();
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Start Page</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Audiobooks rental</h1>");
            out.println("<form action=\"ShowAllAudiobooksServlet\">");
            out.println("<input type=\"submit\" value=\"Show all audiobooks\" />");
            out.println("</form>");
            out.println("<form action=\"ShowExactGenreMenuServlet\">");
            out.println("<input type=\"submit\" value=\"Show exact genre audiobooks\" />");
            out.println("</form>");
            out.println("<form action=\"AddNewAudiobookServlet\">");
            out.println("<input type=\"submit\" value=\"Add audiobook\" />");
            out.println("</form>");
            out.println("<form action=\"RaportServlet\">");
            out.println("<input type=\"submit\" value=\"Raport\" />");
            out.println("</form>");
            out.println("<form action=\"LoggingOutServlet\">");
            out.println("<input type=\"submit\" value=\"Log out\" />");
            out.println("</form>");
            out.println("</body>");
            out.println("</html>");
        } catch (IOException e) {
            reportException(e, response);
        }
    }

}
