package com.ekryd.plugin.graphqlformatter.action;

import com.ekryd.plugin.graphqlformatter.format.GraphQLFormatter;
import com.ekryd.plugin.graphqlformatter.window.GraphQLToolWindow;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

public class CompressGraphQLAction extends AbstractGraphQLAction {

  public CompressGraphQLAction(GraphQLToolWindow window) {
    super(window, "Compress leaves of GraphQL queries");
  }

  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    processText(GraphQLFormatter::compressFormatted);
  }
}
