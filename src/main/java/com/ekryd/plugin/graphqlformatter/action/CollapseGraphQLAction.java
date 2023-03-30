package com.ekryd.plugin.graphqlformatter.action;

import com.ekryd.plugin.graphqlformatter.format.GraphQLFormatter;
import com.ekryd.plugin.graphqlformatter.window.GraphQLToolWindow;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

public class CollapseGraphQLAction extends AbstractGraphQLAction {

  public CollapseGraphQLAction(GraphQLToolWindow window) {
    super(window, "Collapse GraphQL");
  }

  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    processText(GraphQLFormatter::collapseFormatted);
  }
}
