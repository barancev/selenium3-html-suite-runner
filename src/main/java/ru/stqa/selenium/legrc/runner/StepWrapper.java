package ru.stqa.selenium.legrc.runner;

import java.util.List;

public interface StepWrapper extends Step {
  interface Factory {
    StepWrapper wrap(Step step, List<String> args);
  }
}
