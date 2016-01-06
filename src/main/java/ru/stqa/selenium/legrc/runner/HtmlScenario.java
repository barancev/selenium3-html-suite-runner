package ru.stqa.selenium.legrc.runner;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class HtmlScenario {

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
}
