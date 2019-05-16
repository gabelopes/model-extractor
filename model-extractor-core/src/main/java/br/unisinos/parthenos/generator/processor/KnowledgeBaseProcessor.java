package br.unisinos.parthenos.generator.processor;

import br.unisinos.parthenos.generator.io.SourceFile;
import br.unisinos.parthenos.generator.io.repository.Repository;
import br.unisinos.parthenos.generator.prolog.knowledgeBase.KnowledgeBase;

import java.util.Map;

public interface KnowledgeBaseProcessor {
  void process(Repository repository, Map<SourceFile, KnowledgeBase> knowledgeBases);
}
