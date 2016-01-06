package ru.stqa.selenium.legrc.runner.steps;

import ru.stqa.selenium.legrc.runner.RunContext;
import ru.stqa.selenium.legrc.runner.Step;

import java.util.List;

public class MouseDownRightStep implements Step {

  private String locator;

  public MouseDownRightStep(String locator) {
    this.locator = locator;
  }

  public static class Factory implements Step.Factory {
    @Override
    public Step create(List<String> args) {
      return new MouseDownRightStep(args.get(1));
    }
  }

  @Override
  public boolean run(RunContext ctx) {
    ctx.getWDBS().mouseDownRight(ctx.substitute(locator));
    return true;
  }
}
