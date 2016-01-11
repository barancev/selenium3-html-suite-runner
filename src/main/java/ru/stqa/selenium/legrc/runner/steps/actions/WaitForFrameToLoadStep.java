package ru.stqa.selenium.legrc.runner.steps.actions;

import ru.stqa.selenium.legrc.runner.RunContext;
import ru.stqa.selenium.legrc.runner.Step;
import ru.stqa.selenium.legrc.runner.StepOutcome;
import ru.stqa.selenium.legrc.runner.steps.AbstractStep;
import ru.stqa.selenium.legrc.runner.steps.VoidOutcome;

import java.util.List;

public class WaitForFrameToLoadStep extends AbstractStep {

  private String frameAddress;
  private String timeout;

  public WaitForFrameToLoadStep(List<String> args) {
    super(args, 2);
    this.frameAddress = args.get(1);
    this.timeout = args.get(2);
  }

  public static class Factory implements Step.Factory {
    @Override
    public Step create(List<String> args) {
      return new WaitForFrameToLoadStep(args);
    }
  }

  @Override
  public StepOutcome runInternal(RunContext ctx) {
    ctx.getSelenium().waitForFrameToLoad(ctx.substitute(frameAddress), ctx.substitute(timeout));
    return new VoidOutcome();
  }
}
