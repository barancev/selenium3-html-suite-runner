package ru.stqa.selenium.legrc.runner.steps;

import ru.stqa.selenium.legrc.runner.RunContext;
import ru.stqa.selenium.legrc.runner.Step;
import ru.stqa.selenium.legrc.runner.StepOutcome;

import java.util.List;

public class ElementPositionLeftStep extends AbstractStep {

  private String locator;

  public ElementPositionLeftStep(List<String> args) {
    super(args);
    this.locator = args.get(1);
  }

  public static class Factory implements Step.Factory {
    @Override
    public Step create(List<String> args) {
      return new ElementPositionLeftStep(args);
    }
  }

  @Override
  public StepOutcome runInternal(RunContext ctx) {
    return new NumberOutcome(ctx.getSelenium().getElementPositionLeft(ctx.substitute(locator)));
  }

}
