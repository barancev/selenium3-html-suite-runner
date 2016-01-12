package ru.stqa.selenium.legrc.runner.steps.accessors;

import com.google.common.base.Joiner;
import ru.stqa.selenium.legrc.runner.RunContext;
import ru.stqa.selenium.legrc.runner.Step;
import ru.stqa.selenium.legrc.runner.StepOutcome;
import ru.stqa.selenium.legrc.runner.steps.AbstractStep;
import ru.stqa.selenium.legrc.runner.steps.StringOutcome;

import java.util.List;

public class SelectedValuesStep extends AbstractStep {

  private String selectLocator;

  public SelectedValuesStep(List<String> args) {
    super(args, 1);
    this.selectLocator = args.get(1);
  }

  public static class Factory implements Step.Factory {
    @Override
    public Step create(List<String> args) {
      return new SelectedValuesStep(args);
    }
  }

  @Override
  public StepOutcome runInternal(RunContext ctx) {
    return new StringOutcome(Joiner.on(",").join(ctx.getSelenium().getSelectedValues(ctx.substitute(selectLocator))));
  }

}
