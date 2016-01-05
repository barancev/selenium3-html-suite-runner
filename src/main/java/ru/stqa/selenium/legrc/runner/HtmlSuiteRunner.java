package ru.stqa.selenium.legrc.runner;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

public class HtmlSuiteRunner {

  @Parameter(names = {"--suite", "--scenario", "-s"}, description = "Suite or scenario file to run")
  private String suiteOrScenario;

  @Parameter(names = {"--browser", "-b"}, description = "Browser type")
  private String browser;

  @Parameter(names = {"--baseurl", "-u"}, description = "Base URL")
  private String baseUrl;

  @Parameter(names = {"--report", "-r"}, description = "Report file")
  private String report;

  public static void main(String[] args) {
    HtmlSuiteRunner runner = new HtmlSuiteRunner();
    new JCommander(runner, args);
    runner.run();
  }

  private void run() {

  }

}
