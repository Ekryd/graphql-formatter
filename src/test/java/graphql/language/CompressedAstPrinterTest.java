package graphql.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ekryd.plugin.graphqlformatter.util.ReadFile;
import org.junit.jupiter.api.Test;

class CompressedAstPrinterTest {

  @Test
  void queryWithMultipleBracketsShouldBeCompressed() {
    var input = new ReadFile("mediumQuery.graphql").readFileToString();
    var actual = CompressedAstPrinter.print(input);
    var expected = new ReadFile("mediumQuery_compressed.graphql").readFileToString();
    assertEquals(expected, actual);
  }

  @Test
  void fragmentedQueryShouldBeCompressed() {
    var input = new ReadFile("longQuery.graphql").readFileToString();
    var actual = CompressedAstPrinter.print(input);
    var expected = new ReadFile("longQuery_compressed.graphql").readFileToString();
    assertEquals(expected, actual);
  }

  @Test
  void schemaShouldBeCompressed() {
    var input = new ReadFile("wolfMain.graphqls").readFileToString();
    var actual = CompressedAstPrinter.print(input);
    var expected = new ReadFile("wolfMain_compressed.graphqls").readFileToString();
    assertEquals(expected, actual);
  }
}
