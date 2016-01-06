package ru.stqa.selenium.legrc.runner.steps;

import ru.stqa.selenium.legrc.runner.RunContext;
import ru.stqa.selenium.legrc.runner.Step;

import java.util.List;

public class OpenStep implements Step {

  private String url;

  public OpenStep(String url) {
    this.url = url;
  }

  public static class Factory implements Step.Factory {
    @Override
    public Step create(List<String> args) {
      return new OpenStep(args.get(1));
    }
  }

  @Override
  public boolean run(RunContext ctx) {
    ctx.getWDBS().open(ctx.substitute(url));
    return true;
  }

}
