package ru.stqa.selenium.legrc.runner.steps.accessors;

import ru.stqa.selenium.legrc.runner.RunContext;
import ru.stqa.selenium.legrc.runner.Step;
import ru.stqa.selenium.legrc.runner.StepOutcome;
import ru.stqa.selenium.legrc.runner.steps.AbstractStep;
import ru.stqa.selenium.legrc.runner.steps.StringOutcome;

import java.util.List;

public class AttributeStep extends AbstractStep {

  private String attributeLocator;

  public AttributeStep(List<String> args) {
    super(args, 1);
    this.attributeLocator = args.get(1);
  }

  public static class Factory implements Step.Factory {
    @Override
    public Step create(List<String> args) {
      return new AttributeStep(args);
    }
  }

  @Override
  public StepOutcome runInternal(RunContext ctx) {
    return new StringOutcome(ctx.getSelenium().getAttribute(ctx.substitute(attributeLocator)));
  }

}
