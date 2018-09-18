package com.netshoes.athena.usecases;

import static br.com.six2six.fixturefactory.Fixture.from;
import static com.netshoes.athena.domains.ArtifactTemplateLoader.SPRING_BOOT_STARTER_PARENT_1_4_5_RELEASE;
import static com.netshoes.athena.domains.VersionMappingTemplateLoader.SPRING_BOOT_STARTER_PARENT_2_X_X;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.netshoes.athena.domains.Artifact;
import com.netshoes.athena.domains.ArtifactVersionReport;
import com.netshoes.athena.domains.VersionMapping;
import com.netshoes.athena.gateways.VersionMappingGateway;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@RunWith(MockitoJUnitRunner.class)
public class AnalyzeArtifactMajorVersionTest {
  @Mock private VersionMappingGateway versionPatternGateway;

  private AnalyzeArtifact analyzeArtifact;

  @BeforeClass
  public static void loadTemplates() {
    FixtureFactoryLoader.loadTemplates("com.netshoes.athena");
  }

  @Before
  public void setup() {
    analyzeArtifact = new AnalyzeArtifact(versionPatternGateway);

    final VersionMapping versionMapping =
        from(VersionMapping.class).gimme(SPRING_BOOT_STARTER_PARENT_2_X_X);
    when(versionPatternGateway.findByArtifact(any())).thenReturn(Mono.just(versionMapping));
  }

  @Test
  public void whenArtifactIsNotInStableForMinorAndNotStableVersion() {
    final Artifact springBootArtifact =
        from(Artifact.class).gimme(SPRING_BOOT_STARTER_PARENT_1_4_5_RELEASE);

    StepVerifier.create(analyzeArtifact.execute(springBootArtifact))
        .expectNextMatches(
            report -> {
              assertThat(report.isStable()).isFalse();
              assertThat(report.getStableVersion()).isEqualTo("2.0.5.RELEASE");

              final ArtifactVersionReport alternative = report.getAlternatives().get(0);
              assertThat(alternative.isStable()).isFalse();
              assertThat(alternative.getStableVersion()).isEqualTo("1.4.7.RELEASE");
              assertThat(report.getSummary())
                  .isEqualTo("Upgrade to 2.0.5.RELEASE or Upgrade to 1.4.7.RELEASE");
              return true;
            })
        .verifyComplete();
  }
}
