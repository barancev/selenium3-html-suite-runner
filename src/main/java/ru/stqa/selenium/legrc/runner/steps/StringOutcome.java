package ru.stqa.selenium.legrc.runner.steps;

import ru.stqa.selenium.legrc.runner.StepOutcome;

public class StringOutcome implements StepOutcome {

  private final String outcome;

  public StringOutcome(String outcome) {
    this.outcome = outcome;
  }

  @Override
  public boolean matches(Object expected) {
    if (expected instanceof String) {
      return outcome.equals(expected);
    } else {
      return false;
    }
  }

  @Override
  public String toString() {
    return outcome;
  }
}
