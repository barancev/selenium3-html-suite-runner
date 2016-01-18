package ru.stqa.selenium.legrc.runner.steps;

import com.google.common.escape.Escaper;
import com.google.common.html.HtmlEscapers;
import ru.stqa.selenium.legrc.runner.RunContext;
import ru.stqa.selenium.legrc.runner.Step;
import ru.stqa.selenium.legrc.runner.StepOutcome;

import java.util.List;

public class AbstractStep implements Step {

  private long start;
  private long finish;
  private List<String> args;
  private int argAmount;
  protected StepOutcome outcome = new VoidOutcome();
  protected boolean result = true;
  protected boolean executed = false;

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
      result = true; // default; runInternal can overwrite the result
      outcome = runInternal(ctx);
    } catch (Throwable ex) {
      outcome = new ExceptionOutcome(ex);
      result = false;
    } finally {
      finish = System.currentTimeMillis();
    }
    executed = true;
    return result;
  }

  protected String coords(String s) {
    return (null == s || "".equals(s)) ? "0,0" : s;
  }

  protected StepOutcome runInternal(RunContext ctx) {
    throw new UnsupportedOperationException();
  }

  @Override
  public String toHtml() {
    return toHtml(executed ? (result ? "status_done" : "status_failed") : "status_skipped");
  }

  @Override
  public String toHtml(String status) {
    StringBuilder sb = new StringBuilder();
    Escaper escaper = HtmlEscapers.htmlEscaper();
    sb.append(String.format("<tr class='step %s'>", status));
    sb.append(String.format("<td class='command'>%s</td>", escaper.escape(getArgs().get(0))));
    sb.append(String.format("<td class='arg1'>%s</td>", escaper.escape(getArgs().get(1))));
    sb.append(String.format("<td class='arg2'>%s</td>", escaper.escape(getArgs().get(2))));
    sb.append(String.format("<td class='outcome'>%s</td>", escaper.escape(getOutcome().toString())));
    sb.append(String.format("<td class='time'>%d</td>", finish - start));
    sb.append("</tr>");
    return sb.toString();
  }
}
