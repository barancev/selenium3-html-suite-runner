package ru.stqa.selenium.legrc.runner.steps;

import ru.stqa.selenium.legrc.runner.RunContext;
import ru.stqa.selenium.legrc.runner.Step;
import ru.stqa.selenium.legrc.runner.StepOutcome;

import java.util.List;

public class EvalStep extends AbstractStep {

  private String script;

  public EvalStep(String script) {
    this.script = script;
  }

  public static class Factory implements Step.Factory {
    @Override
    public Step create(List<String> args) {
      return new EvalStep(args.get(1));
    }
  }

  @Override
  public StepOutcome runInternal(RunContext ctx) {
    return new StringOutcome(ctx.getWDBS().getEval(ctx.substitute(ctx.substitute(script))));
  }

}
