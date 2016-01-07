package ru.stqa.selenium.legrc.runner.steps;

import ru.stqa.selenium.legrc.runner.StepOutcome;

public class BooleanOutcome implements StepOutcome {
  private boolean outcome;

  public BooleanOutcome(boolean outcome) {
    this.outcome = outcome;
  }

  @Override
  public boolean matches(Object expected) {
    if (expected instanceof Boolean) {
      return outcome == (Boolean) expected;
    } else {
      return false;
    }
  }
}
