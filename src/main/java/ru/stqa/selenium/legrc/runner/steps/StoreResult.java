package ru.stqa.selenium.legrc.runner.steps;

import ru.stqa.selenium.legrc.runner.*;

import java.util.List;

public class StoreResult implements ResultProcessor {

  private Step step;
  private String varName;

  public StoreResult(Step step, String varName) {
    this.step = step;
    this.varName = varName;
  }

  public static class Factory implements ResultProcessor.Factory {
    @Override
    public ResultProcessor wrap(Step step, List<String> args) {
      return new StoreResult(step, args.get(2));
    }
  }

  @Override
  public boolean run(RunContext ctx) {
    step.run(ctx);
    if (step instanceof HasStringResult) {
      ctx.storeVar(varName, ((HasStringResult) step).getResult());
    } else if (step instanceof HasNumberResult) {
      ctx.storeVar(varName, ((HasNumberResult) step).getResult().toString());
    } else if (step instanceof HasBooleanResult) {
      ctx.storeVar(varName, "" + ((HasBooleanResult) step).getResult());
    } else {
      return false;
    }
    return true;
  }

}
