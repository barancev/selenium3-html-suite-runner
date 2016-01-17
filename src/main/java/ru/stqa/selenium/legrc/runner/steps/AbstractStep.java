package ru.stqa.selenium.legrc.runner.steps;

import ru.stqa.selenium.legrc.runner.RunContext;
import ru.stqa.selenium.legrc.runner.Step;
import ru.stqa.selenium.legrc.runner.StepOutcome;

import java.util.List;

public class AbstractStep implements Step {

  private long start;
  private long finish;
  private List<String> args;
  private int argAmount;
  private StepOutcome outcome;
  protected boolean result = true;

  public AbstractStep(List<String> args, int argAmount) {
    this.args = args;
    this.argAmount = argAmount;
  }

  @Override
  public List<String> getArgs() {
    return args;
  }

  @Override
  public int getArgAmount() {
    return argAmount;
  }

  @Override
  public String getExtraArg() {
    return args.size() > argAmount + 1 ? args.get(argAmount + 1) : "";
  }

  @Override
  public boolean breaksOnFailure() {
    return true;
  }

  @Override
  public StepOutcome getOutcome() {
    return outcome;
  }

  @Override
  public boolean run(RunContext ctx) {
    start = System.currentTimeMillis();
    try {
      outcome = runInternal(ctx);
      result = true;
    } catch (Throwable ex) {
      outcome = new ExceptionOutcome(ex);
      result = false;
    } finally {
      finish = System.currentTimeMillis();
    }
    return result;
  }

  protected StepOutcome runInternal(RunContext ctx) {
    throw new UnsupportedOperationException();
  }

  @Override
  public String toHtml() {
    if (outcome == null) {
      return toHtml("");
    } else {
      return toHtml(result ? "status_done" : "status_failed");
    }
  }

  @Override
  public String toHtml(String status) {
    StringBuilder sb = new StringBuilder();
    sb.append(String.format("<tr class='step %s'>", status));
    sb.append(String.format("<td class='command'>%s</td>", args.get(0)));
    sb.append(String.format("<td class='arg1'>%s</td>", args.get(1)));
    sb.append(String.format("<td class='arg2'>%s</td>", args.get(2)));
    sb.append(String.format("<td class='outcome'>%s</td>", outcome));
    sb.append(String.format("<td class='time'>%d</td>", finish - start));
    sb.append("</tr>");
    return sb.toString();
  }
}
