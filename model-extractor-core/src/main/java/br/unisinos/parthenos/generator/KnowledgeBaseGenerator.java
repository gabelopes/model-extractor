package br.unisinos.parthenos.generator;

import br.unisinos.parthenos.generator.analyzer.FactAnalyzer;
import br.unisinos.parthenos.generator.analyzer.FactAnalyzerFactory;
import br.unisinos.parthenos.generator.enumerator.SourceLanguage;
import br.unisinos.parthenos.generator.io.SourceFile;
import br.unisinos.parthenos.generator.io.repository.Repository;
import br.unisinos.parthenos.generator.processor.KnowledgeBaseProcessor;
import br.unisinos.parthenos.generator.processor.KnowledgeBaseProcessorFactory;
import br.unisinos.parthenos.generator.prolog.fact.Fact;
import br.unisinos.parthenos.generator.prolog.knowledgeBase.KnowledgeBase;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class KnowledgeBaseGenerator {
  private Repository repository;

  private Set<SourceLanguage> getSourceLanguages(Set<SourceFile> sourceFiles) {
    return sourceFiles
      .stream()
      .map(SourceFile::getLanguage)
      .collect(Collectors.toSet());
  }

  private void processKnowledgeBases(Map<SourceFile, KnowledgeBase> knowledgeBases) {
    final Set<SourceLanguage> sourceLanguages = this.getSourceLanguages(knowledgeBases.keySet());
    final Set<KnowledgeBaseProcessor> processors = KnowledgeBaseProcessorFactory.getProcessorsFor(sourceLanguages);

    for (KnowledgeBaseProcessor processor : processors) {
      processor.process(this.getRepository(), knowledgeBases);
    }
  }

  public Map<SourceFile, KnowledgeBase> generate() {
    final Map<SourceFile, KnowledgeBase> knowledgeBases = new HashMap<>();

    for (SourceFile sourceFile : this.getRepository().findSourceFiles()) {
      final FactAnalyzer factAnalyzer = FactAnalyzerFactory.getFactAnalyzerFor(sourceFile);

      if (factAnalyzer != null) {
        final Set<Fact> facts = factAnalyzer.retrieveFacts();

        if (facts != null && !facts.isEmpty()) {
          knowledgeBases.put(sourceFile, KnowledgeBase.from(facts));
        }
      }
    }

    this.processKnowledgeBases(knowledgeBases);

    return knowledgeBases;
  }
}
