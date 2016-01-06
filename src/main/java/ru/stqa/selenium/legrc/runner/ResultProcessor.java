package ru.stqa.selenium.legrc.runner;

import java.util.List;

public interface ResultProcessor extends Step {
  interface Factory {
    ResultProcessor wrap(Step step, List<String> args);
  }
}
