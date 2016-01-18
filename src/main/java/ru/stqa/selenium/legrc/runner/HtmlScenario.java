package ru.stqa.selenium.legrc.runner;

import org.w3c.dom.Node;
import ru.stqa.selenium.legrc.runner.steps.CommentStep;
import ru.stqa.selenium.legrc.runner.steps.StepFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HtmlScenario implements HtmlRunnable {

  private static long counter = 0;
  private String name;
  private String path;
  private StepFactory stepFactory;
  private long id;
  private List<Step> steps = new ArrayList<Step>();
  boolean result = true;
  private long start;
  private long finish;

  public HtmlScenario(String path, StepFactory stepFactory) {
    this.path = path;
    this.stepFactory = stepFactory;
    this.id = ++counter;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public long getId() {
    return id;
  }

  public void addStep(Step step) {
    steps.add(step);
  }

  public long getDuration() {
    return finish - start;
  }

  public boolean getResult() {
    return result;
  }

  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append(name);
    builder.append("\n");
    for (Step step : steps) {
      builder.append(step);
      builder.append("\n");
    }
    return builder.toString();
  }

  protected void loadFrom(Node table) {
    Node thead = HtmlParserUtils.findChildElement(table, "THEAD");
    setName(thead.getTextContent().trim());

    Node tbody = HtmlParserUtils.findChildElement(table, "TBODY");
    Node row = tbody.getFirstChild();
    while (row != null) {
      if (row.getNodeType() == Node.ELEMENT_NODE) {
        List<String> args = new ArrayList<String>();
        Node cell = row.getFirstChild();
        while (cell != null) {
          if (cell.getNodeType() == Node.ELEMENT_NODE) {
            args.add(cell.getTextContent().trim());
          }
          cell = cell.getNextSibling();
        }
        addStep(stepFactory.createStep(args));
      } else if (row.getNodeType() == Node.COMMENT_NODE) {
        addStep(new CommentStep(row.getTextContent()));
      }
      row = row.getNextSibling();
    }
  }

  public boolean run(RunContext ctx) {
    start = System.currentTimeMillis();
    System.out.print("\n" + name + " ");
    result = true;
    for (Step step : steps) {
      boolean stepResult = step.run(ctx);
      result = stepResult && result;
      if (stepResult) {
        System.out.print(".");
      } else {
        if (!step.breaksOnFailure()) {
          System.out.print("F");
        } else {
          System.out.print("X");
          break;
        }
      }
    }
    finish = System.currentTimeMillis();
    return result;
  }

  @Override
  public String toHtml() {
    StringBuilder sb = new StringBuilder();
    sb.append("<div class='scenario'>\n");
    sb.append(String.format("<h3><a name='s%s'></a>Scenario &quot;%s&quot; (%s)</h3>\n", id, name, path));
    sb.append(String.format("<p>Started at %s</p>\n", new Date(start)));
    sb.append("<table class='scenario' border='1' cellpadding='1' cellspacing='1'>\n");
    sb.append("<thead><tr><th>Command</th><th>Arg1</th><th>Arg2</th><th>Result</th><th>Time&nbsp;(ms)</th></tr></thead>\n");
    sb.append("<tbody>\n");
    for (Step step : steps) {
      sb.append(step.toHtml());
      sb.append("\n");
    }
    sb.append(String.format("<tr><th colspan='4' style='text-align: right'>Total:</th><th>%d</th></tr>\n", getDuration()));
    sb.append("</tbody></table>\n");
    sb.append("</div>\n");
    return sb.toString();
  }
}
