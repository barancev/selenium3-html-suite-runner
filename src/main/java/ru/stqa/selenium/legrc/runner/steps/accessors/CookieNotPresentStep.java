package ru.stqa.selenium.legrc.runner.steps.accessors;

import ru.stqa.selenium.legrc.runner.RunContext;
import ru.stqa.selenium.legrc.runner.Step;
import ru.stqa.selenium.legrc.runner.StepOutcome;
import ru.stqa.selenium.legrc.runner.steps.AbstractStep;
import ru.stqa.selenium.legrc.runner.steps.BooleanOutcome;

import java.util.List;

public class CookieNotPresentStep extends AbstractStep {

  private String name;

  public CookieNotPresentStep(List<String> args) {
    super(args, 1);
    this.name = args.get(1);
  }

  public static class Factory implements Step.Factory {
    @Override
    public Step create(List<String> args) {
      return new CookieNotPresentStep(args);
    }
  }

  @Override
  public StepOutcome runInternal(RunContext ctx) {
    return new BooleanOutcome(! ctx.getSelenium().isCookiePresent(ctx.substitute(name)));
  }

}
