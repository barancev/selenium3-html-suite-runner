package ru.stqa.selenium.legrc.runner.steps;

import ru.stqa.selenium.legrc.runner.RunContext;
import ru.stqa.selenium.legrc.runner.Step;
import ru.stqa.selenium.legrc.runner.StepWrapper;

public class AndWaitResult extends AbstractStepWrapper {

  public AndWaitResult(Step step) {
    super(step);
  }

  public static class Factory implements StepWrapper.Factory {
    @Override
    public StepWrapper wrap(Step step) {
      return new AndWaitResult(step);
    }
  }

  @Override
  public boolean afterStep(RunContext ctx) {
    ctx.getSelenium().waitForPageToLoad("" + ctx.getTimeout());
    return true;
  }
}
