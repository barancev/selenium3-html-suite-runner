package ru.stqa.selenium.legrc.runner.steps;

import ru.stqa.selenium.legrc.runner.RunContext;
import ru.stqa.selenium.legrc.runner.Step;
import ru.stqa.selenium.legrc.runner.StepWrapper;

public class VerifyResult extends AbstractStepWrapper {

  private String expectedResult;

  public VerifyResult(Step step) {
    super(step);
    this.expectedResult = step.getExtraArg();
  }

  public static class Factory implements StepWrapper.Factory {
    @Override
    public StepWrapper wrap(Step step) {
      return new VerifyResult(step);
    }
  }

  @Override
  public boolean breaksOnFailure() {
    return false;
  }

  @Override
  public boolean afterStep(RunContext ctx) {
    return step.getOutcome().matches(ctx.substitute(expectedResult));
  }

}
