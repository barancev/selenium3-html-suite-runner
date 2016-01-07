package ru.stqa.selenium.legrc.runner.steps;

import ru.stqa.selenium.legrc.runner.RunContext;
import ru.stqa.selenium.legrc.runner.Step;
import ru.stqa.selenium.legrc.runner.StepOutcome;

import java.util.List;

public class ShiftKeyDownStep extends AbstractStep {

  public ShiftKeyDownStep(List<String> args) {
    super(args);
  }

  public static class Factory implements Step.Factory {
    @Override
    public Step create(List<String> args) {
      return new ShiftKeyDownStep(args);
    }
  }

  @Override
  public StepOutcome runInternal(RunContext ctx) {
    ctx.getWDBS().shiftKeyDown();
    return new VoidOutcome();
  }
}
