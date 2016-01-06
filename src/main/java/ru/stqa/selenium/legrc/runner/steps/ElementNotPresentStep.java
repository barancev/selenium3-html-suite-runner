package ru.stqa.selenium.legrc.runner.steps;

import ru.stqa.selenium.legrc.runner.HasBooleanResult;
import ru.stqa.selenium.legrc.runner.RunContext;
import ru.stqa.selenium.legrc.runner.Step;

import java.util.List;

public class ElementNotPresentStep implements Step, HasBooleanResult {

  private String locator;
  private boolean result;

  public ElementNotPresentStep(String locator) {
    this.locator = locator;
  }

  @Override
  public boolean getResult() {
    return result;
  }

  public static class Factory implements Step.Factory {
    @Override
    public Step create(List<String> args) {
      return new ElementNotPresentStep(args.get(1));
    }
  }

  @Override
  public boolean run(RunContext ctx) {
    result = ! ctx.getWDBS().isElementPresent(ctx.substitute(locator));
    return true;
  }

}