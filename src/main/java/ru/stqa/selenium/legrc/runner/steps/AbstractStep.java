package ru.stqa.selenium.legrc.runner.steps;

import ru.stqa.selenium.legrc.runner.RunContext;
import ru.stqa.selenium.legrc.runner.Step;
import ru.stqa.selenium.legrc.runner.StepOutcome;

public abstract class AbstractStep implements Step {

  private StepOutcome outcome;

  @Override
  public StepOutcome getOutcome() {
    return outcome;
  }

  @Override
  public boolean run(RunContext ctx) {
    outcome = runInternal(ctx);
    return true;
  }

  protected abstract StepOutcome runInternal(RunContext ctx);

}
