package ru.stqa.selenium.legrc.runner.steps.actions;

import ru.stqa.selenium.legrc.runner.RunContext;
import ru.stqa.selenium.legrc.runner.Step;
import ru.stqa.selenium.legrc.runner.StepOutcome;
import ru.stqa.selenium.legrc.runner.steps.AbstractStep;
import ru.stqa.selenium.legrc.runner.steps.VoidOutcome;

import java.util.List;

public class DeleteCookieStep extends AbstractStep {

  private String name;
  private String optionsString;

  public DeleteCookieStep(List<String> args) {
    super(args, 2);
    this.name = args.get(1);
    this.optionsString = args.get(2);
  }

  public static class Factory implements Step.Factory {
    @Override
    public Step create(List<String> args) {
      return new DeleteCookieStep(args);
    }
  }

  @Override
  public StepOutcome runInternal(RunContext ctx) {
    ctx.getSelenium().deleteCookie(ctx.substitute(name), ctx.substitute(optionsString));
    return new VoidOutcome();
  }
}
