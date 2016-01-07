package ru.stqa.selenium.legrc.runner.steps;

import ru.stqa.selenium.legrc.runner.RunContext;
import ru.stqa.selenium.legrc.runner.Step;
import ru.stqa.selenium.legrc.runner.StepOutcome;

import java.util.List;

public class MouseDownAtStep extends AbstractStep {

  private String locator;
  private String coords;

  public MouseDownAtStep(String locator, String coords) {
    this.locator = locator;
    this.coords = coords;
  }

  public static class Factory implements Step.Factory {
    @Override
    public Step create(List<String> args) {
      return new MouseDownAtStep(args.get(1), args.get(2));
    }
  }

  @Override
  public StepOutcome runInternal(RunContext ctx) {
    ctx.getWDBS().mouseDownAt(ctx.substitute(locator), ctx.substitute(coords));
    return new VoidOutcome();
  }
}
