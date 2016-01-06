package ru.stqa.selenium.legrc.runner.steps;

import ru.stqa.selenium.legrc.runner.HasNumberResult;
import ru.stqa.selenium.legrc.runner.RunContext;
import ru.stqa.selenium.legrc.runner.Step;

import java.util.List;

public class CssCountStep implements Step, HasNumberResult {

  private String locator;
  private Number result;

  public CssCountStep(String locator) {
    this.locator = locator;
  }

  @Override
  public Number getResult() {
    return result;
  }

  public static class Factory implements Step.Factory {
    @Override
    public Step create(List<String> args) {
      return new CssCountStep(args.get(1));
    }
  }

  @Override
  public boolean run(RunContext ctx) {
    result = ctx.getWDBS().getCssCount(ctx.substitute(locator));
    return true;
  }

}
