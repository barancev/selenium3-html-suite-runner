package ru.stqa.selenium.legrc.runner.steps;

import ru.stqa.selenium.legrc.runner.HasStringResult;
import ru.stqa.selenium.legrc.runner.RunContext;
import ru.stqa.selenium.legrc.runner.Step;

import java.util.List;

public class ValueStep implements Step, HasStringResult {

  private String locator;
  private String result;

  public ValueStep(String locator) {
    this.locator = locator;
  }

  @Override
  public String getResult() {
    return result;
  }

  public static class Factory implements Step.Factory {
    @Override
    public Step create(List<String> args) {
      return new ValueStep(args.get(1));
    }
  }

  @Override
  public boolean run(RunContext ctx) {
    result = ctx.getWDBS().getValue(locator);
    return true;
  }

}
