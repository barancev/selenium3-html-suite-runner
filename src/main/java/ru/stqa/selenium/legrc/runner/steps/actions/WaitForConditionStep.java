package ru.stqa.selenium.legrc.runner.steps.actions;

import ru.stqa.selenium.legrc.runner.RunContext;
import ru.stqa.selenium.legrc.runner.Step;
import ru.stqa.selenium.legrc.runner.StepOutcome;
import ru.stqa.selenium.legrc.runner.steps.AbstractStep;
import ru.stqa.selenium.legrc.runner.steps.VoidOutcome;

import java.util.List;

public class WaitForConditionStep extends AbstractStep {

  private String script;
  private String timeout;

  public WaitForConditionStep(List<String> args) {
    super(args, 2);
    this.script = args.get(1);
    this.timeout = args.get(2);
  }

  public static class Factory implements Step.Factory {
    @Override
    public Step create(List<String> args) {
      return new WaitForConditionStep(args);
    }
  }

  @Override
  public StepOutcome runInternal(RunContext ctx) {
    ctx.getSelenium().waitForCondition(ctx.substitute(script), ctx.substitute(timeout));
    return new VoidOutcome();
  }
}
