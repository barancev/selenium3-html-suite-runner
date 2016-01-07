package ru.stqa.selenium.legrc.runner.steps;

import ru.stqa.selenium.legrc.runner.*;

import java.util.List;

public class AssertResult extends AbstractStepWrapper {

  private String expectedResult;

  public AssertResult(Step step, String expectedResult) {
    super(step);
    this.expectedResult = expectedResult;
  }

  public static class Factory implements StepWrapper.Factory {
    @Override
    public StepWrapper wrap(Step step, List<String> args) {
      return new AssertResult(step, args.get(2));
    }
  }

  @Override
  public boolean afterStep(RunContext ctx) {
    return step.getOutcome().matches(ctx.substitute(expectedResult));
  }

}
