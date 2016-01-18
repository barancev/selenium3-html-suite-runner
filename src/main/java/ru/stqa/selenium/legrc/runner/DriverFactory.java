package ru.stqa.selenium.legrc.runner;

import com.google.common.collect.ImmutableMap;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

public class DriverFactory {

  private CliOptions options;

  public DriverFactory(CliOptions options) {
    this.options = options;
  }

  public WebDriver createDriver() throws IOException {
    if (options.gridUrl == null) {
      // local driver
      DriverSupplier supplier = driverSuppliers.get(options.browser);
      if (supplier == null) {
        throw new IllegalArgumentException("Unrecognized browser: " + options.browser);
      }
      return supplier.getDriver(options);

    } else {
      // remote driver
      return new RemoteWebDriver(new URL(options.gridUrl), options.getCapabilities());
    }
  }

  private interface DriverSupplier {
    WebDriver getDriver(CliOptions options);
  }

  private static Map<String, DriverSupplier> driverSuppliers = new ImmutableMap.Builder<String, DriverSupplier>()
          .put(BrowserType.FIREFOX, new DriverSupplier() {
            @Override
            public WebDriver getDriver(CliOptions options) {
              return new FirefoxDriver(options.getCapabilities());
            }
          })
          .put(BrowserType.CHROME, new DriverSupplier() {
            @Override
            public WebDriver getDriver(CliOptions options) {
              return new ChromeDriver(options.getCapabilities());
            }
          })
          .put(BrowserType.IE, new DriverSupplier() {
            @Override
            public WebDriver getDriver(CliOptions options) {
              return new InternetExplorerDriver(options.getCapabilities());
            }
          })
          .build();

}
