package ru.stqa.selenium.legrc.runner.steps;

import ru.stqa.selenium.legrc.runner.RunContext;
import ru.stqa.selenium.legrc.runner.Step;
import ru.stqa.selenium.legrc.runner.StepOutcome;

import java.util.List;

public class XpathCountStep extends AbstractStep {

  private String locator;

  public XpathCountStep(String locator) {
    this.locator = locator;
  }

  public static class Factory implements Step.Factory {
    @Override
    public Step create(List<String> args) {
      return new XpathCountStep(args.get(1));
    }
  }

  @Override
  public StepOutcome runInternal(RunContext ctx) {
    return new NumberOutcome(ctx.getWDBS().getXpathCount(ctx.substitute(locator)));
  }

}
