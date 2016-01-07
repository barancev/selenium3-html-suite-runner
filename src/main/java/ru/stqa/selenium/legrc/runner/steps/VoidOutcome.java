package ru.stqa.selenium.legrc.runner.steps;

import ru.stqa.selenium.legrc.runner.StepOutcome;

public class VoidOutcome implements StepOutcome {

  @Override
  public boolean matches(Object expected) {
    return false;
  }

  @Override
  public String toString() {
    return "";
  }
}
