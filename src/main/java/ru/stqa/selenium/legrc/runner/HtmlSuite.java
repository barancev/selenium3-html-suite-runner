package ru.stqa.selenium.legrc.runner;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HtmlSuite implements HtmlRunnable {

  private final String path;
  private List<HtmlScenario> scenarios = new ArrayList<HtmlScenario>();
  private long start;
  private long finish;

  public HtmlSuite(String path) {
    this.path = path;
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
    sb.append(String.format("<p>Started at: %s<br/>\n", new Date(start)));
    sb.append(String.format("Total execution time (ms): %d</p>\n", finish - start));
    sb.append("<table class='scenarios' border='1' cellpadding='1' cellspacing='1'>\n");
    sb.append("<thead><tr><th>Scenario</th><th>Result</th><th>Time&nbsp;(ms)</th></tr></thead>\n");
    sb.append("<tbody>\n");
    for (HtmlScenario scenario : scenarios) {
      sb.append(String.format("<tr class='%s'><td><a href='#s%s'>%s</a></td><td>%s</td><td>%d</td></tr>\n",
              scenario.getResult() ? "status_passed" : "status_failed",
              scenario.getId(), scenario.getName(), scenario.getResult(), scenario.getDuration()));
    }
    sb.append("</tbody></table>\n");
    for (HtmlScenario scenario : scenarios) {
      sb.append(scenario.toHtml());
      sb.append("\n");
    }
    sb.append("</div>\n");
    return sb.toString();
  }
}
