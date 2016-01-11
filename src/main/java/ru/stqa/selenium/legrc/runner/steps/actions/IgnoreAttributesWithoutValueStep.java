package ru.stqa.selenium.legrc.runner.steps.actions;

import ru.stqa.selenium.legrc.runner.RunContext;
import ru.stqa.selenium.legrc.runner.Step;
import ru.stqa.selenium.legrc.runner.StepOutcome;
import ru.stqa.selenium.legrc.runner.steps.AbstractStep;
import ru.stqa.selenium.legrc.runner.steps.VoidOutcome;

import java.util.List;

public class IgnoreAttributesWithoutValueStep extends AbstractStep {

  private String ignore;

  public IgnoreAttributesWithoutValueStep(List<String> args) {
    super(args, 1);
    this.ignore = args.get(1);
  }

  public static class Factory implements Step.Factory {
    @Override
    public Step create(List<String> args) {
      return new IgnoreAttributesWithoutValueStep(args);
    }
  }

  @Override
  public StepOutcome runInternal(RunContext ctx) {
    ctx.getSelenium().ignoreAttributesWithoutValue(ctx.substitute(ignore));
    return new VoidOutcome();
  }
}
