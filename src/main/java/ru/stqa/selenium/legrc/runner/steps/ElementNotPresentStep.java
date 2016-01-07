package ru.stqa.selenium.legrc.runner.steps;

import ru.stqa.selenium.legrc.runner.RunContext;
import ru.stqa.selenium.legrc.runner.Step;
import ru.stqa.selenium.legrc.runner.StepOutcome;

import java.util.List;

public class ElementNotPresentStep extends AbstractStep {

  private String locator;

  public ElementNotPresentStep(List<String> args) {
    super(args);
    this.locator = args.get(1);
  }

  public static class Factory implements Step.Factory {
    @Override
    public Step create(List<String> args) {
      return new ElementNotPresentStep(args);
    }
  }

  @Override
  public StepOutcome runInternal(RunContext ctx) {
    return new BooleanOutcome(ctx.getWDBS().isElementPresent(ctx.substitute(locator)));
  }

}
