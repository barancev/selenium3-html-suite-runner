package ru.stqa.selenium.legrc.runner.steps.actions;

import ru.stqa.selenium.legrc.runner.RunContext;
import ru.stqa.selenium.legrc.runner.Step;
import ru.stqa.selenium.legrc.runner.StepOutcome;
import ru.stqa.selenium.legrc.runner.steps.AbstractStep;
import ru.stqa.selenium.legrc.runner.steps.VoidOutcome;

import java.util.List;

public class MouseDownStep extends AbstractStep {

  private String locator;

  public MouseDownStep(List<String> args) {
    super(args, 1);
    this.locator = args.get(1);
  }

  public static class Factory implements Step.Factory {
    @Override
    public Step create(List<String> args) {
      return new MouseDownStep(args);
    }
  }

  @Override
  public StepOutcome runInternal(RunContext ctx) {
    ctx.getSelenium().mouseDown(ctx.substitute(locator));
    return new VoidOutcome();
  }
}
