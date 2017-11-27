package com.netshoes.athena.gateways.mongo.docs;

import com.netshoes.athena.domains.PendingProjectAnalyze;
import com.netshoes.athena.domains.Project;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "pendingProjectAnalyzes")
@TypeAlias("pendingProjectAnalyze")
@Data
@NoArgsConstructor
public class PendingProjectAnalyzeDoc {

  @Id private String id;
  private ProjectDoc project;
  private String reason;
  private String stackTraceReason;
  @Indexed private LocalDateTime scheduledDate;
  @LastModifiedDate private LocalDateTime lastModifiedDate;

  public PendingProjectAnalyzeDoc(PendingProjectAnalyze domain) {
    final Project projectDomain = domain.getProject();
    this.id = projectDomain.getId();
    this.project = new ProjectDoc(projectDomain);
    this.reason = domain.getReason();
    this.stackTraceReason = domain.getStackTraceReason();
    this.scheduledDate = domain.getScheduledDate();
    this.lastModifiedDate = domain.getLastModifiedDate();
  }

  public PendingProjectAnalyze toDomain() {
    final Project projectDomain = this.project.toDomain(false);
    final PendingProjectAnalyze domain = new PendingProjectAnalyze(projectDomain);
    domain.setLastModifiedDate(lastModifiedDate);
    domain.setReason(reason);
    domain.setStackTraceReason(stackTraceReason);
    domain.setScheduledDate(scheduledDate);
    return domain;
  }
}
