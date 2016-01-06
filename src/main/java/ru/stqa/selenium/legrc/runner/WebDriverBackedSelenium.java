package ru.stqa.selenium.legrc.runner;

import com.google.common.base.Supplier;

import com.thoughtworks.selenium.CommandProcessor;
import com.thoughtworks.selenium.DefaultSelenium;

import com.thoughtworks.selenium.webdriven.WebDriverCommandProcessor;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.internal.WrapsDriver;

public class WebDriverBackedSelenium extends DefaultSelenium
        implements HasCapabilities, WrapsDriver {
  public WebDriverBackedSelenium(Supplier<WebDriver> maker, String baseUrl) {
    super(new WebDriverCommandProcessor(baseUrl, maker));
  }

  public WebDriverBackedSelenium(WebDriver baseDriver, String baseUrl) {
    super(new WebDriverCommandProcessor(baseUrl, baseDriver));
  }

  public WebDriver getWrappedDriver() {
    return ((WrapsDriver) commandProcessor).getWrappedDriver();
  }

  @Override
  public Capabilities getCapabilities() {
    WebDriver driver = getWrappedDriver();
    if (driver instanceof HasCapabilities) {
      return ((HasCapabilities) driver).getCapabilities();
    }

    return null;
  }

  public CommandProcessor getCommandProcessor() {
    return commandProcessor;
  }
}
