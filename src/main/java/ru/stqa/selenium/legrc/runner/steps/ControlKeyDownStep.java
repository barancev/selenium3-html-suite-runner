package ru.stqa.selenium.legrc.runner.steps;

import ru.stqa.selenium.legrc.runner.RunContext;
import ru.stqa.selenium.legrc.runner.Step;
import ru.stqa.selenium.legrc.runner.StepOutcome;

import java.util.List;

public class ControlKeyDownStep extends AbstractStep {

  public static class Factory implements Step.Factory {
    @Override
    public Step create(List<String> args) {
      return new ControlKeyDownStep();
    }
  }

  @Override
  public StepOutcome runInternal(RunContext ctx) {
    ctx.getWDBS().controlKeyDown();
    return new VoidOutcome();
  }
}
