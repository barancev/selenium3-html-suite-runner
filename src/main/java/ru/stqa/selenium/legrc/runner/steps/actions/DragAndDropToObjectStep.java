package ru.stqa.selenium.legrc.runner.steps.actions;

import ru.stqa.selenium.legrc.runner.RunContext;
import ru.stqa.selenium.legrc.runner.Step;
import ru.stqa.selenium.legrc.runner.StepOutcome;
import ru.stqa.selenium.legrc.runner.steps.AbstractStep;
import ru.stqa.selenium.legrc.runner.steps.VoidOutcome;

import java.util.List;

public class DragAndDropToObjectStep extends AbstractStep {

  private String locatorOfObjectToBeDragged;
  private String locatorOfDragDestinationObject;

  public DragAndDropToObjectStep(List<String> args) {
    super(args, 2);
    this.locatorOfObjectToBeDragged = args.get(1);
    this.locatorOfDragDestinationObject = args.get(2);
  }

  public static class Factory implements Step.Factory {
    @Override
    public Step create(List<String> args) {
      return new DragAndDropToObjectStep(args);
    }
  }

  @Override
  public StepOutcome runInternal(RunContext ctx) {
    ctx.getSelenium().dragAndDropToObject(ctx.substitute(locatorOfObjectToBeDragged), ctx.substitute(locatorOfDragDestinationObject));
    return new VoidOutcome();
  }
}
