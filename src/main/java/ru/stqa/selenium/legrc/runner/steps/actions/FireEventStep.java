package ru.stqa.selenium.legrc.runner.steps.actions;

import ru.stqa.selenium.legrc.runner.RunContext;
import ru.stqa.selenium.legrc.runner.Step;
import ru.stqa.selenium.legrc.runner.StepOutcome;
import ru.stqa.selenium.legrc.runner.steps.AbstractStep;
import ru.stqa.selenium.legrc.runner.steps.VoidOutcome;

import java.util.List;

public class FireEventStep extends AbstractStep {

  private String locator;
  private String eventName;

  public FireEventStep(List<String> args) {
    super(args, 2);
    this.locator = args.get(1);
    this.eventName = args.get(2);
  }

  public static class Factory implements Step.Factory {
    @Override
    public Step create(List<String> args) {
      return new FireEventStep(args);
    }
  }

  @Override
  public StepOutcome runInternal(RunContext ctx) {
    ctx.getSelenium().fireEvent(ctx.substitute(locator), ctx.substitute(eventName));
    return new VoidOutcome();
  }
}
