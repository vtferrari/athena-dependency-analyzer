package com.netshoes.athena.domains;

public enum ContentType {
  DIRECTORY("dir"),
  FILE("file"),
  UNDEFINED("undefined");
  String stringValue;

  ContentType(String stringValue) {
    this.stringValue = stringValue;
  }

  public static ContentType fromString(String stringValue) {
    for (ContentType c : ContentType.values()) {
      if (stringValue.equals(c.stringValue)) {
        return c;
      }
    }
    return UNDEFINED;
  }
}
