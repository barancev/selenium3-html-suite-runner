package ru.stqa.selenium.legrc.runner.steps.actions;

import ru.stqa.selenium.legrc.runner.RunContext;
import ru.stqa.selenium.legrc.runner.Step;
import ru.stqa.selenium.legrc.runner.StepOutcome;
import ru.stqa.selenium.legrc.runner.steps.AbstractStep;
import ru.stqa.selenium.legrc.runner.steps.VoidOutcome;

import java.util.List;

public class WaitForPageToLoadStep extends AbstractStep {

  private String timeout;

  public WaitForPageToLoadStep(List<String> args) {
    super(args, 1);
    this.timeout = args.get(1);
  }

  public static class Factory implements Step.Factory {
    @Override
    public Step create(List<String> args) {
      return new WaitForPageToLoadStep(args);
    }
  }

  @Override
  public StepOutcome runInternal(RunContext ctx) {
    ctx.getSelenium().waitForPageToLoad(ctx.substitute(timeout));
    return new VoidOutcome();
  }
}
