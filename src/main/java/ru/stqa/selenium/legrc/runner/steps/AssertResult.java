package ru.stqa.selenium.legrc.runner.steps;

import ru.stqa.selenium.legrc.runner.*;

import java.util.List;

public class AssertResult implements ResultProcessor {

  private Step step;
  private String expectedResult;

  public AssertResult(Step step, String expectedResult) {
    this.step = step;
    this.expectedResult = expectedResult;
  }

  public static class Factory implements ResultProcessor.Factory {
    @Override
    public ResultProcessor wrap(Step step, List<String> args) {
      return new AssertResult(step, args.get(2));
    }
  }

  @Override
  public boolean run(RunContext ctx) {
    step.run(ctx);
    if (step instanceof HasStringResult) {
      return expectedResult.equals(((HasStringResult) step).getResult());
    } else if (step instanceof HasNumberResult) {
      return expectedResult.equals(((HasNumberResult) step).getResult().toString());
    } else if (step instanceof HasBooleanResult) {
      return ((HasBooleanResult) step).getResult();
    } else {
      return false;
    }
  }

}
