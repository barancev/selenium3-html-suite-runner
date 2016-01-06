package ru.stqa.selenium.legrc.runner.steps;

import ru.stqa.selenium.legrc.runner.Step;
import ru.stqa.selenium.legrc.runner.Step.Factory;

import java.util.List;

public class OpenStep implements Step {

  public static class Factory implements Step.Factory {

    @Override
    public Step create(List<String> args) {
      return new OpenStep();
    }
  }
}
