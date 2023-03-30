package com.ekryd.plugin.graphqlformatter.window;

import com.ekryd.plugin.graphqlformatter.action.AbstractGraphQLAction;
import com.ekryd.plugin.graphqlformatter.action.CollapseGraphQLAction;
import com.ekryd.plugin.graphqlformatter.action.CompressGraphQLAction;
import com.ekryd.plugin.graphqlformatter.action.CopyContentAction;
import com.ekryd.plugin.graphqlformatter.action.ExpandGraphQLAction;
import com.ekryd.plugin.graphqlformatter.action.VerifyGraphQLAction;
import com.intellij.icons.AllIcons.Actions;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.openapi.wm.ex.ToolWindowEx;
import com.intellij.ui.content.ContentFactory;
import java.util.List;
import java.util.function.Function;
import javax.swing.Icon;
import org.jetbrains.annotations.NotNull;

public class GraphQLToolWindowFactory implements ToolWindowFactory {

  @Override
  public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
    var graphQLToolWindow = new GraphQLToolWindow();
    var contentFactory = ContentFactory.SERVICE.getInstance();
    var content = contentFactory.createContent(graphQLToolWindow.getContent(project), "", false);

    toolWindow.getContentManager().addContent(content);

    initAction(toolWindow, graphQLToolWindow);
  }

  private void initAction(ToolWindow toolWindow, GraphQLToolWindow window) {
    var collapseGraphQLAction =
        createAction(window, Actions.Collapseall, CollapseGraphQLAction::new);
    var expandGraphQLAction = createAction(window, Actions.Expandall, ExpandGraphQLAction::new);
    var compressGraphQLAction =
        createAction(window, Actions.PrettyPrint, CompressGraphQLAction::new);
    var verifyGraphQLAction = createAction(window, Actions.SetDefault, VerifyGraphQLAction::new);
    var copyContentAction = createAction(window, Actions.Copy, CopyContentAction::new);

    ToolWindowEx ex = (ToolWindowEx) toolWindow;

    ex.setTitleActions(
        List.of(
            collapseGraphQLAction,
            expandGraphQLAction,
            compressGraphQLAction,
            verifyGraphQLAction,
            copyContentAction));
  }

  private <T extends AbstractGraphQLAction> T createAction(
      GraphQLToolWindow window, Icon icon, Function<GraphQLToolWindow, T> constructor) {
    var action = constructor.apply(window);
    action.getTemplatePresentation().setIcon(icon);
    action.getTemplatePresentation().setText(action.getActionName());
    return action;
  }
}
