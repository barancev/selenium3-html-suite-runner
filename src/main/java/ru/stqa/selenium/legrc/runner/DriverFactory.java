package ru.stqa.selenium.legrc.runner;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.IOException;
import java.net.URL;

public class DriverFactory {

  private CliOptions options;

  public DriverFactory(CliOptions options) {
    this.options = options;
  }

  public WebDriver createDriver() throws IOException {
    if (options.gridUrl == null) {
      // local driver
      if (options.browser.equals(BrowserType.FIREFOX)) {
        return new FirefoxDriver(getCapabilities());
      } else if (options.browser.equals(BrowserType.CHROME)) {
        return new ChromeDriver(getCapabilities());
      } else if (options.browser.equals(BrowserType.IE)) {
        return new InternetExplorerDriver(getCapabilities());
      } else {
        return null;
      }
    } else {
      // remote driver
      return new RemoteWebDriver(new URL(options.gridUrl), getCapabilities());
    }
  }

  private Capabilities getCapabilities() {
    DesiredCapabilities capabilities = new DesiredCapabilities();
    capabilities.setBrowserName(options.browser);
    for (String capability : options.capabilities) {
      String[] parts = capability.split("=");
      capabilities.setCapability(parts[0], parts[1]);
    }
    return capabilities;
  }

}
