package ru.stqa.selenium.legrc.runner;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.google.common.collect.ImmutableMap;
import com.thoughtworks.selenium.Selenium;
import org.cyberneko.html.parsers.DOMParser;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.internal.BuildInfo;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import ru.stqa.selenium.legrc.runner.steps.*;
import ru.stqa.selenium.legrc.runner.steps.accessors.*;
import ru.stqa.selenium.legrc.runner.steps.actions.*;

import java.io.File;
import java.io.FileWriter;
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

  @Parameter(names = {"--report", "-r"}, description = "Report file", required = true)
  private String report;

  @Parameter(names = {"--overwrite", "-o"}, description = "Overwrite report file")
  private boolean overwriteReport;

  private WebDriver driver;
  private WebDriverBackedSelenium wdbs;
  private Map<String, StepOutcome> vars = new HashMap<String, StepOutcome>();

  @Override
  public void setDriver(WebDriver driver) {
    this.driver = driver;
    this.wdbs = new WebDriverBackedSelenium(driver, baseUrl);
  }

  @Override
  public WebDriver getDriver() {
    return driver;
  }

  @SuppressWarnings("deprecation")
  @Override
  public Selenium getSelenium() {
    return wdbs;
  }

  @Override
  public long getTimeout() {
    return 5000;
  }

  @Override
  public void storeVar(String name, StepOutcome value) {
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
        m.appendReplacement(sb, Matcher.quoteReplacement(vars.get(varName).toString()));
      } else {
        m.appendReplacement(sb, Matcher.quoteReplacement(maybeVar));
      }
    }
    m.appendTail(sb);
    return sb.toString();
  }

  public static void main(String[] args) throws IOException, SAXException {
    HtmlSuiteRunner runner = new HtmlSuiteRunner();
    JCommander cli = new JCommander(runner);
    try {
      cli.parse(args);
    } catch (ParameterException ex) {
      cli.usage();
      return;
    }
    runner.run();
  }

  private void run() throws IOException, SAXException {
    File reportFile = new File(report);
    if (reportFile.exists() && !overwriteReport) {
      System.out.println("Report file already exists: " + reportFile);
      System.out.println("If you want to overwrite existing report file use -o option");
      return;
    }

    File toRun = new File(suiteOrScenario);
    Node table = getTableFromHtmlFile(toRun);
    Node id = table.getAttributes().getNamedItem("id");
    
    HtmlRunnable runnable;

    if (id == null) {
      HtmlScenario scenario = new HtmlScenario(suiteOrScenario);
      initScenario(scenario, table);
      runnable = scenario;

    } else {
      HtmlSuite suite = new HtmlSuite(suiteOrScenario);
      initSuite(suite, table);
      runnable = suite;
    }

    setDriver(createDriver(browser));
    try {
      runnable.run(this);
    } finally {
      getDriver().quit();
    }
    generateReport(runnable);
  }

  private Node getTableFromHtmlFile(File htmlFile) throws IOException, SAXException {
    DOMParser parser = new DOMParser();
    parser.parse(htmlFile.toURI().toString());
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
          String scenarioRef = findChildElement(row, "A").getAttributes().getNamedItem("href").getNodeValue();
          HtmlScenario scenario = new HtmlScenario(scenarioRef);
          File scenarioPath = suite.getFullPathToScenario(scenarioRef);
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
      } else if (row.getNodeType() == Node.COMMENT_NODE) {
        scenario.addStep(new CommentStep(row.getTextContent()));
      }
      row = row.getNextSibling();
    }
  }

  private Step createStep(List<String> args) {
    String command = args.get(0).toLowerCase();

    if ("waitforpagetoload".equals(command.toLowerCase())) {
      return new WaitForPageToLoadStep.Factory().create(args);
    } else if ("waitforpopup".equals(command.toLowerCase())) {
      return new WaitForPopUpStep.Factory().create(args);
    } else if ("waitforframetoload".equals(command.toLowerCase())) {
      return new WaitForFrameToLoadStep.Factory().create(args);
    } else if ("waitforcondition".equals(command.toLowerCase())) {
      return new WaitForConditionStep.Factory().create(args);
    } else if ("store".equals(command.toLowerCase())) {
      return new ExpressionStep.Factory().create(args);
    }

    String resultProcessor = null;
    Pattern p = Pattern.compile("(store|assert|verify|waitfor)(.*)");
    Matcher m = p.matcher(command);
    if (m.matches()) {
      resultProcessor = m.group(1);
      command = m.group(2);
    }

    Pattern p2 = Pattern.compile("(.*)(andwait)");
    Matcher m2 = p2.matcher(command);
    if (m2.matches()) {
      resultProcessor = m2.group(2);
      command = m2.group(1);
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
          .put("addlocationstrategy", new AddLocationStrategyStep.Factory())
          .put("addscript", new AddScriptStep.Factory())
          .put("addselection", new AddSelectionStep.Factory())
          .put("alert", new AlertStep.Factory())
          .put("alertpresent", new AlertPresentStep.Factory())
          .put("alertnotpresent", new AlertNotPresentStep.Factory())
          .put("allownativexpath", new AllowNativeXpathStep.Factory())
          .put("altkeydown", new AltKeyDownStep.Factory())
          .put("altkeyup", new AltKeyUpStep.Factory())
          .put("answeronnextprompt", new AnswerOnNextPromptStep.Factory())
          .put("assignid", new AssignIdStep.Factory())
          .put("attribute", new AttributeStep.Factory())
          .put("attributefromallwindows", new AttributeFromAllWindowsStep.Factory())
          .put("bodytext", new BodyTextStep.Factory())
          .put("captureentirepagescreenshot", new CaptureEntirePageScreenshot.Factory())
          .put("checked", new CheckedStep.Factory())
          .put("check", new CheckStep.Factory())
          .put("choosecancelonnextconfirmation", new ChooseCancelOnNextConfirmationStep.Factory())
          .put("chooseokonnextconfirmation", new ChooseOkOnNextConfirmationStep.Factory())
          .put("close", new CloseStep.Factory())
          .put("click", new ClickStep.Factory())
          .put("clickat", new ClickAtStep.Factory())
          .put("confirmation", new ConfirmationStep.Factory())
          .put("confirmationpresent", new ConfirmationPresentStep.Factory())
          .put("confirmationnotpresent", new ConfirmationNotPresentStep.Factory())
          .put("contextmenu", new ContextMenuStep.Factory())
          .put("contextmenuat", new ContextMenuAtStep.Factory())
          .put("controlkeydown", new ControlKeyDownStep.Factory())
          .put("controlkeyup", new ControlKeyUpStep.Factory())
          .put("cookie", new CookieStep.Factory())
          .put("cookiebyname", new CookieByNameStep.Factory())
          .put("cookiepresent", new CookiePresentStep.Factory())
          .put("cookienotpresent", new CookieNotPresentStep.Factory())
          .put("createcookie", new CreateCookieStep.Factory())
          .put("csscount", new CssCountStep.Factory())
          .put("cursorposition", new CursorPositionStep.Factory())
          .put("deleteallvisiblecookies", new DeleteAllVisibleCookiesStep.Factory())
          .put("deletecookie", new DeleteCookieStep.Factory())
          .put("deselectpopup", new DeselectPopUpStep.Factory())
          .put("doubleclick", new DoubleClickStep.Factory())
          .put("doubleclickat", new DoubleClickAtStep.Factory())
          .put("draganddrop", new DragAndDropStep.Factory())
          .put("draganddroptoobject", new DragAndDropToObjectStep.Factory())
          .put("dragdrop", new DragDropStep.Factory())
          .put("echo", new EchoStep.Factory())
          .put("editable", new EditableStep.Factory())
          .put("elementheight", new ElementHeightStep.Factory())
          .put("elementpresent", new ElementPresentStep.Factory())
          .put("elementnotpresent", new ElementNotPresentStep.Factory())
          .put("elementpositionleft", new ElementPositionLeftStep.Factory())
          .put("elementpositiontop", new ElementPositionTopStep.Factory())
          .put("elementwidth", new ElementWidthStep.Factory())
          .put("eval", new EvalStep.Factory())
          .put("expression", new ExpressionStep.Factory())
          .put("fireevent", new FireEventStep.Factory())
          .put("focus", new FocusStep.Factory())
          .put("goback", new GoBackStep.Factory())
          .put("highlight", new HighlightStep.Factory())
          .put("htmlsource", new HtmlSourceStep.Factory())
          .put("ignoreattributeswithoutvalue", new IgnoreAttributesWithoutValueStep.Factory())
          .put("keydown", new KeyDownStep.Factory())
          .put("keypress", new KeyPressStep.Factory())
          .put("keyup", new KeyUpStep.Factory())
          .put("location", new LocationStep.Factory())
          .put("metakeydown", new MetaKeyDownStep.Factory())
          .put("metakeyup", new MetaKeyUpStep.Factory())
          .put("mousedown", new MouseDownStep.Factory())
          .put("mousedownright", new MouseDownRightStep.Factory())
          .put("mousedownat", new MouseDownAtStep.Factory())
          .put("mousedownrightat", new MouseDownRightAtStep.Factory())
          .put("mousemove", new MouseMoveStep.Factory())
          .put("mousemoveat", new MouseMoveAtStep.Factory())
          .put("mouseout", new MouseOutStep.Factory())
          .put("mouseover", new MouseOverStep.Factory())
          .put("mousespeed", new MouseSpeedStep.Factory())
          .put("mouseup", new MouseUpStep.Factory())
          .put("mouseupright", new MouseUpRightStep.Factory())
          .put("mouseupat", new MouseUpAtStep.Factory())
          .put("mouseuprightat", new MouseUpRightAtStep.Factory())
          .put("open", new OpenStep.Factory())
          .put("openwindow", new OpenWindowStep.Factory())
          .put("ordered", new OrderedStep.Factory())
          .put("pause", new PauseStep.Factory())
          .put("prompt", new PromptStep.Factory())
          .put("promptpresent", new PromptPresentStep.Factory())
          .put("promptnotpresent", new PromptNotPresentStep.Factory())
          .put("refresh", new RefreshStep.Factory())
          .put("removeallselections", new RemoveAllSelectionsStep.Factory())
          .put("removescript", new RemoveScriptStep.Factory())
          .put("removeselection", new RemoveSelectionStep.Factory())
          .put("rollup", new RollupStep.Factory())
          .put("runscript", new RunScriptStep.Factory())
          .put("table", new TableStep.Factory())
          .put("text", new TextStep.Factory())
          .put("textpresent", new TextPresentStep.Factory())
          .put("textnotpresent", new TextNotPresentStep.Factory())
          .put("title", new TitleStep.Factory())
          .put("type", new TypeStep.Factory())
          .put("typekeys", new TypeKeysStep.Factory())
          .put("select", new SelectStep.Factory())
          .put("selectedid", new SelectedIdStep.Factory())
          .put("selectedids", new SelectedIdsStep.Factory())
          .put("selectedindex", new SelectedIndexStep.Factory())
          .put("selectedindexes", new SelectedIndexesStep.Factory())
          .put("selectedlabel", new SelectedLabelStep.Factory())
          .put("selectedlabels", new SelectedLabelsStep.Factory())
          .put("selectedvalue", new SelectedValueStep.Factory())
          .put("selectedvalues", new SelectedValuesStep.Factory())
          .put("selectframe", new SelectFrameStep.Factory())
          .put("selectoptions", new SelectOptionsStep.Factory())
          .put("selectpopup", new SelectPopUpStep.Factory())
          .put("selectwindow", new SelectWindowStep.Factory())
          .put("sendkeys", new SendKeysStep.Factory())
          .put("setbrowserloglevel", new SetBrowserLogLevelStep.Factory())
          .put("setcursorposition", new SetCursorPositionStep.Factory())
          .put("setspeed", new SetSpeedStep.Factory())
          .put("setmousespeed", new SetMouseSpeedStep.Factory())
          .put("settimeout", new SetTimeoutStep.Factory())
          .put("shiftkeydown", new ShiftKeyDownStep.Factory())
          .put("shiftkeyup", new ShiftKeyUpStep.Factory())
          .put("somethingselected", new SomethingSelectedStep.Factory())
          .put("speed", new SpeedStep.Factory())
          .put("storeallbuttons", new AllButtonsStep.Factory())
          .put("storeallfields", new AllFieldsStep.Factory())
          .put("storealllinks", new AllLinksStep.Factory())
          .put("storeallwindowids", new AllWindowIdsStep.Factory())
          .put("storeallwindownames", new AllWindowNamesStep.Factory())
          .put("storeallwindowtitles", new AllWindowTitlesStep.Factory())
          .put("submit", new SubmitStep.Factory())
          .put("uncheck", new UncheckStep.Factory())
          .put("usexpathlibrary", new UseXpathLibraryStep.Factory())
          .put("value", new ValueStep.Factory())
          .put("visible", new VisibleStep.Factory())
          .put("whetherthisframematchframeexpression", new WhetherThisFrameMatchFrameExpressionStep.Factory())
          .put("whetherthiswindowmatchwindowexpression", new WhetherThisWindowMatchWindowExpressionStep.Factory())
          .put("windowfocus", new WindowFocusStep.Factory())
          .put("windowmaximize", new WindowMaximizeStep.Factory())
          .put("xpathcount", new XpathCountStep.Factory())
          .build();

  private Map<String, StepWrapper.Factory> resultProcessorFactories = new ImmutableMap.Builder<String, StepWrapper.Factory>()
          .put("assert", new AssertResult.Factory())
          .put("assertnot", new AssertNotResult.Factory())
          .put("verify", new VerifyResult.Factory())
          .put("store", new StoreResult.Factory())
          .put("waitfor", new WaitForResult.Factory())
          .put("waitfornot", new WaitForNotResult.Factory())
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

  private void generateReport(HtmlRunnable runnable) throws IOException {
    StringBuilder sb = new StringBuilder();
    sb.append("<html>\n<head>\n<style type='text/css'>\n");
    sb.append("table {\n" +
            "    border-collapse: collapse;\n" +
            "    border: 1px solid #ccc;\n" +
            "}\n");
    sb.append("th, td {\n" +
            "    padding-left: 0.3em;\n" +
            "    padding-right: 0.3em;\n" +
            "    text-align: left;\n" +
            "}\n");
    sb.append("th {\n" +
            "    background-color: #e5e5ff;\n" +
            "}\n");
    sb.append(".status_done {\n" +
            "    background-color: #eeffee;\n" +
            "}\n");
    sb.append(".status_passed {\n" +
            "    background-color: #ccffcc;\n" +
            "}\n");
    sb.append(".status_failed {\n" +
            "    background-color: #ffcccc;\n" +
            "}\n");
    sb.append("tr.comment {\n" +
            "    color: #0000ff;\n" +
            "    background-color: #ffffff;\n" +
            "}\n");
    sb.append("</style>\n</head>\n");
    sb.append("<body>\n");
    sb.append("<h1>Test Run Report</h1>\n");
    Capabilities caps = ((RemoteWebDriver) driver).getCapabilities();
    sb.append(String.format("<p>Driver info: browser=%s, version=%s, platform=%s</p>\n",
            caps.getBrowserName(), caps.getVersion(), caps.getPlatform()));
    sb.append(runnable.toHtml());
    sb.append(String.format("<p>Selenium version: %s</p>\n", new BuildInfo().getReleaseLabel()));
    sb.append("</body>\n</html>");
    FileWriter reportWriter = new FileWriter(new File(report));
    try {
      reportWriter.write(sb.toString());
    } finally {
      reportWriter.close();
    }
  }

}
