package ru.stqa.selenium.legrc.runner.steps;

import ru.stqa.selenium.legrc.runner.RunContext;
import ru.stqa.selenium.legrc.runner.Step;
import ru.stqa.selenium.legrc.runner.StepWrapper;

import java.util.List;

public class WaitForNotResult extends AbstractStepWrapper {

  private String expectedResult;

  public WaitForNotResult(Step step, String expectedResult) {
    super(step);
    this.expectedResult = expectedResult;
  }

  public static class Factory implements StepWrapper.Factory {
    @Override
    public StepWrapper wrap(Step step, List<String> args) {
      return new WaitForNotResult(step, args.get(2));
    }
  }

  @Override
  public boolean run(RunContext ctx) {
    String expectedResult = ctx.substitute(this.expectedResult);
    long start = System.currentTimeMillis();
    while (System.currentTimeMillis() < start + ctx.getTimeout()) {
      boolean stepResult = step.run(ctx);
      if (stepResult && ! step.getOutcome().matches(expectedResult)) {
        return true;
      }
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    return false;
  }

}
