package ru.stqa.selenium.legrc.runner.steps;

import ru.stqa.selenium.legrc.runner.*;

import java.util.List;

public class WaitForResult extends AbstractStepWrapper {

  private String expectedResult;

  public WaitForResult(Step step, String expectedResult) {
    super(step);
    this.expectedResult = expectedResult;
  }

  public static class Factory implements ResultProcessor.Factory {
    @Override
    public ResultProcessor wrap(Step step, List<String> args) {
      return new WaitForResult(step, args.get(2));
    }
  }

  @Override
  public boolean run(RunContext ctx) {
    String expectedResult = ctx.substitute(this.expectedResult);
    long start = System.currentTimeMillis();
    do {
      step.run(ctx);
      if (step.getOutcome().matches(expectedResult)) {
        return true;
      }
    } while (System.currentTimeMillis() < start + ctx.getTimeout());

    return false;
  }

}
