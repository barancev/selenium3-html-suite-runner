package ru.stqa.selenium.legrc.runner.steps.accessors;

import ru.stqa.selenium.legrc.runner.RunContext;
import ru.stqa.selenium.legrc.runner.Step;
import ru.stqa.selenium.legrc.runner.StepOutcome;
import ru.stqa.selenium.legrc.runner.steps.AbstractStep;
import ru.stqa.selenium.legrc.runner.steps.BooleanOutcome;

import java.util.List;

public class EditableStep extends AbstractStep {

  private String locator;

  public EditableStep(List<String> args) {
    super(args, 1);
    this.locator = args.get(1);
  }

  public static class Factory implements Step.Factory {
    @Override
    public Step create(List<String> args) {
      return new EditableStep(args);
    }
  }

  @Override
  public StepOutcome runInternal(RunContext ctx) {
    return new BooleanOutcome(ctx.getSelenium().isEditable(ctx.substitute(locator)));
  }

}
