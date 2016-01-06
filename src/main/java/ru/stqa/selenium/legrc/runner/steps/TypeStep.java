package ru.stqa.selenium.legrc.runner.steps;

import ru.stqa.selenium.legrc.runner.RunContext;
import ru.stqa.selenium.legrc.runner.Step;

import java.util.List;

public class TypeStep implements Step {

  private String locator;
  private String text;

  public TypeStep(String locator, String text) {
    this.locator = locator;
    this.text = text;
  }

  public static class Factory implements Step.Factory {
    @Override
    public Step create(List<String> args) {
      return new TypeStep(args.get(1), args.get(2));
    }
  }

  @Override
  public boolean run(RunContext ctx) {
    ctx.getWDBS().type(locator, ctx.substitute(text));
    return true;
  }
}