package ru.stqa.selenium.legrc.runner;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.google.common.collect.ImmutableMap;
import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;
import org.cyberneko.html.parsers.DOMParser;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import ru.stqa.selenium.legrc.runner.steps.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlSuiteRunner implements RunContext {

  @Parameter(names = {"--suite", "--scenario", "-s"}, description = "Suite or scenario file to run", required = true)
  private String suiteOrScenario;

  @Parameter(names = {"--browser", "-b"}, description = "Browser type", required = true)
  private String browser;

  @Parameter(names = {"--baseurl", "-u"}, description = "Base URL", required = true)
  private String baseUrl;

  @Parameter(names = {"--report", "-r"}, description = "Report file")
  private String report;

  private WebDriver driver;
  private WebDriverBackedSelenium wdbs;
  private Map<String, String> vars = new HashMap<String, String>();

  @Override
  public void setDriver(WebDriver driver) {
    this.driver = driver;
    this.wdbs = new WebDriverBackedSelenium(driver, baseUrl);
  }

  @Override
  public WebDriver getDriver() {
    return driver;
  }

  @Override
  public WebDriverBackedSelenium getWDBS() {
    return wdbs;
  }

  @Override
  public long getTimeout() {
    return 30000;
  }

  @Override
  public void storeVar(String name, String value) {
    vars.put(name, value);
  }

  @Override
  public String substitute(String text) {
    StringBuffer sb = new StringBuffer();
    Pattern p = Pattern.compile("(\\$\\{\\w+\\})");
    Matcher m = p.matcher(text);
    while (m.find()) {
      String maybeVar = m.group(1);
      String varName = maybeVar.substring(2, maybeVar.length() - 1);
      if (vars.containsKey(varName)) {
        m.appendReplacement(sb, Matcher.quoteReplacement(vars.get(varName)));
      } else {
        m.appendReplacement(sb, Matcher.quoteReplacement(maybeVar));
      }
    }
    m.appendTail(sb);
    return sb.toString();
  }

  public static void main(String[] args) throws IOException, SAXException {
    HtmlSuiteRunner runner = new HtmlSuiteRunner();
    new JCommander(runner, args);
    runner.run();
  }

  private void run() throws IOException, SAXException {
    File toRun = new File(suiteOrScenario);

    Node table = getTableFromHtmlFile(toRun);
    Node id = table.getAttributes().getNamedItem("id");
    
    HtmlRunnable runnable;

    if (id == null) {
      HtmlScenario scenario = new HtmlScenario(toRun);
      initScenario(scenario, table);
      runnable = scenario;

    } else {
      HtmlSuite suite = new HtmlSuite(toRun);
      initSuite(suite, table);
      runnable = suite;
    }
    
    setDriver(createDriver(browser));
    runnable.run(this);
    //getDriver().quit();
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

    Pattern p2 = Pattern.compile("(.*)(AndWait)");
    Matcher m2 = p2.matcher(command);
    if (m2.matches()) {
      resultProcessor = m2.group(2);
      command = m2.group(1);
    }

    Step.Factory factory = stepFactories.get(command.toLowerCase());
    if (factory == null) {
      return new UnsupportedCommandStep(args);
    }

    Step step = factory.create(args);

    if (resultProcessor != null) {
      step = resultProcessorFactories.get(resultProcessor.toLowerCase()).wrap(step, args);
    }

    return step;
  }

  private Map<String, Step.Factory> stepFactories = new ImmutableMap.Builder<String, Step.Factory>()
          .put("open", new OpenStep.Factory())
          .put("click", new ClickStep.Factory())
          .put("csscount", new CssCountStep.Factory())
          .put("attribute", new AttributeStep.Factory())
          .put("elementpresent", new ElementPresentStep.Factory())
          .put("elementnotpresent", new ElementNotPresentStep.Factory())
          .put("eval", new EvalStep.Factory())
          .put("text", new TextStep.Factory())
          .put("type", new TypeStep.Factory())
          .put("value", new ValueStep.Factory())
          .put("xpathcount", new XpathCountStep.Factory())
          .build();

  private Map<String, ResultProcessor.Factory> resultProcessorFactories = new ImmutableMap.Builder<String, ResultProcessor.Factory>()
          .put("assert", new AssertResult.Factory())
          .put("verify", new VerifyResult.Factory())
          .put("store", new StoreResult.Factory())
          .put("waitfor", new WaitForResult.Factory())
          .put("andwait", new AndWaitResult.Factory())
          .build();

  private WebDriver createDriver(String browser) {
    if (browser.equals(BrowserType.FIREFOX)) {
      return new FirefoxDriver();
    } else if (browser.equals(BrowserType.CHROME)) {
      return new ChromeDriver();
    } else if (browser.equals(BrowserType.IE)) {
      return new InternetExplorerDriver();
    } else {
      return null;
    }
  }

}
