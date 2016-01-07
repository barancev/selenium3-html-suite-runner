package ru.stqa.selenium.legrc.runner.steps;

import ru.stqa.selenium.legrc.runner.StepOutcome;

public class ExceptionOutcome implements StepOutcome {

  private Throwable ex;

  public ExceptionOutcome(Throwable ex) {
    this.ex = ex;
  }

  @Override
  public boolean matches(Object expected) {
    return false;
  }

  @Override
  public String toString() {
    return ex.getMessage();
  }
}
