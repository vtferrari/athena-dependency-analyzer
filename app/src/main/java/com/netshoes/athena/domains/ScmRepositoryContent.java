package com.netshoes.athena.domains;

import lombok.Data;

@Data
public class ScmRepositoryContent {

  private final ScmRepository repository;
  private final String path;
  private final String name;
  private final ContentType type;
  private String content;
  private long size;

  public ScmRepositoryContent(
      ScmRepository repository,
      String path,
      String name,
      long size,
      ContentType type,
      String content) {
    this.repository = repository;
    this.path = path;
    this.name = name;
    this.type = type;
    this.size = size;
    this.content = content;
  }

  public boolean isDirectory() {
    return ContentType.DIRECTORY.equals(type);
  }

  public boolean isFile() {
    return ContentType.FILE.equals(type);
  }
}
