package ru.stqa.selenium.legrc.runner;

public interface ResultProcessor extends Step {
  interface Factory {
    ResultProcessor wrap(Step step);
  }
}
