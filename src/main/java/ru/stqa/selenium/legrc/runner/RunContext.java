package ru.stqa.selenium.legrc.runner;

import com.thoughtworks.selenium.Selenium;
import org.openqa.selenium.WebDriver;

public interface RunContext {

  void setDriver(WebDriver driver);
  WebDriver getDriver();
  Selenium getSelenium();

  long getTimeout();

  void storeVar(String name, StepOutcome value);
  String substitute(String text);
}
