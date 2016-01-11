package ru.stqa.selenium.legrc.runner.steps.actions;

import ru.stqa.selenium.legrc.runner.RunContext;
import ru.stqa.selenium.legrc.runner.Step;
import ru.stqa.selenium.legrc.runner.StepOutcome;
import ru.stqa.selenium.legrc.runner.steps.AbstractStep;
import ru.stqa.selenium.legrc.runner.steps.VoidOutcome;

import java.util.List;

public class AssignIdStep extends AbstractStep {

  private String locator;
  private String identifier;

  public AssignIdStep(List<String> args) {
    super(args, 2);
    this.locator = args.get(1);
    this.identifier = args.get(2);
  }

  public static class Factory implements Step.Factory {
    @Override
    public Step create(List<String> args) {
      return new AssignIdStep(args);
    }
  }

  @Override
  public StepOutcome runInternal(RunContext ctx) {
    ctx.getSelenium().assignId(ctx.substitute(locator), ctx.substitute(identifier));
    return new VoidOutcome();
  }
}
