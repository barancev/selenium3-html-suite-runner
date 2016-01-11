package ru.stqa.selenium.legrc.runner.steps.accessors;

import ru.stqa.selenium.legrc.runner.RunContext;
import ru.stqa.selenium.legrc.runner.Step;
import ru.stqa.selenium.legrc.runner.StepOutcome;
import ru.stqa.selenium.legrc.runner.steps.AbstractStep;
import ru.stqa.selenium.legrc.runner.steps.StringOutcome;

import java.util.List;

public class EvalStep extends AbstractStep {

  private String script;

  public EvalStep(List<String> args) {
    super(args, 1);
    this.script = args.get(1);
  }

  public static class Factory implements Step.Factory {
    @Override
    public Step create(List<String> args) {
      return new EvalStep(args);
    }
  }

  @Override
  public StepOutcome runInternal(RunContext ctx) {
    return new StringOutcome(ctx.getSelenium().getEval(ctx.substitute(script)));
  }

}
