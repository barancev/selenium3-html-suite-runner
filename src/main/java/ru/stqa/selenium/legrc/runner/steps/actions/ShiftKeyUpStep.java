package ru.stqa.selenium.legrc.runner.steps.actions;

import ru.stqa.selenium.legrc.runner.RunContext;
import ru.stqa.selenium.legrc.runner.Step;
import ru.stqa.selenium.legrc.runner.StepOutcome;
import ru.stqa.selenium.legrc.runner.steps.AbstractStep;
import ru.stqa.selenium.legrc.runner.steps.VoidOutcome;

import java.util.List;

public class ShiftKeyUpStep extends AbstractStep {

  public ShiftKeyUpStep(List<String> args) {
    super(args, 0);
  }

  public static class Factory implements Step.Factory {
    @Override
    public Step create(List<String> args) {
      return new ShiftKeyUpStep(args);
    }
  }

  @Override
  public StepOutcome runInternal(RunContext ctx) {
    ctx.getSelenium().shiftKeyUp();
    return new VoidOutcome();
  }
}
