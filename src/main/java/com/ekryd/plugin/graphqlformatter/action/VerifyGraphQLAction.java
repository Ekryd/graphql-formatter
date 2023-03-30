package com.ekryd.plugin.graphqlformatter.action;

import com.ekryd.plugin.graphqlformatter.format.GraphQLFormatter;
import com.ekryd.plugin.graphqlformatter.window.GraphQLToolWindow;
import com.intellij.openapi.actionSystem.AnActionEvent;
import graphql.parser.InvalidSyntaxException;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

public class VerifyGraphQLAction extends AbstractGraphQLAction {

  public VerifyGraphQLAction(GraphQLToolWindow window) {
    super(window, "Verify GraphQL");
  }

  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    String text = window.getText();

    if (StringUtils.isBlank(text)) {
      return;
    }

    try {
      new GraphQLFormatter(text);
    } catch (InvalidSyntaxException invalidSyntaxException) {
      window.notifyError(invalidSyntaxException);
      return;
    }

    window.notifyInfo("GraphQL is valid");
  }
}
