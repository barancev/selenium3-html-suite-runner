package ru.stqa.selenium.legrc.runner.steps.actions;

import ru.stqa.selenium.legrc.runner.RunContext;
import ru.stqa.selenium.legrc.runner.Step;
import ru.stqa.selenium.legrc.runner.StepOutcome;
import ru.stqa.selenium.legrc.runner.steps.AbstractStep;
import ru.stqa.selenium.legrc.runner.steps.VoidOutcome;

import java.util.List;

public class WaitForPopUpStep extends AbstractStep {

  private String windowID;
  private String timeout;

  public WaitForPopUpStep(List<String> args) {
    super(args, 2);
    this.windowID = args.get(1);
    this.timeout = args.get(2);
  }

  public static class Factory implements Step.Factory {
    @Override
    public Step create(List<String> args) {
      return new WaitForPopUpStep(args);
    }
  }

  @Override
  public StepOutcome runInternal(RunContext ctx) {
    ctx.getSelenium().waitForPopUp(ctx.substitute(windowID), ctx.substitute(timeout));
    return new VoidOutcome();
  }
}
