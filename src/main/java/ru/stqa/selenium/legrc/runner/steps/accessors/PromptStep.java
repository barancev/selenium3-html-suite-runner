package ru.stqa.selenium.legrc.runner.steps.accessors;

import ru.stqa.selenium.legrc.runner.RunContext;
import ru.stqa.selenium.legrc.runner.Step;
import ru.stqa.selenium.legrc.runner.StepOutcome;
import ru.stqa.selenium.legrc.runner.steps.AbstractStep;
import ru.stqa.selenium.legrc.runner.steps.StringOutcome;

import java.util.List;

public class PromptStep extends AbstractStep {

  public PromptStep(List<String> args) {
    super(args, 0);
  }

  public static class Factory implements Step.Factory {
    @Override
    public Step create(List<String> args) {
      return new PromptStep(args);
    }
  }

  @Override
  public StepOutcome runInternal(RunContext ctx) {
    return new StringOutcome(ctx.getSelenium().getPrompt());
  }
}
