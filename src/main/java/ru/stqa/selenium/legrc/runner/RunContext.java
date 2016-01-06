package ru.stqa.selenium.legrc.runner;

import org.openqa.selenium.WebDriver;

public interface RunContext {

  void setDriver(WebDriver driver);
  WebDriver getDriver();
}
