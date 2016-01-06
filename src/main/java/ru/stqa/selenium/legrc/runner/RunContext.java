package ru.stqa.selenium.legrc.runner;

import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;
import org.openqa.selenium.WebDriver;

public interface RunContext {

  void setDriver(WebDriver driver);
  WebDriver getDriver();
  WebDriverBackedSelenium getWDBS();

  String getPageLoadTimeout();
}
