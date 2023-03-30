package graphql.language;

import static graphql.parser.ParserEnvironment.newParserEnvironment;

import graphql.parser.NodeToRuleCapturingParser;
import graphql.parser.NodeToRuleCapturingParser.ParserContext;
import graphql.parser.ParserEnvironment;
import java.util.List;
import java.util.stream.Collectors;

public class CompressedAstPrinter extends PrettyAstPrinter {

  public CompressedAstPrinter(ParserContext parserContext) {
    super(parserContext);
    replacePrinter(SelectionSet.class, compressedSelectionSet());
  }

  private NodePrinter<SelectionSet> compressedSelectionSet() {
    return (out, node) -> out.append(block(node.getSelections()));
  }

  private <T extends Node<?>> String block(List<T> nodes) {
    if (nodes == null || nodes.isEmpty()) {
      return "{}";
    }

    var hasSelectionSet =
        nodes.stream()
            .anyMatch(
                node -> {
                  if (node instanceof SelectionSetContainer<?>) {
                    var field = (SelectionSetContainer<?>) node;
                    return field.getSelectionSet() != null;
                  }
                  return false;
                });

    if (!hasSelectionSet) {
      return "{" + joinTight(nodes) + "}";
    }

    var line = indent(new StringBuilder().append("{\n").append(join(nodes)));
    var lastLine = line.substring(line.lastIndexOf("\n") + 1);
    if (onlyEndBracketsInLine(lastLine)) {
      return line + "}";
    }
    return line + "\n}";
  }

  private <T extends Node<?>> String joinTight(List<T> nodes) {
    var strings = nodes.stream().map(this::node).collect(Collectors.toList());

    return " " + String.join(" ", strings) + " ";
  }

  private StringBuilder indent(StringBuilder maybeString) {
    for (int i = 0; i < maybeString.length(); i++) {
      char c = maybeString.charAt(i);
      if (c == '\n') {
        var justEndBrackets = onlyEndBracketsInLine(maybeString.substring(i + 1));
        if (!justEndBrackets) {
          maybeString.replace(i, i + 1, "\n  ");
        }
      }
    }
    return maybeString;
  }

  private boolean onlyEndBracketsInLine(String lastLine) {
    return lastLine.trim().chars().allMatch(i -> i == '}');
  }

  private <T extends Node<?>> String join(List<T> nodes) {
    var strings = nodes.stream().map(this::node).collect(Collectors.toList());

    return String.join("\n", strings);
  }

  private String node(Node<?> node) {
    StringBuilder builder = new StringBuilder();
    NodePrinter<Node<?>> printer = _findPrinter(node, null);
    printer.print(builder, node);
    return builder.toString();
  }

  public static String print(String content) {
    NodeToRuleCapturingParser parser = new NodeToRuleCapturingParser();
    ParserEnvironment parserEnvironment = newParserEnvironment().document(content).build();
    Document document = parser.parseDocument(parserEnvironment);

    return new CompressedAstPrinter(parser.getParserContext()).print(document);
  }
}
