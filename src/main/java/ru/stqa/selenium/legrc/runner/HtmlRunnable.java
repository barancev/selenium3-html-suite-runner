package ru.stqa.selenium.legrc.runner;

public interface HtmlRunnable {

  boolean run(RunContext ctx);
  String toHtml();
}
