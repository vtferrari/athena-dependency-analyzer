package com.netshoes.athena.domains;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class PendingProjectAnalyze {

  private final Project project;
  private String reason;
  private LocalDateTime scheduledDate;
  private LocalDateTime lastModifiedDate;

  public PendingProjectAnalyze(Project project) {
    this.project = project;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }

  public void setReason(Exception e) {
    this.reason = e.getMessage();
  }
}
