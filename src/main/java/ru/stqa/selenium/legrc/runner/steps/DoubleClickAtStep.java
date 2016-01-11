package ru.stqa.selenium.legrc.runner.steps;

import ru.stqa.selenium.legrc.runner.RunContext;
import ru.stqa.selenium.legrc.runner.Step;
import ru.stqa.selenium.legrc.runner.StepOutcome;

import java.util.List;

public class DoubleClickAtStep extends AbstractStep {

  private String locator;
  private String coords;

  public DoubleClickAtStep(List<String> args) {
    super(args, 2);
    this.locator = args.get(1);
    this.coords = args.get(2);
  }

  public static class Factory implements Step.Factory {
    @Override
    public Step create(List<String> args) {
      return new DoubleClickAtStep(args);
    }
  }

  @Override
  public StepOutcome runInternal(RunContext ctx) {
    ctx.getSelenium().doubleClickAt(ctx.substitute(locator), ctx.substitute(coords));
    return new VoidOutcome();
  }
}
