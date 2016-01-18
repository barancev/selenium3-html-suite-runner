package ru.stqa.selenium.legrc.runner.steps;

import com.thoughtworks.selenium.Selenium;
import org.junit.Rule;
import org.junit.rules.ExternalResource;
import org.mockito.Mockito;
import org.openqa.selenium.WebDriver;
import ru.stqa.selenium.legrc.runner.RunContext;
import ru.stqa.selenium.legrc.runner.StepOutcome;

public class StepTestBase {

  public static class RunContextRule extends ExternalResource implements RunContext {

    Selenium mockedSelenium;

    public RunContextRule() {
      super();
      mockedSelenium = Mockito.mock(Selenium.class);
    }

    @Override
    public void setBaseUrl(String baseUrl) {
      throw new UnsupportedOperationException();
    }

    @Override
    public void setDriver(WebDriver driver) {
      throw new UnsupportedOperationException();
    }

    @Override
    public WebDriver getDriver() {
      throw new UnsupportedOperationException();
    }

    @Override
    public Selenium getSelenium() {
      return mockedSelenium;
    }

    @Override
    public long getTimeout() {
      return 3000;
    }

    @Override
    public void storeVar(String name, StepOutcome value) {
      throw new UnsupportedOperationException();
    }

    @Override
    public String substitute(String text) {
      return text;
    }
  }

  @Rule
  public RunContextRule context = new RunContextRule();

}
