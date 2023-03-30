package com.ekryd.plugin.graphqlformatter.window;

import com.intellij.codeInsight.hint.HintManager;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.editor.LogicalPosition;
import com.intellij.openapi.editor.ScrollType;
import com.intellij.openapi.editor.ScrollingModel;
import com.intellij.openapi.editor.ex.EditorEx;
import com.intellij.openapi.fileTypes.FileTypes;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.ui.EditorTextField;
import graphql.parser.InvalidSyntaxException;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

public class GraphQLToolWindow {

  private EditorTextField editorTextArea;

  public GraphQLToolWindow() {}

  public EditorTextField getContent(@NotNull Project project) {
    // TODO: Filetype
    editorTextArea =
        new EditorTextField(
            EditorFactory.getInstance().createDocument(StringUtil.convertLineSeparators("")),
            project,
            FileTypes.UNKNOWN,
            false,
            false) {
          @Override
          protected @NotNull EditorEx createEditor() {
            var editor = super.createEditor();

            var settings = editor.getSettings();
            settings.setFoldingOutlineShown(true);
            settings.setLineNumbersShown(true);
            settings.setLineMarkerAreaShown(true);
            settings.setIndentGuidesShown(true);
            editor.setHorizontalScrollbarVisible(true);
            editor.setVerticalScrollbarVisible(true);

            return editor;
          }
        };

    return editorTextArea;
  }

  public String getText() {
    return editorTextArea.getText();
  }

  public void setText(String content) {
    editorTextArea.setText(content);
  }

  public void notifyError(InvalidSyntaxException invalidSyntaxException) {
    var message = invalidSyntaxException.getMessage();
    if (StringUtils.isBlank(message)) {
      return;
    }

    var location = invalidSyntaxException.getLocation();
    var editor = editorTextArea.getEditor();

    if (location != null && editor != null) {
      editor
          .getCaretModel()
          .moveToLogicalPosition(
              new LogicalPosition(location.getLine() - 1, location.getColumn() - 1));

      ScrollingModel scrollingModel = editor.getScrollingModel();
      scrollingModel.scrollToCaret(ScrollType.MAKE_VISIBLE);
      scrollingModel.runActionOnScrollingFinished(
          () -> HintManager.getInstance().showErrorHint(editor, message));
    }
  }

  public void notifyInfo(String message) {
    if (StringUtils.isBlank(message)) {
      return;
    }

    var editor = editorTextArea.getEditor();

    if (editor != null) {
      editor.getCaretModel().moveToOffset(-1);

      ScrollingModel scrollingModel = editor.getScrollingModel();
      scrollingModel.scrollToCaret(ScrollType.MAKE_VISIBLE);
      scrollingModel.runActionOnScrollingFinished(
          () -> HintManager.getInstance().showInformationHint(editor, message));
    }
  }
}
