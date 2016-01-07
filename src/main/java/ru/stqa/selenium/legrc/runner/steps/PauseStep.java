package ru.stqa.selenium.legrc.runner.steps;

import ru.stqa.selenium.legrc.runner.RunContext;
import ru.stqa.selenium.legrc.runner.Step;
import ru.stqa.selenium.legrc.runner.StepOutcome;

import java.util.List;

public class PauseStep extends AbstractStep {

  private long duration;

  public PauseStep(List<String> args) {
    super(args);
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
