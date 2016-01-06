package ru.stqa.selenium.legrc.runner.steps;

import ru.stqa.selenium.legrc.runner.*;

import java.util.List;

public class WaitForResult implements ResultProcessor {

  private Step step;
  private String expectedResult;

  public WaitForResult(Step step, String expectedResult) {
    this.step = step;
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
      if (step instanceof HasStringResult) {
        if (expectedResult.equals(((HasStringResult) step).getResult())) {
          return true;
        }
      } else if (step instanceof HasNumberResult) {
        if (expectedResult.equals(((HasNumberResult) step).getResult().toString())) {
          return true;
        }
      } else if (step instanceof HasBooleanResult) {
        if (((HasBooleanResult) step).getResult()) {
          return true;
        }
      }
    } while (System.currentTimeMillis() < start + ctx.getTimeout());

    return false;
  }

}
