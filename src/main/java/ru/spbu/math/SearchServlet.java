package ru.spbu.math;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.eclipse.jetty.http.HttpStatus;

public class SearchServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(SearchServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws IOException {

        PrintWriter out = resp.getWriter();

        String searchbox = req.getParameter("searchbox");
        if (searchbox == null) {
            out.println("No search parameter");
            logger.error("No search parameter");
            return;
        }

        String[] searchWords = searchbox.split("\\s+");

        logger.info("Start searching " + searchbox);
        if (searchWords.length == 0) {
            out.println("Empty search parameter");
            logger.debug("Empty search parameter");
            return;
        }

        List<TestDocument> resultDocuments = getDocumentsBySearchWords(searchWords);


        if (resultDocuments.isEmpty()) {
            printNoFoundDocumentsMessage(out, searchbox);
            logger.debug("Found 0 documents");
        } else {
            printFoundDocuments(out, resultDocuments);
            logger.debug("Found " + resultDocuments.size() + " documents");
        }

    }

    private List<TestDocument> getDocumentsBySearchWords(String[] searchWords) {
        List<TestDocument> resultDocuments = SimpleIndexer.getInstance().search(searchWords[0]);

        for (int i = 1; i < searchWords.length; ++i) {
            if (resultDocuments.isEmpty()) break;

            resultDocuments = SimpleIndexer.getInstance().search(resultDocuments, searchWords[i]);
        }

        return resultDocuments;
    }

    private void printNoFoundDocumentsMessage(PrintWriter out, String searchbox) {
        out.println("No documents were found with " + searchbox + " parametr");
    }

    private void printFoundDocuments(PrintWriter out, List<TestDocument> resultDocuments) {
//        out.println("<html>");
//        out.println("<head>");
//        out.println("<title>Search result</title>");
//        out.println("</head>");
//        out.println("<body bgcolor=\"white\">");

        for (TestDocument document : resultDocuments) {
            out.println("<span>");
            out.println("<p>Title: " + document.getTitle() + "</p>");
            out.println("<p>Text: " + document.getText() + "</p>");
            out.println("</span>");

        }

//        out.println("</body>");
//        out.println("</html>");
    }
}
