package ru.stqa.selenium.legrc.runner.steps;

import ru.stqa.selenium.legrc.runner.StepWrapper;
import ru.stqa.selenium.legrc.runner.RunContext;
import ru.stqa.selenium.legrc.runner.Step;
import ru.stqa.selenium.legrc.runner.StepOutcome;

public class AbstractStepWrapper extends AbstractStep implements StepWrapper {

  protected Step step;

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
  public String toHtml() {
    return toHtml(executed ? (result ? "status_passed" : "status_failed") : "status_skipped");
  }
}
