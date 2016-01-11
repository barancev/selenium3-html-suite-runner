package ru.stqa.selenium.legrc.runner.steps.actions;

import ru.stqa.selenium.legrc.runner.RunContext;
import ru.stqa.selenium.legrc.runner.Step;
import ru.stqa.selenium.legrc.runner.StepOutcome;
import ru.stqa.selenium.legrc.runner.steps.AbstractStep;
import ru.stqa.selenium.legrc.runner.steps.VoidOutcome;

import java.util.List;

public class OpenWindowStep extends AbstractStep {

  private String url;
  private String windowID;

  public OpenWindowStep(List<String> args) {
    super(args, 2);
    this.url = args.get(1);
    this.windowID = args.get(2);
  }

  public static class Factory implements Step.Factory {
    @Override
    public Step create(List<String> args) {
      return new OpenWindowStep(args);
    }
  }

  @Override
  public StepOutcome runInternal(RunContext ctx) {
    ctx.getSelenium().openWindow(ctx.substitute(url), ctx.substitute(windowID));
    return new VoidOutcome();
  }
}
