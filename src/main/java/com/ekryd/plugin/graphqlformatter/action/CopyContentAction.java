package com.ekryd.plugin.graphqlformatter.action;

import com.ekryd.plugin.graphqlformatter.window.GraphQLToolWindow;
import com.intellij.openapi.actionSystem.AnActionEvent;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

public class CopyContentAction extends AbstractGraphQLAction {

  public CopyContentAction(GraphQLToolWindow window) {
    super(window, "Copy Content to Clipboard");
  }

  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    String text = window.getText();

    if (StringUtils.isNotBlank(text)) {
      Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(text), null);
      window.notifyInfo("Copied Content to Clipboard");
    }
  }
}
