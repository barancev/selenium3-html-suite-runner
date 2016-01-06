package ru.stqa.selenium.legrc.runner.steps;

import ru.stqa.selenium.legrc.runner.HasStringResult;
import ru.stqa.selenium.legrc.runner.RunContext;
import ru.stqa.selenium.legrc.runner.Step;

import java.util.List;

public class TextStep implements Step, HasStringResult {

  private String locator;
  private String result;

  public TextStep(String locator) {
    this.locator = locator;
  }

  @Override
  public String getResult() {
    return result;
  }

  public static class Factory implements Step.Factory {
    @Override
    public Step create(List<String> args) {
      return new TextStep(args.get(1));
    }
  }

  @Override
  public boolean run(RunContext ctx) {
    result = ctx.getWDBS().getText(ctx.substitute(locator)).replace('\n', ' ');
    return true;
  }

}
