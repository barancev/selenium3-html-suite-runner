package ru.stqa.selenium.legrc.runner;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class HtmlScenario implements HtmlRunnable {

  private String name;
  private final File path;
  private List<Step> steps = new ArrayList<Step>();

  public HtmlScenario(File path) {
    this.path = path;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void addStep(Step step) {
    steps.add(step);
  }

  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append(name);
    builder.append("\n");
    for (Step step : steps) {
      builder.append(step);
      builder.append("\n");
    }
    return builder.toString();
  }

  public boolean run(RunContext ctx) {
    boolean result = true;
    for (Step step : steps) {
      boolean stepResult = step.run(ctx);
      result = stepResult && result;
      System.out.print(stepResult ? "." : "F");
    }
    return result;
  }

  @Override
  public String toHtml() {
    StringBuilder sb = new StringBuilder();
    sb.append("<div class='scenario'>\n");
    sb.append("<p>Metadata</p>\n");
    sb.append("<table class='scenario' border='1' cellpadding='1' cellspacing='1'><tbody>\n");
    for (Step step : steps) {
      sb.append(step.toHtml());
      sb.append("\n");
    }
    sb.append("</tbody></table>\n");
    sb.append("</div>\n");
    return sb.toString();
  }
}
