package ru.stqa.selenium.legrc.runner;

import org.openqa.selenium.WebDriver;

public interface RunContext {

  void setDriver(WebDriver driver);
  WebDriver getDriver();
  WebDriverBackedSelenium getWDBS();

  long getTimeout();

  void storeVar(String name, StepOutcome value);
  String substitute(String text);
}
