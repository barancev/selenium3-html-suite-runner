package ru.stqa.selenium.legrc.runner.steps;

import ru.stqa.selenium.legrc.runner.RunContext;
import ru.stqa.selenium.legrc.runner.Step;
import ru.stqa.selenium.legrc.runner.StepOutcome;

import java.util.ArrayList;
import java.util.List;

public class UnsupportedCommandStep extends AbstractStep {

  public UnsupportedCommandStep(List<String> args) {
    super(args, 0);
  }

  @Override
  public boolean breaksOnFailure() {
    return false;
  }

  @Override
  public StepOutcome getOutcome() {
    return new StringOutcome("Operation is not supported");
  }

}
