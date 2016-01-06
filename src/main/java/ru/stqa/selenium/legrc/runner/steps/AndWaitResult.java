package ru.stqa.selenium.legrc.runner.steps;

import ru.stqa.selenium.legrc.runner.ResultProcessor;
import ru.stqa.selenium.legrc.runner.RunContext;
import ru.stqa.selenium.legrc.runner.Step;

import java.util.List;

public class AndWaitResult implements ResultProcessor {

  private Step step;

  public AndWaitResult(Step step) {
    this.step = step;
  }

  public static class Factory implements ResultProcessor.Factory {
    @Override
    public ResultProcessor wrap(Step step, List<String> args) {
      return new AndWaitResult(step);
    }
  }

  @Override
  public boolean run(RunContext ctx) {
    step.run(ctx);
    ctx.getWDBS().waitForPageToLoad(ctx.getPageLoadTimeout());
    return true;
  }

}
