package ru.spbu.math;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.http.HttpStatus;

public class SearchServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws IOException {

        PrintWriter out = resp.getWriter();

        String searchbox = req.getParameter("searchbox");
        if (searchbox == null || searchbox.isEmpty()) {
            out.println("No search parametr");
            return;
        }

        List<TestDocument> resultDocuments = SimpleIndexer.getInstance().search(searchbox);

        if (resultDocuments.isEmpty()) {
            out.println("No documents were found with " + searchbox + " parametr");
            return;
        }

        out.println("<html>");
        out.println("<head>");
        out.println("<title>Search result</title>");
        out.println("</head>");
        out.println("<body bgcolor=\"white\">");

        for (TestDocument document : resultDocuments) {
            out.println("<span>");
            out.println("<p>Title: " + document.getTitle() + "</p>");
            out.println("<p>Text: " + document.getText() + "</p>");
            out.println("</span>");

        }

        out.println("</body>");
        out.println("</html>");


    }
}
