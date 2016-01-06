package ru.stqa.selenium.legrc.runner.steps;

import ru.stqa.selenium.legrc.runner.RunContext;
import ru.stqa.selenium.legrc.runner.Step;

import java.util.List;

public class PauseStep implements Step {

  private long duration;

  public PauseStep(long duration) {
    this.duration = duration;
  }

  public static class Factory implements Step.Factory {
    @Override
    public Step create(List<String> args) {
      return new PauseStep(Long.parseLong(args.get(1)));
    }
  }

  @Override
  public boolean run(RunContext ctx) {
    try {
      Thread.sleep(duration);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return true;
  }
}
