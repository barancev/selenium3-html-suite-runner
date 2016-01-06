package ru.stqa.selenium.legrc.runner.steps;

import ru.stqa.selenium.legrc.runner.RunContext;
import ru.stqa.selenium.legrc.runner.Step;

import java.util.List;

public class KeyUpStep implements Step {

  private String locator;
  private String text;

  public KeyUpStep(String locator, String text) {
    this.locator = locator;
    this.text = text;
  }

  public static class Factory implements Step.Factory {
    @Override
    public Step create(List<String> args) {
      return new KeyUpStep(args.get(1), args.get(2));
    }
  }

  @Override
  public boolean run(RunContext ctx) {
    ctx.getWDBS().keyUp(locator, ctx.substitute(text));
    return true;
  }
}
