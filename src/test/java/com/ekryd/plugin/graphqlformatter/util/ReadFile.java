package com.ekryd.plugin.graphqlformatter.util;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.FileUtils;

public class ReadFile {
  private final String fileFullPath;

  public ReadFile(String fileName) {
    this.fileFullPath = "src/test/resources/" + fileName;
  }

  public String readFileToString() {
    try {
      return FileUtils.readFileToString(new File(fileFullPath), StandardCharsets.UTF_8);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
