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
      result = step.run(ctx) && result;
    }
    return result;
  }
}
