package ru.stqa.selenium.legrc.runner;

import java.util.List;

public interface Step {
  interface Factory {
    Step create(List<String> args);
  }

  List<String> getArgs();
  int getArgAmount();
  String getExtraArg();
  boolean run(RunContext ctx);
  StepOutcome getOutcome();
  String toHtml();
  String toHtml(String status);

  boolean breaksOnFailure();
}
