package ru.stqa.selenium.legrc.runner.steps;

import ru.stqa.selenium.legrc.runner.RunContext;
import ru.stqa.selenium.legrc.runner.Step;
import ru.stqa.selenium.legrc.runner.StepOutcome;

import java.util.List;

public abstract class AbstractStep implements Step {

  private List<String> args;
  private StepOutcome outcome;

  public AbstractStep(List<String> args) {
    this.args = args;
  }

  @Override
  public StepOutcome getOutcome() {
    return outcome;
  }

  @Override
  public boolean run(RunContext ctx) {
    outcome = runInternal(ctx);
    return true;
  }

  protected abstract StepOutcome runInternal(RunContext ctx);

  @Override
  public String toHtml() {
    StringBuilder sb = new StringBuilder();
    sb.append("<tr class='step'>");
    sb.append(String.format("<td class='command'>%s</td>", args.get(0)));
    sb.append(String.format("<td class='arg1'>%s</td>", args.get(1)));
    sb.append(String.format("<td class='arg2'>%s</td>", args.get(2)));
    sb.append(String.format("<td class='outcome'>%s</td>", outcome));
    sb.append("<td class='time'>Time</td>");
    sb.append("</tr>");
    return sb.toString();
  }
}
