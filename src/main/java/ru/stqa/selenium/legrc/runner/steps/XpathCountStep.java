package ru.stqa.selenium.legrc.runner.steps;

import ru.stqa.selenium.legrc.runner.RunContext;
import ru.stqa.selenium.legrc.runner.Step;
import ru.stqa.selenium.legrc.runner.StepOutcome;

import java.util.List;

public class XpathCountStep extends AbstractStep {

  private String locator;

  public XpathCountStep(List<String> args) {
    super(args);
    this.locator = args.get(1);
  }

  public static class Factory implements Step.Factory {
    @Override
    public Step create(List<String> args) {
      return new XpathCountStep(args);
    }
  }

  @Override
  public StepOutcome runInternal(RunContext ctx) {
    return new NumberOutcome(ctx.getWDBS().getXpathCount(ctx.substitute(locator)));
  }

}
