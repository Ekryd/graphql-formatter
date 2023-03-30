package com.ekryd.plugin.graphqlformatter.format;

import graphql.language.AstPrinter;
import graphql.language.CompressedAstPrinter;
import graphql.language.Document;
import graphql.language.PrettyAstPrinter;
import graphql.language.PrettyAstPrinter.PrettyPrinterOptions;
import graphql.parser.Parser;

public class GraphQLFormatter {

  private static final Parser PARSER = new Parser();
  private final Document document;
  private final String schemaDefinition;

  public GraphQLFormatter(String content) {
    this.document = PARSER.parseDocument(content);
    schemaDefinition = content;
  }

  public String expandFormatted() {
    return PrettyAstPrinter.print(schemaDefinition, PrettyPrinterOptions.defaultOptions());
  }

  public String collapseFormatted() {
    return AstPrinter.printAstCompact(document);
  }

  public String compressFormatted() {
    return CompressedAstPrinter.print(schemaDefinition);
  }
}
