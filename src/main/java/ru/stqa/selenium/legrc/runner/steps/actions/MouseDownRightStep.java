package ru.stqa.selenium.legrc.runner.steps.actions;

import ru.stqa.selenium.legrc.runner.RunContext;
import ru.stqa.selenium.legrc.runner.Step;
import ru.stqa.selenium.legrc.runner.StepOutcome;
import ru.stqa.selenium.legrc.runner.steps.AbstractStep;
import ru.stqa.selenium.legrc.runner.steps.VoidOutcome;

import java.util.List;

public class MouseDownRightStep extends AbstractStep {

  private String locator;

  public MouseDownRightStep(List<String> args) {
    super(args, 1);
    this.locator = args.get(1);
  }

  public static class Factory implements Step.Factory {
    @Override
    public Step create(List<String> args) {
      return new MouseDownRightStep(args);
    }
  }

  @Override
  public StepOutcome runInternal(RunContext ctx) {
    ctx.getSelenium().mouseDownRight(ctx.substitute(locator));
    return new VoidOutcome();
  }
}
