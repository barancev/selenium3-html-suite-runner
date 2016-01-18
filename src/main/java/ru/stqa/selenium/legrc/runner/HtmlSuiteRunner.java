package ru.stqa.selenium.legrc.runner;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.internal.BuildInfo;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import ru.stqa.selenium.legrc.runner.steps.StepFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class HtmlSuiteRunner {

  private CliOptions options = new CliOptions();
  private RunContext ctx = new DefaultRunContext();
  private StepFactory stepFactory = new StepFactory();
  private HtmlRunnable runnable;

  public static void main(String[] args) throws IOException, SAXException {
    HtmlSuiteRunner runner = new HtmlSuiteRunner(args);
    if (runner.prepareEnv() && runner.prepareRunnable() && runner.prepareDriver()) {
      runner.run();
    }
    runner.generateReport();
  }

  public HtmlSuiteRunner(String[] args) {
    options.parse(args);
  }

  private boolean prepareEnv() {
    ctx.setBaseUrl(options.baseUrl);

    File reportFile = new File(options.report);
    if (reportFile.exists() && !options.overwriteReport) {
      System.out.println("Report file already exists: " + reportFile);
      System.out.println("If you want to overwrite existing report file use -o option");
      return false;
    }
    return true;
  }

  private boolean prepareRunnable() throws IOException, SAXException {
    File toRun = new File(options.suiteOrScenario);
    Node table = HtmlParserUtils.getTableFromHtmlFile(toRun);
    Node id = table.getAttributes().getNamedItem("id");

    if (id == null) {
      HtmlScenario scenario = new HtmlScenario(options.suiteOrScenario, stepFactory);
      scenario.loadFrom(table);
      runnable = scenario;

    } else {
      HtmlSuite suite = new HtmlSuite(options.suiteOrScenario, stepFactory);
      suite.loadFrom(table);
      runnable = suite;
    }

    return true;
  }

  private boolean prepareDriver() throws IOException {
    ctx.setDriver(new DriverFactory(options).createDriver());
    return true;
  }

  private void run() throws IOException, SAXException {
    try {
      runnable.run(ctx);
    } finally {
      ctx.getDriver().quit();
    }
  }

  private void generateReport() throws IOException {
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
    Capabilities caps = ((RemoteWebDriver) ctx.getDriver()).getCapabilities();
    sb.append("<table class='metadata'><tbody>\n");
    sb.append(String.format("<tr><td>Base URL</td><td>%s</td></tr>\n", options.baseUrl));
    sb.append(String.format("<tr><td>Browser type</td><td>%s</td></tr>\n", caps.getBrowserName()));
    sb.append(String.format("<tr><td>Browser version</td><td>%s</td></tr>\n", caps.getVersion()));
    sb.append(String.format("<tr><td>Platform</td><td>%s</td></tr>\n", caps.getPlatform()));
    sb.append("</tbody></table>\n");
    sb.append(runnable.toHtml());
    sb.append(String.format("<p class='footprint'>Test run performed by Selenium version %s</p>\n", new BuildInfo().getReleaseLabel()));
    sb.append("</body>\n</html>");
    FileWriter reportWriter = new FileWriter(new File(options.report));
    try {
      reportWriter.write(sb.toString());
    } finally {
      reportWriter.close();
    }
  }

}
