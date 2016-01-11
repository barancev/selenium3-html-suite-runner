package ru.stqa.selenium.legrc.runner.steps.accessors;

import ru.stqa.selenium.legrc.runner.RunContext;
import ru.stqa.selenium.legrc.runner.Step;
import ru.stqa.selenium.legrc.runner.StepOutcome;
import ru.stqa.selenium.legrc.runner.steps.AbstractStep;
import ru.stqa.selenium.legrc.runner.steps.NumberOutcome;

import java.util.List;

public class ElementPositionLeftStep extends AbstractStep {

  private String locator;

  public ElementPositionLeftStep(List<String> args) {
    super(args, 1);
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
