package ru.stqa.selenium.legrc.runner.steps;

import com.google.common.collect.ImmutableList;
import org.junit.Test;
import ru.stqa.selenium.legrc.runner.Step;
import ru.stqa.selenium.legrc.runner.StepWrapper;

import java.util.List;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class StepTests extends StepTestBase {

  @Test
  public void testStepWithVoidOutcome() {
    List<String> args = new ImmutableList.Builder<String>()
            .add("click").add("id=test").build();

    Step step = new ClickStep(args);

    step.run(context);

    verify(context.getSelenium()).click(args.get(1));
    assertThat(step.getOutcome(), instanceOf(VoidOutcome.class));
  }

  @Test
  public void testStepWithBooleanTrueOutcome() {
    List<String> args = new ImmutableList.Builder<String>()
            .add("assertElementPresent").add("id=test").build();

    when(context.getSelenium().isElementPresent(args.get(1))).thenReturn(true);

    Step step = new ElementPresentStep(args);

    step.run(context);

    verify(context.getSelenium()).isElementPresent(args.get(1));
    assertThat(step.getOutcome(), instanceOf(BooleanOutcome.class));
    assertThat((step.getOutcome()).matches(true), is(true));
  }

  @Test
  public void testStepWithBooleanFalseOutcome() {
    List<String> args = new ImmutableList.Builder<String>()
            .add("assertElementPresent").add("id=test").build();

    when(context.getSelenium().isElementPresent(args.get(1))).thenReturn(false);

    Step step = new ElementPresentStep(args);

    step.run(context);

    verify(context.getSelenium()).isElementPresent(args.get(1));
    assertThat(step.getOutcome(), instanceOf(BooleanOutcome.class));
    assertThat((step.getOutcome()).matches(true), is(false));
  }

  @Test
  public void testStepWithStringOutcome() {
    List<String> args = new ImmutableList.Builder<String>()
            .add("assertText").add("id=test").add("test").build();

    when(context.getSelenium().getText(args.get(1))).thenReturn(args.get(2));

    Step step = new TextStep(args);

    step.run(context);

    verify(context.getSelenium()).getText(args.get(1));
    assertThat(step.getOutcome(), instanceOf(StringOutcome.class));
    assertThat((step.getOutcome()).matches(args.get(2)), is(true));
  }

  @Test
  public void testStepWithNumberOutcome() {
    List<String> args = new ImmutableList.Builder<String>()
            .add("assertCssCount").add("id=test").add("10").build();

    when(context.getSelenium().getCssCount(args.get(1))).thenReturn(10);

    Step step = new CssCountStep(args);

    step.run(context);

    verify(context.getSelenium()).getCssCount(args.get(1));
    assertThat(step.getOutcome(), instanceOf(NumberOutcome.class));
    assertThat((step.getOutcome()).matches(10), is(true));
  }

  @Test
  public void testAssertStepWrapperWithTrueBooleanOutcome() {
    List<String> args = new ImmutableList.Builder<String>()
            .add("assertElementPresent").add("id=test").build();

    when(context.getSelenium().isElementPresent(args.get(1))).thenReturn(true);

    StepWrapper step = new AssertResult(new ElementPresentStep(args), "");

    step.run(context);

    verify(context.getSelenium()).isElementPresent(args.get(1));
    assertThat(step.getOutcome(), instanceOf(BooleanOutcome.class));
    assertThat((step.getOutcome()).matches(true), is(true));
  }

  @Test
  public void testAssertStepWrapperWithFalseBooleanOutcome() {
    List<String> args = new ImmutableList.Builder<String>()
            .add("assertElementPresent").add("id=test").build();

    when(context.getSelenium().isElementPresent(args.get(1))).thenReturn(false);

    StepWrapper step = new AssertResult(new ElementPresentStep(args), "");

    step.run(context);

    verify(context.getSelenium()).isElementPresent(args.get(1));
    assertThat(step.getOutcome(), instanceOf(BooleanOutcome.class));
    assertThat((step.getOutcome()).matches(true), is(false));
  }

  @Test
  public void testAssertStepWrapperWithMatchingStringOutcome() {
    List<String> args = new ImmutableList.Builder<String>()
            .add("assertText").add("id=test").add("test").build();

    when(context.getSelenium().getText(args.get(1))).thenReturn(args.get(2));

    StepWrapper step = new AssertResult(new TextStep(args), args.get(2));

    step.run(context);

    verify(context.getSelenium()).getText(args.get(1));
    assertThat(step.getOutcome(), instanceOf(BooleanOutcome.class));
    assertThat((step.getOutcome()).matches(true), is(true));
  }

  @Test
  public void testAssertStepWrapperWithNonMatchingStringOutcome() {
    List<String> args = new ImmutableList.Builder<String>()
            .add("assertText").add("id=test").add("something").build();

    when(context.getSelenium().getText(args.get(1))).thenReturn("test");

    StepWrapper step = new AssertResult(new TextStep(args), args.get(2));

    step.run(context);

    verify(context.getSelenium()).getText(args.get(1));
    assertThat(step.getOutcome(), instanceOf(BooleanOutcome.class));
    assertThat((step.getOutcome()).matches(true), is(false));
  }

  @Test
  public void testAssertStepWrapperWithMatchingNumberOutcome() {
    List<String> args = new ImmutableList.Builder<String>()
            .add("assertCssCount").add("id=test").add("10").build();

    when(context.getSelenium().getCssCount(args.get(1))).thenReturn(10);

    StepWrapper step = new AssertResult(new CssCountStep(args), args.get(2));

    step.run(context);

    verify(context.getSelenium()).getCssCount(args.get(1));
    assertThat(step.getOutcome(), instanceOf(BooleanOutcome.class));
    assertThat((step.getOutcome()).matches(true), is(true));
  }

  @Test
  public void testAssertStepWrapperWithNonMatchingNumberOutcome() {
    List<String> args = new ImmutableList.Builder<String>()
            .add("assertCssCount").add("id=test").add("10").build();

    when(context.getSelenium().getCssCount(args.get(1))).thenReturn(20);

    StepWrapper step = new AssertResult(new CssCountStep(args), args.get(2));

    step.run(context);

    verify(context.getSelenium()).getCssCount(args.get(1));
    assertThat(step.getOutcome(), instanceOf(BooleanOutcome.class));
    assertThat((step.getOutcome()).matches(true), is(false));
  }

}
