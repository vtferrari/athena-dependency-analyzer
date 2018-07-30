package com.netshoes.athena.usecases.exceptions;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import lombok.Getter;

public class DomainAlreadyExistsException extends RuntimeException {
  @Getter private Identifier id;

  public DomainAlreadyExistsException(String message, Object... idValues) {
    super(message);
    this.id = new Identifier(idValues);
  }

  public static class Identifier {
    private final List<String> values;

    public Identifier(Object... idFields) {
      values = new ArrayList<>();
      for (Object idField : idFields) {
        values.add(idField.toString());
      }
    }

    public String toString() {
      final StringJoiner sj = new StringJoiner(",");
      values.forEach(sj::add);
      return sj.toString();
    }
  }
}
