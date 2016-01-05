package ru.stqa.selenium.legrc.runner;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import org.cyberneko.html.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.traversal.DocumentTraversal;
import org.w3c.dom.traversal.NodeFilter;
import org.w3c.dom.traversal.TreeWalker;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;

public class HtmlSuiteRunner {

  @Parameter(names = {"--suite", "--scenario", "-s"}, description = "Suite or scenario file to run")
  private String suiteOrScenario;

  @Parameter(names = {"--browser", "-b"}, description = "Browser type")
  private String browser;

  @Parameter(names = {"--baseurl", "-u"}, description = "Base URL")
  private String baseUrl;

  @Parameter(names = {"--report", "-r"}, description = "Report file")
  private String report;

  public static void main(String[] args) throws IOException, SAXException {
    HtmlSuiteRunner runner = new HtmlSuiteRunner();
    new JCommander(runner, args);
    runner.run();
  }

  private void run() throws IOException, SAXException {
    File toRun = new File(suiteOrScenario);

    DOMParser parser = new DOMParser();
    parser.parse(toRun.getAbsolutePath());
    Document doc = parser.getDocument();

    DocumentTraversal traversal = (DocumentTraversal) doc;
    TreeWalker walker = traversal.createTreeWalker(doc.getDocumentElement(), NodeFilter.SHOW_ALL, null, false);
    
  }

}
