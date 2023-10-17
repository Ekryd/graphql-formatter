package com.ekryd.plugin.graphqlformatter.format;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ekryd.plugin.graphqlformatter.util.ReadFile;
import org.junit.jupiter.api.Test;

class GraphQLFormatterTest {

  @Test
  void expandFormatted() {
    // language=graphql
    var input = "{ me {name\n} }";
    var formatter = new GraphQLFormatter(input);
    assertThat(formatter.expandFormatted(), is("{\n  me {\n    name\n  }\n}\n"));
  }

  @Test
  void collapseFormatted() {
    // language=graphql
    var input = "{ me\n}\n";
    
    var formatter = new GraphQLFormatter(input);
    assertEquals("{me}", formatter.collapseFormatted());
  }

  @Test
  void expandCommentShouldKeepComment() {
    // language=graphqls
    var input = "type Query {\n#A comment\nme: String\n}";
    
    var formatter = new GraphQLFormatter(input);
    assertThat(formatter.expandFormatted(), is("type Query {\n  #A comment\n  me: String\n}\n"));
  }

  @Test
  void compressCommentShouldKeepComment() {
    // language=graphqls
    var input = "type Query {\n#A comment\nme: String\n}";
    
    var formatter = new GraphQLFormatter(input);
    assertThat(formatter.compressFormatted(), is("type Query {\n  #A comment\n  me: String\n}\n"));
  }

  @Test
  void collapseCommentShouldRemoveIt() {
    // language=graphqls
    var input = "type Query {\n#A comment\nme: String\n}";
    
    var formatter = new GraphQLFormatter(input);
    assertEquals("type Query {me: String}", formatter.collapseFormatted());
  }

  @Test
  void schemaShouldBeExpanded() {
    var input = new ReadFile("wolfMain.graphqls").readFileToString();
    var formatter = new GraphQLFormatter(input);
    var expected = new ReadFile("wolfMain_expanded.graphqls").readFileToString();
    assertEquals(expected, formatter.expandFormatted());
  }
  
    @Test
  void queryWithMultipleBracketsShouldBeCompressed() {
    var input = new ReadFile("mediumQuery.graphql").readFileToString();
    var formatter = new GraphQLFormatter(input);
    var expected = new ReadFile("mediumQuery_compressed.graphql").readFileToString();
    assertEquals(expected, formatter.compressFormatted());
  }
}
