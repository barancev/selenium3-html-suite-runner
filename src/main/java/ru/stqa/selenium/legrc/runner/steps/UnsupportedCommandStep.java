package ru.stqa.selenium.legrc.runner.steps;

import ru.stqa.selenium.legrc.runner.RunContext;
import ru.stqa.selenium.legrc.runner.Step;
import ru.stqa.selenium.legrc.runner.StepOutcome;

import java.util.ArrayList;
import java.util.List;

public class UnsupportedCommandStep implements Step {

  private final List<String> args;

  public UnsupportedCommandStep(List<String> args) {
    this.args = new ArrayList<String>(args);
  }

  public String toString() {
    return "Unsupported command " + args.get(0);
  }

  @Override
  public boolean run(RunContext ctx) {
    return false;
  }

  @Override
  public StepOutcome getOutcome() {
    return new VoidOutcome();
  }
}
