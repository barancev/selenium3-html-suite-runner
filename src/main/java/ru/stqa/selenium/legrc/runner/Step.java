package ru.stqa.selenium.legrc.runner;

import java.util.List;

public interface Step {
  interface Factory {
    Step create(List<String> args);
  }

  boolean run(RunContext ctx);
  StepOutcome getOutcome();
  String toHtml();
}
