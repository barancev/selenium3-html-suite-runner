package ru.stqa.selenium.legrc.runner.steps.accessors;

import ru.stqa.selenium.legrc.runner.RunContext;
import ru.stqa.selenium.legrc.runner.Step;
import ru.stqa.selenium.legrc.runner.StepOutcome;
import ru.stqa.selenium.legrc.runner.steps.AbstractStep;
import ru.stqa.selenium.legrc.runner.steps.BooleanOutcome;

import java.util.List;

public class WhetherThisFrameMatchFrameExpressionStep extends AbstractStep {

  private String currentFrameString;
  private String target;

  public WhetherThisFrameMatchFrameExpressionStep(List<String> args) {
    super(args, 2);
    this.currentFrameString = args.get(1);
    this.target = args.get(2);
  }

  public static class Factory implements Step.Factory {
    @Override
    public Step create(List<String> args) {
      return new WhetherThisFrameMatchFrameExpressionStep(args);
    }
  }

  @Override
  public StepOutcome runInternal(RunContext ctx) {
    return new BooleanOutcome(ctx.getSelenium().getWhetherThisFrameMatchFrameExpression(ctx.substitute(currentFrameString), ctx.substitute(target)));
  }
}
