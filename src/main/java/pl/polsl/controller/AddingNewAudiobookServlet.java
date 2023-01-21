/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package pl.polsl.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import pl.polsl.model.Audiobook;
import pl.polsl.model.Genre;
import pl.polsl.model.Model;
import pl.polsl.model.OwnException;

/**
 *
 * Servlet which controls adding audiobook process.
 *
 * @author Kamil Musiałowski
 * @version 1.5
 */
@WebServlet("/AddingNewAudiobookServlet")
public class AddingNewAudiobookServlet extends HttpServlet {

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
        if ("".equals(request.getParameter("newPrice")) || "".equals(request.getParameter("newTitle"))) {
            incorrectData(response);
        } else {
            try {
                addingProcess(request, response);
            } catch (OwnException e) {
                incorrectData(response);
            }
        }
    }
/**
 * Displays exception-handlig page.
 * @param response servlet response
 * @throws IOException Throws exception.
 */
    protected void incorrectData(HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        out.println("<html>\n<body>\n"
                + "<h1>Not correct data</h1>\n"
                + "<form action=\"MainPageServlet\">"
                + "<input type=\"submit\" value=\"Main menu\">\n"
                + "</form>\n"
                + "Audiobooks price must be greater or equal to 0 and audiobook must have title."
                + "<button onclick=\"history.back()\">Go Back</button>"
                + "</body>\n</html>");
    }

    /**
     * Displays adding process info-status, number of added audiobooks (in
     * session) and info about them.
     *
     * @param response servlet response
     * @param addedAudiobooksNum number of added audiobooks
     * @param addedAudiobooks list of added audiobooks
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private void audiobooksAddedInSessionDisplay(HttpServletResponse response, int addedAudiobooksNum, List<Audiobook> addedAudiobooks)
            throws ServletException, IOException {
        String s = addedAudiobooks.stream().map(e -> e.toString()).reduce("", String::concat);
        PrintWriter out = response.getWriter();
        out.println("<html>\n<body>\n"
                + "<h1>Done</h1>\n"
                + "In this session you added " + String.valueOf(addedAudiobooksNum++) + " audiobook(s) (cookies):<br>"
                + s
                + "<form action=\"MainPageServlet\">"
                + "<input type=\"submit\" value=\"Main menu\">\n"
                + "</form>\n"
                + "</body>\n</html>");
    }

    /**
     * Controls the audiobook adding process.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private void addingProcess(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, OwnException {
        try {
            String author = request.getParameter("newAuthor");
            String title = request.getParameter("newTitle");
            String genre = request.getParameter("genre");
            Double price = Double.valueOf(request.getParameter("newPrice"));
            ((Model) this.getServletContext().getAttribute("model")).insertAudiobook(title, author, genre, price);

            Cookie[] cookies = request.getCookies();
            int addedAudiobooksNum = 0;
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("addedAudiobooksNumber")) {
                        addedAudiobooksNum = Integer.parseInt(cookie.getValue());
                        break;
                    }
                }
            }
            addedAudiobooksNum++;
            Cookie cookie = new Cookie("addedAudiobooksNumber", String.valueOf(addedAudiobooksNum));
            response.addCookie(cookie);
            List<Audiobook> addedAudiobooks;
            HttpSession session = request.getSession(true);
            Object obj = session.getAttribute("addedAudiobooks");
            if (obj == null) {
                addedAudiobooks = new ArrayList();
            } else {
                addedAudiobooks = (List<Audiobook>) obj;
            }
            Audiobook added = new Audiobook(author, title, Genre.valueOf(genre.toUpperCase()), price);
            addedAudiobooks.add(added);
            session.setAttribute("addedAudiobooks", addedAudiobooks);
            audiobooksAddedInSessionDisplay(response, addedAudiobooksNum, addedAudiobooks);

        } catch (SQLException e) {
            PrintWriter out = response.getWriter();
            out.println("<html>\n<body>\n"
                    + "<h1>Not correct data</h1>\n"
                    + e.getMessage()
                    + "<button onclick=\"history.back()\">Go Back</button>"
                    + "</body>\n</html>");
        }
    }

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
}
