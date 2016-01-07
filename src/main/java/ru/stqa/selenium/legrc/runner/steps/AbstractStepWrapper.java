package ru.stqa.selenium.legrc.runner.steps;

import ru.stqa.selenium.legrc.runner.StepWrapper;
import ru.stqa.selenium.legrc.runner.RunContext;
import ru.stqa.selenium.legrc.runner.Step;
import ru.stqa.selenium.legrc.runner.StepOutcome;

public class AbstractStepWrapper implements StepWrapper {

  protected Step step;

  public AbstractStepWrapper(Step step) {
    this.step = step;
  }

  @Override
  public boolean run(RunContext ctx) {
    boolean stepResult = step.run(ctx);
    stepResult = afterStep(ctx) && stepResult;
    return stepResult;
  }

  protected boolean afterStep(RunContext ctx) {
    return true;
  };

  @Override
  public StepOutcome getOutcome() {
    return step.getOutcome();
  }
}
