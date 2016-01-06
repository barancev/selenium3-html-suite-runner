package ru.stqa.selenium.legrc.runner.steps;

import ru.stqa.selenium.legrc.runner.RunContext;
import ru.stqa.selenium.legrc.runner.Step;

import java.util.List;

public class MouseMoveAtStep implements Step {

  private String locator;
  private String coords;

  public MouseMoveAtStep(String locator, String coords) {
    this.locator = locator;
    this.coords = coords;
  }

  public static class Factory implements Step.Factory {
    @Override
    public Step create(List<String> args) {
      return new MouseMoveAtStep(args.get(1), args.get(2));
    }
  }

  @Override
  public boolean run(RunContext ctx) {
    ctx.getWDBS().mouseMoveAt(ctx.substitute(locator), ctx.substitute(coords));
    return true;
  }
}
