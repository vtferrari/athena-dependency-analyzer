package com.netshoes.athena.gateways.mongo;

import com.netshoes.athena.domains.PendingProjectAnalyze;
import com.netshoes.athena.gateways.PendingProjectAnalyzeGateway;
import com.netshoes.athena.gateways.mongo.docs.PendingProjectAnalyzeDoc;
import com.netshoes.athena.gateways.mongo.repositories.PendingProjectAnalyzeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class PendingProjectAnalyzeMongoGateway implements PendingProjectAnalyzeGateway {

  private final PendingProjectAnalyzeRepository pendingProjectAnalyzeRepository;

  @Override
  public PendingProjectAnalyze findById(String id) {
    final PendingProjectAnalyzeDoc doc = pendingProjectAnalyzeRepository.findOne(id);
    return doc != null ? doc.toDomain() : null;
  }

  @Override
  public void save(PendingProjectAnalyze pendingProjectAnalyze) {
    final PendingProjectAnalyzeDoc doc = new PendingProjectAnalyzeDoc(pendingProjectAnalyze);
    pendingProjectAnalyzeRepository.save(doc);
  }
}
