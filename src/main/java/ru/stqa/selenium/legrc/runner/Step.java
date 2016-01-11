package ru.stqa.selenium.legrc.runner;

import java.util.List;

public interface Step {
  interface Factory {
    Step create(List<String> args);
  }

  String getExtraArg();
  boolean run(RunContext ctx);
  StepOutcome getOutcome();
  String toHtml();
  String toHtml(String status);

  boolean breaksOnFailure();
}
