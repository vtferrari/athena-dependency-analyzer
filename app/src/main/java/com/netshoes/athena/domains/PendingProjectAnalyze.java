package com.netshoes.athena.domains;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

@Data
@EqualsAndHashCode(of = "project.id")
public class PendingProjectAnalyze {
  private static final int MAX_STACK_TRACE_WIDTH = 2000;
  private final Project project;
  private String reason;
  private String stackTraceReason;
  private LocalDateTime scheduledDate;
  private LocalDateTime lastModifiedDate;

  public PendingProjectAnalyze(Project project) {
    this.project = project;
  }

  public void setException(Exception e) {
    final Throwable cause = e.getCause();
    if (cause != null) {
      this.reason = cause.getMessage();
    } else {
      this.reason = e.getMessage();
    }

    final String stackTrace = ExceptionUtils.getStackTrace(e);
    this.setStackTraceReason(StringUtils.abbreviate(stackTrace, MAX_STACK_TRACE_WIDTH));
  }
}
