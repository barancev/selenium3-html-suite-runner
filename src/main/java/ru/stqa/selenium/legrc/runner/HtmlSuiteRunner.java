package ru.stqa.selenium.legrc.runner;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.google.common.collect.ImmutableMap;
import org.cyberneko.html.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import ru.stqa.selenium.legrc.runner.steps.AssertResult;
import ru.stqa.selenium.legrc.runner.steps.OpenStep;
import ru.stqa.selenium.legrc.runner.steps.UnsupportedCommandStep;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    Node table = getTableFromHtmlFile(toRun);
    Node id = table.getAttributes().getNamedItem("id");

    if (id == null) {
      HtmlScenario scenario = new HtmlScenario(toRun);
      initScenario(scenario, table);
      System.out.println(scenario);
    } else {
      HtmlSuite suite = new HtmlSuite(toRun);
      initSuite(suite, table);
      System.out.println(suite);
    }
  }

  private Node getTableFromHtmlFile(File htmlFile) throws IOException, SAXException {
    DOMParser parser = new DOMParser();
    parser.parse(htmlFile.getAbsolutePath());
    Document doc = parser.getDocument();

    return doc.getElementsByTagName("TABLE").item(0);
  }

  private Node findChildElement(Node node, String tagName) {
    if (node.getNodeType() == Node.ELEMENT_NODE && tagName.equals(node.getNodeName())) {
      return node;
    }

    Node child = node.getFirstChild();
    while(child != null) {
      if (child.getNodeType() == Node.ELEMENT_NODE) {
        if (tagName.equals(child.getNodeName())) {
          return child;
        } else {
          Node found = findChildElement(child, tagName);
          if (found != null) {
            return found;
          }
        }
      }
      child = child.getNextSibling();
    }
    return null;
  }

  private void initSuite(HtmlSuite suite, Node table) throws IOException, SAXException {
    Node tbody = findChildElement(table, "TBODY");
    Node row = tbody.getFirstChild();
    boolean firstRow = true;
    while (row != null) {
      if (row.getNodeType() == Node.ELEMENT_NODE) {
        if (firstRow) {
          // TODO: Suite name?
          firstRow = false;
        } else {
          File scenarioPath = suite.getFullPathToScenario(findChildElement(row, "A")
                  .getAttributes().getNamedItem("href").getNodeValue());
          HtmlScenario scenario = new HtmlScenario(scenarioPath);
          initScenario(scenario, getTableFromHtmlFile(scenarioPath));
          suite.addScenario(scenario);
        }
      }
      row = row.getNextSibling();
    }
  }

  private void initScenario(HtmlScenario scenario, Node table) {
    Node thead = findChildElement(table, "THEAD");
    scenario.setName(thead.getTextContent().trim());

    Node tbody = findChildElement(table, "TBODY");
    Node row = tbody.getFirstChild();
    while (row != null) {
      if (row.getNodeType() == Node.ELEMENT_NODE) {
        List<String> args = new ArrayList<String>();
        Node cell = row.getFirstChild();
        while (cell != null) {
          if (cell.getNodeType() == Node.ELEMENT_NODE) {
            args.add(cell.getTextContent().trim());
          }
          cell = cell.getNextSibling();
        }
        scenario.addStep(createStep(args));
      }
      row = row.getNextSibling();
    }
  }

  private Step createStep(List<String> args) {
    String command = args.get(0);
    String resultProcessor = null;
    Pattern p = Pattern.compile("(store|assert|verify|waitFor)(.*)");
    Matcher m = p.matcher(command);
    if (m.matches()) {
      resultProcessor = m.group(1);
      command = m.group(2);
    }

    Step.Factory factory = stepFactories.get(command);
    if (factory == null) {
      return new UnsupportedCommandStep(args);
    }

    Step step = factory.create(args);

    if (resultProcessor != null) {
      step = resultProcessorFactories.get(resultProcessor).wrap(step);
    }

    return step;
  }

  private Map<String, Step.Factory> stepFactories = new ImmutableMap.Builder<String, Step.Factory>()
          .put("open", new OpenStep.Factory())
          .build();

  private Map<String, ResultProcessor.Factory> resultProcessorFactories = new ImmutableMap.Builder<String, ResultProcessor.Factory>()
          .put("assert", new AssertResult.Factory())
          .build();
}
