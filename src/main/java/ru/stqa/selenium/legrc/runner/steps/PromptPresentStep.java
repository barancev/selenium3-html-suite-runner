package ru.stqa.selenium.legrc.runner.steps;

import ru.stqa.selenium.legrc.runner.RunContext;
import ru.stqa.selenium.legrc.runner.Step;
import ru.stqa.selenium.legrc.runner.StepOutcome;

import java.util.List;

public class PromptPresentStep extends AbstractStep {

  public PromptPresentStep(List<String> args) {
    super(args);
  }

  public static class Factory implements Step.Factory {
    @Override
    public Step create(List<String> args) {
      return new PromptPresentStep(args);
    }
  }

  @Override
  public StepOutcome runInternal(RunContext ctx) {
    return new BooleanOutcome(ctx.getSelenium().isPromptPresent());
  }

}
