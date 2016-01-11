package ru.stqa.selenium.legrc.runner.steps.actions;

import ru.stqa.selenium.legrc.runner.RunContext;
import ru.stqa.selenium.legrc.runner.Step;
import ru.stqa.selenium.legrc.runner.StepOutcome;
import ru.stqa.selenium.legrc.runner.steps.AbstractStep;
import ru.stqa.selenium.legrc.runner.steps.VoidOutcome;

import java.util.List;

public class AddScriptStep extends AbstractStep {

  private String scriptContent;
  private String scriptTagId;

  public AddScriptStep(List<String> args) {
    super(args, 2);
    this.scriptContent = args.get(1);
    this.scriptTagId = args.get(2);
  }

  public static class Factory implements Step.Factory {
    @Override
    public Step create(List<String> args) {
      return new AddScriptStep(args);
    }
  }

  @Override
  public StepOutcome runInternal(RunContext ctx) {
    ctx.getSelenium().addScript(ctx.substitute(scriptContent), ctx.substitute(scriptTagId));
    return new VoidOutcome();
  }
}
