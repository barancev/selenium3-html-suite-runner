package ru.stqa.selenium.legrc.runner;

import com.thoughtworks.selenium.CommandProcessor;
import com.thoughtworks.selenium.DefaultSelenium;

import com.thoughtworks.selenium.webdriven.WebDriverCommandProcessor;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.internal.WrapsDriver;

@SuppressWarnings("deprecation")
public class WebDriverBackedSelenium extends DefaultSelenium
        implements HasCapabilities, WrapsDriver {

  public WebDriverBackedSelenium(WebDriver baseDriver, String baseUrl) {
    super(new WebDriverCommandProcessor(baseUrl, baseDriver));
    ((WebDriverCommandProcessor) commandProcessor).addMethod("sendKeys", ((WebDriverCommandProcessor) commandProcessor).getMethod("typeKeys"));
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
