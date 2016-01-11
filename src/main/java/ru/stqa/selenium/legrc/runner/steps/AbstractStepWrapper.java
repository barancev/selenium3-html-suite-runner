package ru.stqa.selenium.legrc.runner.steps;

import ru.stqa.selenium.legrc.runner.StepWrapper;
import ru.stqa.selenium.legrc.runner.RunContext;
import ru.stqa.selenium.legrc.runner.Step;
import ru.stqa.selenium.legrc.runner.StepOutcome;

public class AbstractStepWrapper implements StepWrapper {

  protected Step step;
  protected boolean result = true;
  private StepOutcome outcome;

  public AbstractStepWrapper(Step step) {
    this.step = step;
  }

  @Override
  public String getExtraArg() {
    return null;
  }

  @Override
  public boolean run(RunContext ctx) {
    boolean stepResult = runStep(ctx);
    result = stepResult && afterStep(ctx);
    outcome = new BooleanOutcome(result);
    return result;
  }

  protected boolean runStep(RunContext ctx) {
    return step.run(ctx);
  }

  protected boolean afterStep(RunContext ctx) {
    return true;
  };

  @Override
  public StepOutcome getOutcome() {
    return outcome;
  }

  @Override
  public boolean breaksOnFailure() {
    return true;
  }

  @Override
  public String toHtml() {
    if (outcome == null) {
      return step.toHtml("");
    } else {
      return step.toHtml(result ? "status_passed" : "status_failed");
    }
  }

  @Override
  public String toHtml(String status) {
    return step.toHtml(status);
  }
}
