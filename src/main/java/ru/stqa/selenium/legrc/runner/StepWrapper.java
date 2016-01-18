package ru.stqa.selenium.legrc.runner;

public interface StepWrapper extends Step {
  interface Factory {
    StepWrapper wrap(Step step);
  }
}
