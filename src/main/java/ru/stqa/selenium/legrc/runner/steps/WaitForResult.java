package ru.stqa.selenium.legrc.runner.steps;

import ru.stqa.selenium.legrc.runner.RunContext;
import ru.stqa.selenium.legrc.runner.Step;
import ru.stqa.selenium.legrc.runner.StepOutcome;
import ru.stqa.selenium.legrc.runner.StepWrapper;

public class WaitForResult extends AbstractStepWrapper {

  private String expectedResult;

  public WaitForResult(Step step) {
    super(step);
    this.expectedResult = step.getExtraArg();
  }

  public static class Factory implements StepWrapper.Factory {
    @Override
    public StepWrapper wrap(Step step) {
      return new WaitForResult(step);
    }
  }

  @Override
  public StepOutcome runInternal(RunContext ctx) {
    String expectedResult = ctx.substitute(this.expectedResult);
    int count = 0;
    long start = System.currentTimeMillis();
    while (System.currentTimeMillis() < start + ctx.getTimeout()) {
      boolean stepResult = step.run(ctx);
      count++;
      if (stepResult && step.getOutcome().matches(expectedResult)) {
        return new BooleanOutcome(true);
      }
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    result = false;
    return new BooleanOutcome(false);
  }

}
