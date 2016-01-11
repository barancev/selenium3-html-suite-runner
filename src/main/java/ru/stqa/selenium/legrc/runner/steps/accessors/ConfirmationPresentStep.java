package ru.stqa.selenium.legrc.runner.steps.accessors;

import ru.stqa.selenium.legrc.runner.RunContext;
import ru.stqa.selenium.legrc.runner.Step;
import ru.stqa.selenium.legrc.runner.StepOutcome;
import ru.stqa.selenium.legrc.runner.steps.AbstractStep;
import ru.stqa.selenium.legrc.runner.steps.BooleanOutcome;

import java.util.List;

public class ConfirmationPresentStep extends AbstractStep {

  public ConfirmationPresentStep(List<String> args) {
    super(args, 0);
  }

  public static class Factory implements Step.Factory {
    @Override
    public Step create(List<String> args) {
      return new ConfirmationPresentStep(args);
    }
  }

  @Override
  public StepOutcome runInternal(RunContext ctx) {
    return new BooleanOutcome(ctx.getSelenium().isConfirmationPresent());
  }

}
