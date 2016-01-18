package ru.stqa.selenium.legrc.runner.steps;

import com.google.common.collect.ImmutableMap;
import ru.stqa.selenium.legrc.runner.Step;
import ru.stqa.selenium.legrc.runner.StepWrapper;
import ru.stqa.selenium.legrc.runner.steps.accessors.*;
import ru.stqa.selenium.legrc.runner.steps.actions.*;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StepFactory {

  public Step createStep(List<String> args) {
    String command = args.get(0).toLowerCase();

    if ("waitforpagetoload".equals(command.toLowerCase())) {
      return new WaitForPageToLoadStep.Factory().create(args);
    } else if ("waitforpopup".equals(command.toLowerCase())) {
      return new WaitForPopUpStep.Factory().create(args);
    } else if ("waitforframetoload".equals(command.toLowerCase())) {
      return new WaitForFrameToLoadStep.Factory().create(args);
    } else if ("waitforcondition".equals(command.toLowerCase())) {
      return new WaitForConditionStep.Factory().create(args);
    } else if ("store".equals(command.toLowerCase())) {
      return new ExpressionStep.Factory().create(args);
    }

    String resultProcessor = null;
    Pattern p = Pattern.compile("(store|assert|verify|waitfor)(.*)");
    Matcher m = p.matcher(command);
    if (m.matches()) {
      resultProcessor = m.group(1);
      command = m.group(2);
    }

    Pattern p2 = Pattern.compile("(.*)(andwait)");
    Matcher m2 = p2.matcher(command);
    if (m2.matches()) {
      resultProcessor = m2.group(2);
      command = m2.group(1);
    }

    Step.Factory factory = stepFactories.get(command);
    if (factory == null) {
      return new UnsupportedCommandStep(args);
    }

    Step step = factory.create(args);

    if (resultProcessor != null) {
      step = resultProcessorFactories.get(resultProcessor).wrap(step);
    }

    return step;
  }

  private Map<String, Step.Factory> stepFactories = new ImmutableMap.Builder<String, Step.Factory>()
          .put("addlocationstrategy", new AddLocationStrategyStep.Factory())
          .put("addscript", new AddScriptStep.Factory())
          .put("addselection", new AddSelectionStep.Factory())
          .put("alert", new AlertStep.Factory())
          .put("alertpresent", new AlertPresentStep.Factory())
          .put("alertnotpresent", new AlertNotPresentStep.Factory())
          .put("allownativexpath", new AllowNativeXpathStep.Factory())
          .put("altkeydown", new AltKeyDownStep.Factory())
          .put("altkeyup", new AltKeyUpStep.Factory())
          .put("answeronnextprompt", new AnswerOnNextPromptStep.Factory())
          .put("assignid", new AssignIdStep.Factory())
          .put("attribute", new AttributeStep.Factory())
          .put("attributefromallwindows", new AttributeFromAllWindowsStep.Factory())
          .put("bodytext", new BodyTextStep.Factory())
          .put("captureentirepagescreenshot", new CaptureEntirePageScreenshot.Factory())
          .put("checked", new CheckedStep.Factory())
          .put("check", new CheckStep.Factory())
          .put("choosecancelonnextconfirmation", new ChooseCancelOnNextConfirmationStep.Factory())
          .put("chooseokonnextconfirmation", new ChooseOkOnNextConfirmationStep.Factory())
          .put("close", new CloseStep.Factory())
          .put("click", new ClickStep.Factory())
          .put("clickat", new ClickAtStep.Factory())
          .put("confirmation", new ConfirmationStep.Factory())
          .put("confirmationpresent", new ConfirmationPresentStep.Factory())
          .put("confirmationnotpresent", new ConfirmationNotPresentStep.Factory())
          .put("contextmenu", new ContextMenuStep.Factory())
          .put("contextmenuat", new ContextMenuAtStep.Factory())
          .put("controlkeydown", new ControlKeyDownStep.Factory())
          .put("controlkeyup", new ControlKeyUpStep.Factory())
          .put("cookie", new CookieStep.Factory())
          .put("cookiebyname", new CookieByNameStep.Factory())
          .put("cookiepresent", new CookiePresentStep.Factory())
          .put("cookienotpresent", new CookieNotPresentStep.Factory())
          .put("createcookie", new CreateCookieStep.Factory())
          .put("csscount", new CssCountStep.Factory())
          .put("cursorposition", new CursorPositionStep.Factory())
          .put("deleteallvisiblecookies", new DeleteAllVisibleCookiesStep.Factory())
          .put("deletecookie", new DeleteCookieStep.Factory())
          .put("deselectpopup", new DeselectPopUpStep.Factory())
          .put("doubleclick", new DoubleClickStep.Factory())
          .put("doubleclickat", new DoubleClickAtStep.Factory())
          .put("draganddrop", new DragAndDropStep.Factory())
          .put("draganddroptoobject", new DragAndDropToObjectStep.Factory())
          .put("dragdrop", new DragDropStep.Factory())
          .put("echo", new EchoStep.Factory())
          .put("editable", new EditableStep.Factory())
          .put("elementheight", new ElementHeightStep.Factory())
          .put("elementpresent", new ElementPresentStep.Factory())
          .put("elementnotpresent", new ElementNotPresentStep.Factory())
          .put("elementpositionleft", new ElementPositionLeftStep.Factory())
          .put("elementpositiontop", new ElementPositionTopStep.Factory())
          .put("elementwidth", new ElementWidthStep.Factory())
          .put("eval", new EvalStep.Factory())
          .put("expression", new ExpressionStep.Factory())
          .put("fireevent", new FireEventStep.Factory())
          .put("focus", new FocusStep.Factory())
          .put("goback", new GoBackStep.Factory())
          .put("highlight", new HighlightStep.Factory())
          .put("htmlsource", new HtmlSourceStep.Factory())
          .put("ignoreattributeswithoutvalue", new IgnoreAttributesWithoutValueStep.Factory())
          .put("keydown", new KeyDownStep.Factory())
          .put("keypress", new KeyPressStep.Factory())
          .put("keyup", new KeyUpStep.Factory())
          .put("location", new LocationStep.Factory())
          .put("metakeydown", new MetaKeyDownStep.Factory())
          .put("metakeyup", new MetaKeyUpStep.Factory())
          .put("mousedown", new MouseDownStep.Factory())
          .put("mousedownright", new MouseDownRightStep.Factory())
          .put("mousedownat", new MouseDownAtStep.Factory())
          .put("mousedownrightat", new MouseDownRightAtStep.Factory())
          .put("mousemove", new MouseMoveStep.Factory())
          .put("mousemoveat", new MouseMoveAtStep.Factory())
          .put("mouseout", new MouseOutStep.Factory())
          .put("mouseover", new MouseOverStep.Factory())
          .put("mousespeed", new MouseSpeedStep.Factory())
          .put("mouseup", new MouseUpStep.Factory())
          .put("mouseupright", new MouseUpRightStep.Factory())
          .put("mouseupat", new MouseUpAtStep.Factory())
          .put("mouseuprightat", new MouseUpRightAtStep.Factory())
          .put("open", new OpenStep.Factory())
          .put("openwindow", new OpenWindowStep.Factory())
          .put("ordered", new OrderedStep.Factory())
          .put("pause", new PauseStep.Factory())
          .put("prompt", new PromptStep.Factory())
          .put("promptpresent", new PromptPresentStep.Factory())
          .put("promptnotpresent", new PromptNotPresentStep.Factory())
          .put("refresh", new RefreshStep.Factory())
          .put("removeallselections", new RemoveAllSelectionsStep.Factory())
          .put("removescript", new RemoveScriptStep.Factory())
          .put("removeselection", new RemoveSelectionStep.Factory())
          .put("rollup", new RollupStep.Factory())
          .put("runscript", new RunScriptStep.Factory())
          .put("table", new TableStep.Factory())
          .put("text", new TextStep.Factory())
          .put("textpresent", new TextPresentStep.Factory())
          .put("textnotpresent", new TextNotPresentStep.Factory())
          .put("title", new TitleStep.Factory())
          .put("type", new TypeStep.Factory())
          .put("typekeys", new TypeKeysStep.Factory())
          .put("select", new SelectStep.Factory())
          .put("selectedid", new SelectedIdStep.Factory())
          .put("selectedids", new SelectedIdsStep.Factory())
          .put("selectedindex", new SelectedIndexStep.Factory())
          .put("selectedindexes", new SelectedIndexesStep.Factory())
          .put("selectedlabel", new SelectedLabelStep.Factory())
          .put("selectedlabels", new SelectedLabelsStep.Factory())
          .put("selectedvalue", new SelectedValueStep.Factory())
          .put("selectedvalues", new SelectedValuesStep.Factory())
          .put("selectframe", new SelectFrameStep.Factory())
          .put("selectoptions", new SelectOptionsStep.Factory())
          .put("selectpopup", new SelectPopUpStep.Factory())
          .put("selectwindow", new SelectWindowStep.Factory())
          .put("sendkeys", new SendKeysStep.Factory())
          .put("setbrowserloglevel", new SetBrowserLogLevelStep.Factory())
          .put("setcursorposition", new SetCursorPositionStep.Factory())
          .put("setspeed", new SetSpeedStep.Factory())
          .put("setmousespeed", new SetMouseSpeedStep.Factory())
          .put("settimeout", new SetTimeoutStep.Factory())
          .put("shiftkeydown", new ShiftKeyDownStep.Factory())
          .put("shiftkeyup", new ShiftKeyUpStep.Factory())
          .put("somethingselected", new SomethingSelectedStep.Factory())
          .put("speed", new SpeedStep.Factory())
          .put("storeallbuttons", new AllButtonsStep.Factory())
          .put("storeallfields", new AllFieldsStep.Factory())
          .put("storealllinks", new AllLinksStep.Factory())
          .put("storeallwindowids", new AllWindowIdsStep.Factory())
          .put("storeallwindownames", new AllWindowNamesStep.Factory())
          .put("storeallwindowtitles", new AllWindowTitlesStep.Factory())
          .put("submit", new SubmitStep.Factory())
          .put("uncheck", new UncheckStep.Factory())
          .put("usexpathlibrary", new UseXpathLibraryStep.Factory())
          .put("value", new ValueStep.Factory())
          .put("visible", new VisibleStep.Factory())
          .put("whetherthisframematchframeexpression", new WhetherThisFrameMatchFrameExpressionStep.Factory())
          .put("whetherthiswindowmatchwindowexpression", new WhetherThisWindowMatchWindowExpressionStep.Factory())
          .put("windowfocus", new WindowFocusStep.Factory())
          .put("windowmaximize", new WindowMaximizeStep.Factory())
          .put("xpathcount", new XpathCountStep.Factory())
          .build();

  private Map<String, StepWrapper.Factory> resultProcessorFactories = new ImmutableMap.Builder<String, StepWrapper.Factory>()
          .put("assert", new AssertResult.Factory())
          .put("assertnot", new AssertNotResult.Factory())
          .put("verify", new VerifyResult.Factory())
          .put("store", new StoreResult.Factory())
          .put("waitfor", new WaitForResult.Factory())
          .put("waitfornot", new WaitForNotResult.Factory())
          .put("andwait", new AndWaitResult.Factory())
          .build();

}
