package ru.stqa.selenium.legrc.runner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HtmlScenario implements HtmlRunnable {

  private String name;
  private String path;
  private List<Step> steps = new ArrayList<Step>();
  private long start;
  private long finish;

  public HtmlScenario(String path) {
    this.path = path;
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
    start = System.currentTimeMillis();
    System.out.print("\n" + name + " ");
    boolean result = true;
    for (Step step : steps) {
      boolean stepResult = step.run(ctx);
      result = stepResult && result;
      if (stepResult) {
        System.out.print(".");
      } else {
        if (!step.breaksOnFailure()) {
          System.out.print("F");
        } else {
          System.out.print("X");
          break;
        }
      }
    }
    finish = System.currentTimeMillis();
    return result;
  }

  @Override
  public String toHtml() {
    StringBuilder sb = new StringBuilder();
    sb.append("<div class='scenario'>\n");
    sb.append(String.format("<h3>Scenario %s</h3>\n", path));
    sb.append(String.format("<p>Started at: %s<br/>\n", new Date(start)));
    sb.append(String.format("Total execution time (ms): %d</p>\n", finish - start));
    sb.append("<table class='scenario' border='1' cellpadding='1' cellspacing='1'>\n");
    sb.append("<thead><tr><th>Command</th><th>Arg1</th><th>Arg2</th><th>Result</th><th>Time&nbsp;(ms)</th></tr></thead>\n");
    sb.append("<tbody>\n");
    for (Step step : steps) {
      sb.append(step.toHtml());
      sb.append("\n");
    }
    sb.append("</tbody></table>\n");
    sb.append("</div>\n");
    return sb.toString();
  }
}
