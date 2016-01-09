package ru.stqa.selenium.legrc.runner.steps;

import ru.stqa.selenium.legrc.runner.StepOutcome;

public class NumberOutcome implements StepOutcome {

  private Number outcome;

  public NumberOutcome(Number outcome) {
    this.outcome = outcome;
  }

  @Override
  public boolean matches(Object expected) {
    if (expected instanceof Number) {
      return outcome.equals(expected);
    } else {
      try {
        return outcome.doubleValue() == Double.parseDouble(expected.toString());
      } catch (NumberFormatException ex) {
        return false;
      }
    }
  }

  @Override
  public String toString() {
    return "" + outcome;
  }
}
