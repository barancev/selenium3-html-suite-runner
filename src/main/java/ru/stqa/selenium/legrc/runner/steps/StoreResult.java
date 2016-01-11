package ru.stqa.selenium.legrc.runner.steps;

import ru.stqa.selenium.legrc.runner.*;

import java.util.List;

public class StoreResult extends AbstractStepWrapper {

  private String varName;

  public StoreResult(Step step) {
    super(step);
    this.varName = step.getExtraArg();
  }

  public static class Factory implements StepWrapper.Factory {
    @Override
    public StepWrapper wrap(Step step) {
      return new StoreResult(step);
    }
  }

  @Override
  public boolean afterStep(RunContext ctx) {
    ctx.storeVar(varName, step.getOutcome());
    return true;
  }

  @Override
  public String toHtml() {
    return step.toHtml(result ? "status_done" : "status_failed");
  }

}
