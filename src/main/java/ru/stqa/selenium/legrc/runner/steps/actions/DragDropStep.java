package ru.stqa.selenium.legrc.runner.steps.actions;

import ru.stqa.selenium.legrc.runner.RunContext;
import ru.stqa.selenium.legrc.runner.Step;
import ru.stqa.selenium.legrc.runner.StepOutcome;
import ru.stqa.selenium.legrc.runner.steps.AbstractStep;
import ru.stqa.selenium.legrc.runner.steps.VoidOutcome;

import java.util.List;

public class DragDropStep extends AbstractStep {

  private String locator;
  private String movementsString;

  public DragDropStep(List<String> args) {
    super(args, 2);
    this.locator = args.get(1);
    this.movementsString = args.get(2);
  }

  public static class Factory implements Step.Factory {
    @Override
    public Step create(List<String> args) {
      return new DragDropStep(args);
    }
  }

  @Override
  public StepOutcome runInternal(RunContext ctx) {
    ctx.getSelenium().dragdrop(ctx.substitute(locator), ctx.substitute(movementsString));
    return new VoidOutcome();
  }
}
