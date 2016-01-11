package ru.stqa.selenium.legrc.runner.steps;

import ru.stqa.selenium.legrc.runner.RunContext;
import ru.stqa.selenium.legrc.runner.Step;
import ru.stqa.selenium.legrc.runner.StepOutcome;
import ru.stqa.selenium.legrc.runner.WebDriverBackedSelenium;

import java.util.List;

public class SendKeysStep extends AbstractStep {

  private String locator;
  private String text;

  public SendKeysStep(List<String> args) {
    super(args, 2);
    this.locator = args.get(1);
    this.text = args.get(2);
  }

  public static class Factory implements Step.Factory {
    @Override
    public Step create(List<String> args) {
      return new SendKeysStep(args);
    }
  }

  @Override
  public StepOutcome runInternal(RunContext ctx) {
    ((WebDriverBackedSelenium) ctx.getSelenium()).getCommandProcessor().doCommand(
            "sendKeys", new String[] {ctx.substitute(locator), ctx.substitute(text)});
    return new VoidOutcome();
  }
}
