package ru.stqa.selenium.legrc.runner.steps;

import ru.stqa.selenium.legrc.runner.ResultProcessor;
import ru.stqa.selenium.legrc.runner.RunContext;
import ru.stqa.selenium.legrc.runner.Step;

import java.util.List;

public class WaitForResult implements ResultProcessor {

  public static class Factory implements ResultProcessor.Factory {
    @Override
    public ResultProcessor wrap(Step step, List<String> args) {
      return new WaitForResult();
    }
  }

  @Override
  public boolean run(RunContext ctx) {
    return false;
  }

}
