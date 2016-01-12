package ru.stqa.selenium.legrc.runner.steps.actions;

import ru.stqa.selenium.legrc.runner.RunContext;
import ru.stqa.selenium.legrc.runner.Step;
import ru.stqa.selenium.legrc.runner.StepOutcome;
import ru.stqa.selenium.legrc.runner.steps.AbstractStep;
import ru.stqa.selenium.legrc.runner.steps.VoidOutcome;

import java.util.List;

public class RollupStep extends AbstractStep {

  private String rollupName;
  private String kwargs;

  public RollupStep(List<String> args) {
    super(args, 2);
    this.rollupName = args.get(1);
    this.kwargs = args.get(2);
  }

  public static class Factory implements Step.Factory {
    @Override
    public Step create(List<String> args) {
      return new RollupStep(args);
    }
  }

  @Override
  public StepOutcome runInternal(RunContext ctx) {
    ctx.getSelenium().rollup(ctx.substitute(rollupName), ctx.substitute(kwargs));
    return new VoidOutcome();
  }
}
