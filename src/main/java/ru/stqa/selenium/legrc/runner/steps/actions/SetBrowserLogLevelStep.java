package ru.stqa.selenium.legrc.runner.steps.actions;

import ru.stqa.selenium.legrc.runner.RunContext;
import ru.stqa.selenium.legrc.runner.Step;
import ru.stqa.selenium.legrc.runner.StepOutcome;
import ru.stqa.selenium.legrc.runner.steps.AbstractStep;
import ru.stqa.selenium.legrc.runner.steps.VoidOutcome;

import java.util.List;

public class SetBrowserLogLevelStep extends AbstractStep {

  private String logLevel;

  public SetBrowserLogLevelStep(List<String> args) {
    super(args, 1);
    this.logLevel = args.get(1);
  }

  public static class Factory implements Step.Factory {
    @Override
    public Step create(List<String> args) {
      return new SetBrowserLogLevelStep(args);
    }
  }

  @Override
  public StepOutcome runInternal(RunContext ctx) {
    ctx.getSelenium().setBrowserLogLevel(ctx.substitute(logLevel));
    return new VoidOutcome();
  }
}
