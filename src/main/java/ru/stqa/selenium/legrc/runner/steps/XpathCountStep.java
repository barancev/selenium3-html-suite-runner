package ru.stqa.selenium.legrc.runner.steps;

import ru.stqa.selenium.legrc.runner.HasNumberResult;
import ru.stqa.selenium.legrc.runner.HasStringResult;
import ru.stqa.selenium.legrc.runner.RunContext;
import ru.stqa.selenium.legrc.runner.Step;

import java.util.List;

public class XpathCountStep implements Step, HasNumberResult {

  private String locator;
  private Number result;

  public XpathCountStep(String locator) {
    this.locator = locator;
  }

  @Override
  public Number getResult() {
    return result;
  }

  public static class Factory implements Step.Factory {
    @Override
    public Step create(List<String> args) {
      return new XpathCountStep(args.get(1));
    }
  }

  @Override
  public boolean run(RunContext ctx) {
    result = ctx.getWDBS().getXpathCount(ctx.substitute(locator));
    return true;
  }

}
