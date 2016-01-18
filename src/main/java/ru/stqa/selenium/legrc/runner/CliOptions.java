package ru.stqa.selenium.legrc.runner;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.ArrayList;
import java.util.List;

public class CliOptions {

  @Parameter(names = {"--suite", "--scenario", "-s"}, description = "Suite or scenario file to run", required = true)
  public String suiteOrScenario;

  @Parameter(names = {"--browser", "-b"}, description = "Browser type", required = true)
  public String browser;

  @Parameter(names = {"--capability", "-c"}, description = "Browser capability")
  private List<String> capabilities = new ArrayList<String>();

  public Capabilities getCapabilities() {
    DesiredCapabilities result = new DesiredCapabilities();
    result.setBrowserName(browser);
    for (String capability : capabilities) {
      String[] parts = capability.split("=");
      result.setCapability(parts[0], parts[1]);
    }
    return result;
  }

  @Parameter(names = {"--grid", "-g"}, description = "Grid URL")
  public String gridUrl;

  @Parameter(names = {"--baseurl", "-u"}, description = "Base URL", required = true)
  public String baseUrl;

  @Parameter(names = {"--report", "-r"}, description = "Report file", required = true)
  public String report;

  @Parameter(names = {"--overwrite", "-o"}, description = "Overwrite report file")
  public boolean overwriteReport;

  public void parse(String[] args) {
    JCommander cli = new JCommander(this);
    try {
      cli.parse(args);
    } catch (ParameterException ex) {
      cli.usage();
      System.exit(0);
    }
  }
}
