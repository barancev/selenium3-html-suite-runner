package ru.stqa.selenium.legrc.runner.steps.actions;

import ru.stqa.selenium.legrc.runner.RunContext;
import ru.stqa.selenium.legrc.runner.Step;
import ru.stqa.selenium.legrc.runner.StepOutcome;
import ru.stqa.selenium.legrc.runner.steps.AbstractStep;
import ru.stqa.selenium.legrc.runner.steps.VoidOutcome;

import java.util.List;

public class CreateCookieStep extends AbstractStep {

  private String nameValuePair;
  private String optionsString;

  public CreateCookieStep(List<String> args) {
    super(args, 2);
    this.nameValuePair = args.get(1);
    this.optionsString = args.get(2);
  }

  public static class Factory implements Step.Factory {
    @Override
    public Step create(List<String> args) {
      return new CreateCookieStep(args);
    }
  }

  @Override
  public StepOutcome runInternal(RunContext ctx) {
    ctx.getSelenium().createCookie(ctx.substitute(nameValuePair), ctx.substitute(optionsString));
    return new VoidOutcome();
  }
}
