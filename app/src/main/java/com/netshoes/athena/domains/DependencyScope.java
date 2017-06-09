package com.netshoes.athena.domains;

public enum DependencyScope {
  COMPILE("compile"),
  PROVIDED("provided"),
  RUNTIME("runtime"),
  TEST("test"),
  SYSTEM("system"),
  MANAGED("managed"),
  IMPORT("import"),
  UNDEFINED("undefined");

  private String stringValue;

  DependencyScope(String stringValue) {
    this.stringValue = stringValue;
  }

  public static DependencyScope fromString(String stringValue) {
    for (DependencyScope d : DependencyScope.values()) {
      if (d.stringValue.equals(stringValue)) {
        return d;
      }
    }
    return UNDEFINED;
  }

  public String getStringValue() {
    return stringValue;
  }
}
