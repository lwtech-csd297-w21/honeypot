package edu.lwtech.csd297.honeypot;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

// Honeypot Servlet -
//      http://<server>:8080/honeypot/servlet
//
// <Your Name>
// LWTech CSD297

@WebServlet(name = "honeypot", urlPatterns = {"/servlet"}, loadOnStartup = 0)
public class HoneypotServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;        // Unused

    // Add an init method here. It should do the following:
    //   1. Add a banner to the logfile whenever the servlet is started
    //   2. Read in the file "apache-index.html" and store it in a global string variable

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

        String ip = request.getRemoteAddr();
        String method = request.getMethod();
        String uri = request.getRequestURI();
        String userAgent = request.getHeader("User-Agent");
        if (userAgent == null)
            userAgent = "";
        String query = request.getQueryString();
        if (query == null)
            query = "";

        // Add code to add that information (with all of it on one line) to the logfile here

        try (ServletOutputStream out = response.getOutputStream()) {

            // Add code to send out the contents of the file "apache-index.html" here

        } catch (IOException e) {
            e.printStackTrace();        // Change this so that it logs the error correctly
        }
    }

    // Add a getServletInfo method here

    // Add a destroy method here that adds a different banner to the logfile whenever the servlet is shutdown

}
