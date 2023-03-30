package com.ekryd.plugin.graphqlformatter.action;

import com.ekryd.plugin.graphqlformatter.format.GraphQLFormatter;
import com.ekryd.plugin.graphqlformatter.window.GraphQLToolWindow;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

public class ExpandGraphQLAction extends AbstractGraphQLAction {

  public ExpandGraphQLAction(GraphQLToolWindow window) {
    super(window, "Expand GraphQL");
  }

  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    processText(GraphQLFormatter::expandFormatted);
  }
}
