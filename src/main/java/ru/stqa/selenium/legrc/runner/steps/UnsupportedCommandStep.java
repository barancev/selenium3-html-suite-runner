package ru.stqa.selenium.legrc.runner.steps;

import ru.stqa.selenium.legrc.runner.RunContext;
import ru.stqa.selenium.legrc.runner.Step;
import ru.stqa.selenium.legrc.runner.StepOutcome;

import java.util.ArrayList;
import java.util.List;

public class UnsupportedCommandStep implements Step {

  private final List<String> args;

  public UnsupportedCommandStep(List<String> args) {
    this.args = new ArrayList<String>(args);
  }

  public String toString() {
    return "Unsupported command " + args.get(0);
  }

  @Override
  public boolean run(RunContext ctx) {
    return false;
  }

  @Override
  public StepOutcome getOutcome() {
    return new VoidOutcome();
  }

  @Override
  public boolean breaksOnFailure() {
    return false;
  }

  @Override
  public String toHtml() {
    StringBuilder sb = new StringBuilder();
    sb.append("<tr class='step'>");
    sb.append(String.format("<td class='step unsupported_command'>%s</td>", args.get(0)));
    sb.append(String.format("<td class='arg1'>%s</td>", args.get(1)));
    sb.append(String.format("<td class='arg2'>%s</td>", args.get(2)));
    sb.append(String.format("<td class='outcome'>Operation is not supported</td>"));
    sb.append("<td class='time'>Time</td>");
    sb.append("</tr>");
    return sb.toString();
  }

  @Override
  public String toHtml(String status) {
    return null;
  }
}
