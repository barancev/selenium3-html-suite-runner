package ru.stqa.selenium.legrc.runner.steps.actions;

import ru.stqa.selenium.legrc.runner.RunContext;
import ru.stqa.selenium.legrc.runner.Step;
import ru.stqa.selenium.legrc.runner.StepOutcome;
import ru.stqa.selenium.legrc.runner.steps.AbstractStep;
import ru.stqa.selenium.legrc.runner.steps.VoidOutcome;

import java.util.List;

public class PauseStep extends AbstractStep {

  private long duration;

  public PauseStep(List<String> args) {
    super(args, 1);
    this.duration = Long.parseLong(args.get(1));
  }

  public static class Factory implements Step.Factory {
    @Override
    public Step create(List<String> args) {
      return new PauseStep(args);
    }
  }

  @Override
  public StepOutcome runInternal(RunContext ctx) {
    try {
      Thread.sleep(duration);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return new VoidOutcome();
  }
}
