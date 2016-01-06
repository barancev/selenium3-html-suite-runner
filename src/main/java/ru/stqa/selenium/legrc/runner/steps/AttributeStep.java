package ru.stqa.selenium.legrc.runner.steps;

import ru.stqa.selenium.legrc.runner.HasStringResult;
import ru.stqa.selenium.legrc.runner.RunContext;
import ru.stqa.selenium.legrc.runner.Step;

import java.util.List;

public class AttributeStep implements Step, HasStringResult {

  private String attributeLocator;
  private String result;

  public AttributeStep(String attributeLocator) {
    this.attributeLocator = attributeLocator;
  }

  @Override
  public String getResult() {
    return result;
  }

  public static class Factory implements Step.Factory {
    @Override
    public Step create(List<String> args) {
      return new AttributeStep(args.get(1));
    }
  }

  @Override
  public boolean run(RunContext ctx) {
    result = ctx.getWDBS().getAttribute(attributeLocator);
    return true;
  }

}
