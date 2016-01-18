package ru.stqa.selenium.legrc.runner.steps;

import ru.stqa.selenium.legrc.runner.RunContext;

import java.util.Collections;

public class CommentStep extends AbstractStep {

  private String text;

  public CommentStep(String text) {
    super(Collections.singletonList(text), 1);
    this.text = text;
  }

  @Override
  public boolean run(RunContext ctx) {
    return true;
  }

  @Override
  public boolean breaksOnFailure() {
    return false;
  }

  @Override
  public String toHtml() {
    return String.format("<tr class='step comment'><td colspan='5'>%s</td></tr>", text);
  }

  @Override
  public String toHtml(String status) {
    return toHtml();
  }

}
