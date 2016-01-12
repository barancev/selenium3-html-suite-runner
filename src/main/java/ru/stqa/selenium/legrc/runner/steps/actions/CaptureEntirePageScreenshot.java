package ru.stqa.selenium.legrc.runner.steps.actions;

import ru.stqa.selenium.legrc.runner.RunContext;
import ru.stqa.selenium.legrc.runner.Step;
import ru.stqa.selenium.legrc.runner.StepOutcome;
import ru.stqa.selenium.legrc.runner.steps.AbstractStep;
import ru.stqa.selenium.legrc.runner.steps.VoidOutcome;

import java.util.List;

public class CaptureEntirePageScreenshot extends AbstractStep {

  private String filename;
  private String kwargs;

  public CaptureEntirePageScreenshot(List<String> args) {
    super(args, 2);
    this.filename = args.get(1);
    this.kwargs = args.get(2);
  }

  public static class Factory implements Step.Factory {
    @Override
    public Step create(List<String> args) {
      return new CaptureEntirePageScreenshot(args);
    }
  }

  @Override
  public StepOutcome runInternal(RunContext ctx) {
    ctx.getSelenium().captureEntirePageScreenshot(ctx.substitute(filename), ctx.substitute(kwargs));
    return new VoidOutcome();
  }
}
