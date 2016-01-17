package ru.stqa.selenium.legrc.runner.steps;

import ru.stqa.selenium.legrc.runner.StepWrapper;
import ru.stqa.selenium.legrc.runner.RunContext;
import ru.stqa.selenium.legrc.runner.Step;
import ru.stqa.selenium.legrc.runner.StepOutcome;

public class AbstractStepWrapper extends AbstractStep implements StepWrapper {

  protected Step step;
  protected boolean result = true;
  private StepOutcome outcome;

  public AbstractStepWrapper(Step step) {
    super(step.getArgs(), step.getArgAmount());
    this.step = step;
  }

  @Override
  public String getExtraArg() {
    return super.getExtraArg();
  }

  @Override
  public StepOutcome runInternal(RunContext ctx) {
    boolean stepResult = runStep(ctx);
    result = stepResult && afterStep(ctx);
    outcome = new BooleanOutcome(result);
    return outcome;
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
  public String toHtml() {
    if (outcome == null) {
      return super.toHtml("");
    } else {
      return super.toHtml(result ? "status_passed" : "status_failed");
    }
  }
}
