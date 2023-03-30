package com.ekryd.plugin.graphqlformatter.action;

import com.ekryd.plugin.graphqlformatter.format.GraphQLFormatter;
import com.ekryd.plugin.graphqlformatter.window.GraphQLToolWindow;
import com.intellij.openapi.actionSystem.AnAction;
import graphql.parser.InvalidSyntaxException;
import java.util.function.Function;
import org.apache.commons.lang3.StringUtils;

public abstract class AbstractGraphQLAction extends AnAction {

  protected final GraphQLToolWindow window;
  private final String actionName;

  public AbstractGraphQLAction(GraphQLToolWindow window, String actionName) {
    this.window = window;
    this.actionName = actionName;
  }

  public String getActionName() {
    return actionName;
  }

  protected void processText(Function<GraphQLFormatter, String> formatterFn) {
    String text = window.getText();

    if (StringUtils.isBlank(text)) {
      return;
    }

    try {
      var graphQLFormatter = new GraphQLFormatter(text);
      window.setText(formatterFn.apply(graphQLFormatter));
    } catch (InvalidSyntaxException invalidSyntaxException) {
      window.notifyError(invalidSyntaxException);
    }
  }
}
