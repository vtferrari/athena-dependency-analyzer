package com.netshoes.athena.usecases;

import static br.com.six2six.fixturefactory.Fixture.from;
import static com.netshoes.athena.domains.ArtifactTemplateLoader.SPRING_BOOT_STARTER_PARENT_1_4_5_RELEASE;
import static com.netshoes.athena.domains.ArtifactTemplateLoader.SPRING_BOOT_STARTER_PARENT_1_4_7_RELEASE;
import static com.netshoes.athena.domains.ArtifactTemplateLoader.SPRING_BOOT_STARTER_PARENT_1_5_7_RELEASE;
import static com.netshoes.athena.domains.ArtifactTemplateLoader.SPRING_BOOT_STARTER_PARENT_1_5_8_RELEASE;
import static com.netshoes.athena.domains.VersionMappingTemplateLoader.SPRING_BOOT_STARTER_PARENT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.netshoes.athena.domains.Artifact;
import com.netshoes.athena.domains.ArtifactVersionReport;
import com.netshoes.athena.domains.VersionMapping;
import com.netshoes.athena.gateways.VersionMappingGateway;
import java.util.Optional;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AnalyzeArtifactTest {
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
        from(VersionMapping.class).gimme(SPRING_BOOT_STARTER_PARENT);
    when(versionPatternGateway.findByArtifact(any())).thenReturn(Optional.of(versionMapping));
  }

  @Test
  public void whenArtifactIsInStableVersion() {
    final Artifact springBootArtifact =
        from(Artifact.class).gimme(SPRING_BOOT_STARTER_PARENT_1_5_8_RELEASE);

    final Optional<ArtifactVersionReport> optionalReport =
        analyzeArtifact.execute(springBootArtifact);

    assertThat(optionalReport.isPresent()).isTrue();

    final ArtifactVersionReport report = optionalReport.get();

    assertThat(report.getAlternatives()).isEmpty();
    assertThat(report.isStable()).isTrue();
    assertThat(report.getStableVersion()).isEqualTo("1.5.8.RELEASE");
    assertThat(report.getSummary()).isEqualTo("Stable version 1.5.8.RELEASE");
  }

  @Test
  public void whenArtifactIsNotInStableForPatchVersion() {
    final Artifact springBootArtifact =
        from(Artifact.class).gimme(SPRING_BOOT_STARTER_PARENT_1_5_7_RELEASE);

    final Optional<ArtifactVersionReport> optionalReport =
        analyzeArtifact.execute(springBootArtifact);

    assertThat(optionalReport.isPresent()).isTrue();

    final ArtifactVersionReport report = optionalReport.get();
    assertThat(report.isStable()).isFalse();
    assertThat(report.getStableVersion()).isEqualTo("1.5.8.RELEASE");
    assertThat(report.getSummary()).isEqualTo("Upgrade to 1.5.8.RELEASE");
  }

  @Test
  public void whenArtifactIsNotInStableForMinorVersion() {
    final Artifact springBootArtifact =
        from(Artifact.class).gimme(SPRING_BOOT_STARTER_PARENT_1_4_7_RELEASE);

    final Optional<ArtifactVersionReport> optionalReport =
        analyzeArtifact.execute(springBootArtifact);

    assertThat(optionalReport.isPresent()).isTrue();

    final ArtifactVersionReport report = optionalReport.get();
    assertThat(report.isStable()).isFalse();
    assertThat(report.getStableVersion()).isEqualTo("1.5.8.RELEASE");

    final ArtifactVersionReport alternative = report.getAlternatives().get(0);
    assertThat(alternative.isStable()).isTrue();
    assertThat(alternative.getStableVersion()).isEqualTo("1.4.7.RELEASE");
    assertThat(report.getSummary()).isEqualTo("Upgrade to 1.5.8.RELEASE");
  }

  @Test
  public void whenArtifactIsNotInStableForMajorVersion() {
    final Artifact springBootArtifact =
        from(Artifact.class).gimme(SPRING_BOOT_STARTER_PARENT_1_4_5_RELEASE);

    final Optional<ArtifactVersionReport> optionalReport =
        analyzeArtifact.execute(springBootArtifact);

    assertThat(optionalReport.isPresent()).isTrue();

    final ArtifactVersionReport report = optionalReport.get();
    assertThat(report.isStable()).isFalse();
    assertThat(report.getStableVersion()).isEqualTo("1.5.8.RELEASE");

    final ArtifactVersionReport alternative = report.getAlternatives().get(0);
    assertThat(alternative.isStable()).isFalse();
    assertThat(alternative.getStableVersion()).isEqualTo("1.4.7.RELEASE");
    assertThat(report.getSummary())
        .isEqualTo("Upgrade to 1.5.8.RELEASE or Upgrade to 1.4.7.RELEASE");
  }
}
