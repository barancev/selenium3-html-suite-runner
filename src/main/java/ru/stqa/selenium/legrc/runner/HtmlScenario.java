package ru.stqa.selenium.legrc.runner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HtmlScenario implements HtmlRunnable {

  private static long counter = 0;
  private String name;
  private String path;
  private long id;
  private List<Step> steps = new ArrayList<Step>();
  boolean result = true;
  private long start;
  private long finish;

  public HtmlScenario(String path) {
    this.path = path;
    this.id = ++counter;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public long getId() {
    return id;
  }

  public void addStep(Step step) {
    steps.add(step);
  }

  public long getDuration() {
    return finish - start;
  }

  public boolean getResult() {
    return result;
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
    result = true;
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
    sb.append(String.format("<h3><a name='s%s'></a>Scenario &quot;%s&quot; (%s)</h3>\n", id, name, path));
    sb.append(String.format("<p>Started at: %s<br/>\n", new Date(start)));
    sb.append(String.format("Total execution time (ms): %d</p>\n", getDuration()));
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
