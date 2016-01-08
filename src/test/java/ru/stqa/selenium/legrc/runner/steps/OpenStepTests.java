package ru.stqa.selenium.legrc.runner.steps;

import com.google.common.collect.ImmutableList;
import org.junit.Test;

import java.util.List;

import static org.mockito.Mockito.verify;

public class OpenStepTests extends StepTestBase {

  @Test
  public void canOpenAPage() {
    List<String> args = new ImmutableList.Builder<String>()
            .add("open").add("/").build();
    new OpenStep(args).run(context);
    verify(context.getSelenium()).open("/");
  }
}
