package ru.stqa.selenium.legrc.runner.steps;

import ru.stqa.selenium.legrc.runner.RunContext;
import ru.stqa.selenium.legrc.runner.Step;
import ru.stqa.selenium.legrc.runner.StepOutcome;

import java.util.List;

public class AttributeStep extends AbstractStep {

  private String attributeLocator;

  public AttributeStep(List<String> args) {
    super(args);
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
    return new StringOutcome(ctx.getWDBS().getAttribute(ctx.substitute(attributeLocator)));
  }

}
