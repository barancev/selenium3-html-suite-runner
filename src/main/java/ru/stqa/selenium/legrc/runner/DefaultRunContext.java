package ru.stqa.selenium.legrc.runner;

import com.thoughtworks.selenium.Selenium;
import org.openqa.selenium.WebDriver;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DefaultRunContext implements RunContext {

  private String baseUrl;
  private WebDriver driver;
  private WebDriverBackedSelenium wdbs;
  private Map<String, StepOutcome> vars = new HashMap<String, StepOutcome>();

  @Override
  public void setBaseUrl(String baseUrl) {
    this.baseUrl = baseUrl;
  }

  @Override
  public void setDriver(WebDriver driver) {
    this.driver = driver;
  }

  @Override
  public WebDriver getDriver() {
    return driver;
  }

  @SuppressWarnings("deprecation")
  @Override
  public Selenium getSelenium() {
    if (wdbs == null) {
      wdbs = new WebDriverBackedSelenium(driver, baseUrl);
    }
    return wdbs;
  }

  @Override
  public long getTimeout() {
    return 5000;
  }

  @Override
  public void storeVar(String name, StepOutcome value) {
    vars.put(name, value);
  }

  @Override
  public String substitute(String text) {
    StringBuffer sb = new StringBuffer();
    Pattern p = Pattern.compile("(\\$\\{\\w+\\})");
    Matcher m = p.matcher(text);
    while (m.find()) {
      String maybeVar = m.group(1);
      String varName = maybeVar.substring(2, maybeVar.length() - 1);
      if (vars.containsKey(varName)) {
        m.appendReplacement(sb, Matcher.quoteReplacement(vars.get(varName).toString()));
      } else {
        m.appendReplacement(sb, Matcher.quoteReplacement(maybeVar));
      }
    }
    m.appendTail(sb);
    return sb.toString();
  }

}
