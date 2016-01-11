package ru.stqa.selenium.legrc.runner.steps.accessors;

import ru.stqa.selenium.legrc.runner.RunContext;
import ru.stqa.selenium.legrc.runner.Step;
import ru.stqa.selenium.legrc.runner.StepOutcome;
import ru.stqa.selenium.legrc.runner.steps.AbstractStep;
import ru.stqa.selenium.legrc.runner.steps.BooleanOutcome;

import java.util.List;

public class WhetherThisWindowMatchWindowExpressionStep extends AbstractStep {

  private String currentWindowString;
  private String target;

  public WhetherThisWindowMatchWindowExpressionStep(List<String> args) {
    super(args, 2);
    this.currentWindowString = args.get(1);
    this.target = args.get(2);
  }

  public static class Factory implements Step.Factory {
    @Override
    public Step create(List<String> args) {
      return new WhetherThisWindowMatchWindowExpressionStep(args);
    }
  }

  @Override
  public StepOutcome runInternal(RunContext ctx) {
    return new BooleanOutcome(ctx.getSelenium().getWhetherThisWindowMatchWindowExpression(ctx.substitute(currentWindowString), ctx.substitute(target)));
  }
}
