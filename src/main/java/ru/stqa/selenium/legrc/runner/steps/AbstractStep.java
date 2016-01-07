package ru.stqa.selenium.legrc.runner.steps;

import ru.stqa.selenium.legrc.runner.RunContext;
import ru.stqa.selenium.legrc.runner.Step;
import ru.stqa.selenium.legrc.runner.StepOutcome;

import java.util.List;

public abstract class AbstractStep implements Step {

  private List<String> args;
  private StepOutcome outcome;
  protected boolean result = true;

  public AbstractStep(List<String> args) {
    this.args = args;
  }

  @Override
  public StepOutcome getOutcome() {
    return outcome;
  }

  @Override
  public boolean run(RunContext ctx) {
    try {
      outcome = runInternal(ctx);
    } catch (Throwable ex) {
      outcome = new ExceptionOutcome(ex);
      result = false;
    }
    return result;
  }

  protected abstract StepOutcome runInternal(RunContext ctx);

  @Override
  public String toHtml() {
    return toHtml(result ? "status_done" : "status_failed");
  }

  @Override
  public String toHtml(String status) {
    StringBuilder sb = new StringBuilder();
    sb.append(String.format("<tr class='step %s'>", status));
    sb.append(String.format("<td class='command'>%s</td>", args.get(0)));
    sb.append(String.format("<td class='arg1'>%s</td>", args.get(1)));
    sb.append(String.format("<td class='arg2'>%s</td>", args.get(2)));
    sb.append(String.format("<td class='outcome'>%s</td>", outcome));
    sb.append("<td class='time'>Time</td>");
    sb.append("</tr>");
    return sb.toString();
  }
}
