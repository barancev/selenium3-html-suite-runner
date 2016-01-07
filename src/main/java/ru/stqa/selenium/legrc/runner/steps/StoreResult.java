package ru.stqa.selenium.legrc.runner.steps;

import ru.stqa.selenium.legrc.runner.*;

import java.util.List;

public class StoreResult extends AbstractStepWrapper {

  private Step step;
  private String varName;

  public StoreResult(Step step, String varName) {
    super(step);
    this.varName = varName;
  }

  public static class Factory implements StepWrapper.Factory {
    @Override
    public StepWrapper wrap(Step step, List<String> args) {
      return new StoreResult(step, args.get(2));
    }
  }

  @Override
  public boolean afterStep(RunContext ctx) {
    ctx.storeVar(varName, step.getOutcome());
    return true;
  }

}
