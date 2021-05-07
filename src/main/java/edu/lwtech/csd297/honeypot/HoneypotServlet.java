package edu.lwtech.csd297.honeypot;

import java.io.*;
import java.nio.file.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import org.apache.logging.log4j.*;

// Honeypot Servlet -
//      http://<server>:8080/honeypot/servlet
//
// Chip Anderson
// LWTech CSD297

@WebServlet(name = "honeypot", urlPatterns = "/servlet", loadOnStartup = 0)
public class HoneypotServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;        // Unused
    private static final Logger logger = LogManager.getLogger(HoneypotServlet.class);

    private static final String SERVLET_NAME = "honeypot";
    private static final String RESOURCES_DIR = "/WEB-INF/classes";

    private String apachePage = "";

    // Add an init method here. It should do the following:
    //   1. Add a banner to the logfile whenever the servlet is started
    //   2. Read in the file "apache-index.html" and store it in a global string variable
    @Override
    public void init(ServletConfig config) throws ServletException {
        logger.warn("");
        logger.warn("===========================================================");
        logger.warn("       " + SERVLET_NAME + " init() started");
        logger.warn("            http://localhost");
        logger.warn("===========================================================");
        logger.warn("");

        String resourcesDir = config.getServletContext().getRealPath(RESOURCES_DIR);
        logger.info("resourcesDir = {}", resourcesDir);

        logger.info("Reading Apache index page...");
        String fullTemplateFilename = resourcesDir + "/templates/apache-index.html";
        apachePage = readTemplateFile(fullTemplateFilename);

        logger.warn("");
        logger.warn("Initialization completed successfully!");
        logger.warn("");
    }

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
        logger.info("{} | {} {} | {}", ip, method, uri, userAgent);

        try (ServletOutputStream out = response.getOutputStream()) {

            // Add code to send out the contents of the file "apache-index.html" here
            out.println(apachePage);

        } catch (IOException e) {
            logger.error("I/O Exception writing out the web page", e);     // Change this so that it logs the error correctly
        }
    }

    // Add a doPost method to just called doGet()
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        doGet(request, response);
    }

    // Add a getServletInfo method here
    @Override
    public String getServletInfo() {
        return SERVLET_NAME + " Servlet";
    }

    // Add a destroy method here that adds a different banner to the logfile whenever the servlet is shutdown
    @Override
    public void destroy() {
        logger.warn("");
        logger.warn("-----------------------------------------");
        logger.warn("  " + SERVLET_NAME + " destroy() completed!");
        logger.warn("-----------------------------------------");
    }

    // --------------------------------------------------------------------

    private String readTemplateFile(String fileName) throws UnavailableException {
        String contents = "";
        try {
            contents = new String(Files.readAllBytes(Paths.get(fileName)));
        } catch (IOException ex) {
            String msg = "Unable to read " + fileName;
            logger.fatal(msg, ex);
            throw new UnavailableException(msg);
        }
        return contents;
    }

}