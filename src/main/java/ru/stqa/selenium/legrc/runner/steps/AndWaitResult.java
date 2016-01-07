package ru.stqa.selenium.legrc.runner.steps;

import ru.stqa.selenium.legrc.runner.ResultProcessor;
import ru.stqa.selenium.legrc.runner.RunContext;
import ru.stqa.selenium.legrc.runner.Step;
import ru.stqa.selenium.legrc.runner.StepOutcome;

import java.util.List;

public class AndWaitResult extends AbstractStepWrapper {

  public AndWaitResult(Step step) {
    super(step);
  }

  public static class Factory implements ResultProcessor.Factory {
    @Override
    public ResultProcessor wrap(Step step, List<String> args) {
      return new AndWaitResult(step);
    }
  }

  @Override
  public boolean afterStep(RunContext ctx) {
    ctx.getWDBS().waitForPageToLoad("" + ctx.getTimeout());
    return true;
  }
}
