package ru.stqa.selenium.legrc.runner.steps.actions;

import ru.stqa.selenium.legrc.runner.RunContext;
import ru.stqa.selenium.legrc.runner.Step;
import ru.stqa.selenium.legrc.runner.StepOutcome;
import ru.stqa.selenium.legrc.runner.steps.AbstractStep;
import ru.stqa.selenium.legrc.runner.steps.VoidOutcome;

import java.util.List;

public class AddLocationStrategyStep extends AbstractStep {

  private String strategyName;
  private String functionDefinition;

  public AddLocationStrategyStep(List<String> args) {
    super(args, 2);
    this.strategyName = args.get(1);
    this.functionDefinition = args.get(2);
  }

  public static class Factory implements Step.Factory {
    @Override
    public Step create(List<String> args) {
      return new AddLocationStrategyStep(args);
    }
  }

  @Override
  public StepOutcome runInternal(RunContext ctx) {
    ctx.getSelenium().addLocationStrategy(ctx.substitute(strategyName), ctx.substitute(functionDefinition));
    return new VoidOutcome();
  }
}
