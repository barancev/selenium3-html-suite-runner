package ru.stqa.selenium.legrc.runner;

import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import ru.stqa.selenium.legrc.runner.steps.StepFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HtmlSuite implements HtmlRunnable {

  private final String path;
  private final StepFactory stepFactory;
  private List<HtmlScenario> scenarios = new ArrayList<HtmlScenario>();
  private long start;
  private long finish;

  public HtmlSuite(String path, StepFactory stepFactory) {
    this.path = path;
    this.stepFactory = stepFactory;
  }

  public File getFullPathToScenario(String relativePath) {
    return new File(new File(path).getParentFile(), relativePath);
  }

  public void addScenario(HtmlScenario scenario) {
    scenarios.add(scenario);
  }

  public String toString() {
    StringBuilder builder = new StringBuilder();
    for (HtmlScenario scenario : scenarios) {
      builder.append(scenario);
      builder.append("\n");
    }
    return builder.toString();
  }

  protected void loadFrom(Node table) throws IOException, SAXException {
    Node tbody = HtmlParserUtils.findChildElement(table, "TBODY");
    Node row = tbody.getFirstChild();
    boolean firstRow = true;
    while (row != null) {
      if (row.getNodeType() == Node.ELEMENT_NODE) {
        if (firstRow) {
          // TODO: Suite name?
          firstRow = false;
        } else {
          String scenarioRef = HtmlParserUtils.findChildElement(row, "A").getAttributes().getNamedItem("href").getNodeValue();
          HtmlScenario scenario = new HtmlScenario(scenarioRef, stepFactory);
          File scenarioPath = getFullPathToScenario(scenarioRef);
          scenario.loadFrom(HtmlParserUtils.getTableFromHtmlFile(scenarioPath));
          addScenario(scenario);
        }
      }
      row = row.getNextSibling();
    }
  }

  public boolean run(RunContext ctx) {
    start = System.currentTimeMillis();
    boolean result = true;
    for (HtmlScenario scenario : scenarios) {
      result = scenario.run(ctx) && result;
    }
    finish = System.currentTimeMillis();
    return result;
  }

  @Override
  public String toHtml() {
    StringBuilder sb = new StringBuilder();
    sb.append("<div class='suite'>\n");
    sb.append(String.format("<h2>Suite %s</h2>\n", path));
    sb.append(String.format("<p>Started at %s</p>\n", new Date(start)));
    sb.append("<table class='scenarios' border='1' cellpadding='1' cellspacing='1'>\n");
    sb.append("<thead><tr><th>Scenario</th><th>Result</th><th>Time&nbsp;(ms)</th></tr></thead>\n");
    sb.append("<tbody>\n");
    for (HtmlScenario scenario : scenarios) {
      sb.append(String.format("<tr class='%s'><td><a href='#s%s'>%s</a></td><td>%s</td><td>%d</td></tr>\n",
              scenario.getResult() ? "status_passed" : "status_failed",
              scenario.getId(), scenario.getName(), scenario.getResult(), scenario.getDuration()));
    }
    sb.append(String.format("<tr><th colspan='2' style='text-align: right'>Total:</th><th>%d</th></tr>\n", finish - start));
    sb.append("</tbody></table>\n");
    for (HtmlScenario scenario : scenarios) {
      sb.append(scenario.toHtml());
      sb.append("\n");
    }
    sb.append("</div>\n");
    return sb.toString();
  }
}
