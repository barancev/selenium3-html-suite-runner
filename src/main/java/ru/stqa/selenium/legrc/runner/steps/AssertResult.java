package ru.stqa.selenium.legrc.runner.steps;

import ru.stqa.selenium.legrc.runner.ResultProcessor;
import ru.stqa.selenium.legrc.runner.Step;

public class AssertResult implements ResultProcessor {

  public static class Factory implements ResultProcessor.Factory {

    @Override
    public ResultProcessor wrap(Step step) {
      return new AssertResult();
    }
  }

}
