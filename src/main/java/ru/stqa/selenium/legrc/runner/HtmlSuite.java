package ru.stqa.selenium.legrc.runner;

import java.io.File;

public class HtmlSuite {

  private final File path;

  public HtmlSuite(File path) {
    this.path = path;
  }

  public File getFullPathToScenario(String relativePath) {
    return new File(path.getParentFile(), relativePath);
  }

  public void addScenario() {

  }
}
