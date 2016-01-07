package ru.stqa.selenium.legrc.runner;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class HtmlSuite implements HtmlRunnable {

  private final File path;
  private List<HtmlScenario> scenarios = new ArrayList<HtmlScenario>();

  public HtmlSuite(File path) {
    this.path = path;
  }

  public File getFullPathToScenario(String relativePath) {
    return new File(path.getParentFile(), relativePath);
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
    boolean result = true;
    for (HtmlScenario scenario : scenarios) {
      result = scenario.run(ctx) && result;
    }
    return result;
  }

  @Override
  public String toHtml() {
    StringBuilder sb = new StringBuilder();
    sb.append("<div class='suite'>\n");
    sb.append("<p>Metadata</p>\n");
    for (HtmlScenario scenario : scenarios) {
      sb.append(scenario.toHtml());
      sb.append("\n");
    }
    sb.append("</div>\n");
    return sb.toString();
  }
}
