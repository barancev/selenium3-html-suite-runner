package ru.stqa.selenium.legrc.runner.steps;

import ru.stqa.selenium.legrc.runner.RunContext;
import ru.stqa.selenium.legrc.runner.Step;
import ru.stqa.selenium.legrc.runner.StepWrapper;

import java.util.List;

public class AssertNotResult extends AbstractStepWrapper {

  private String expectedResult;

  public AssertNotResult(Step step, String expectedResult) {
    super(step);
    this.expectedResult = expectedResult;
  }

  public static class Factory implements StepWrapper.Factory {
    @Override
    public StepWrapper wrap(Step step, List<String> args) {
      return new AssertNotResult(step, args.get(2));
    }
  }

  @Override
  public boolean afterStep(RunContext ctx) {
    return ! step.getOutcome().matches(ctx.substitute(expectedResult));
  }

}
