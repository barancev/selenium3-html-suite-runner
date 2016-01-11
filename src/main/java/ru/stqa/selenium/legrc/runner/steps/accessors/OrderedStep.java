package ru.stqa.selenium.legrc.runner.steps.accessors;

import ru.stqa.selenium.legrc.runner.RunContext;
import ru.stqa.selenium.legrc.runner.Step;
import ru.stqa.selenium.legrc.runner.StepOutcome;
import ru.stqa.selenium.legrc.runner.steps.AbstractStep;
import ru.stqa.selenium.legrc.runner.steps.BooleanOutcome;

import java.util.List;

public class OrderedStep extends AbstractStep {

  private String locator1;
  private String locator2;

  public OrderedStep(List<String> args) {
    super(args, 2);
    this.locator1 = args.get(1);
    this.locator2 = args.get(2);
  }

  public static class Factory implements Step.Factory {
    @Override
    public Step create(List<String> args) {
      return new OrderedStep(args);
    }
  }

  @Override
  public StepOutcome runInternal(RunContext ctx) {
    return new BooleanOutcome(ctx.getSelenium().isOrdered(ctx.substitute(locator1), ctx.substitute(locator2)));
  }
}
