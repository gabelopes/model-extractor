package br.unisinos.parthenos.generator.processor;

import br.unisinos.parthenos.generator.annotation.Language;
import br.unisinos.parthenos.generator.pool.SourceLanguage;
import br.unisinos.parthenos.generator.pool.SourceLanguagePool;
import org.reflections.Reflections;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class KnowledgeBaseProcessorFactory {
  private static KnowledgeBaseProcessor createProcessor(Class<? extends KnowledgeBaseProcessor> processorClass) {
    try {
      return processorClass.newInstance();
    } catch (InstantiationException | IllegalAccessException e) {
      return null;
    }
  }

  private static SourceLanguage getProcessorSourceLanguage(Class<? extends KnowledgeBaseProcessor> processorClass) {
    final Language language = processorClass.getAnnotation(Language.class);

    if (language == null) {
      return null;
    }

    return SourceLanguagePool.get(language.value());
  }

  private static boolean isProperProcessor(Class<? extends KnowledgeBaseProcessor> processorClass, Set<SourceLanguage> sourceLanguages) {
    final SourceLanguage sourceLanguage = getProcessorSourceLanguage(processorClass);

    if (sourceLanguage == null) {
      return false;
    }

    return sourceLanguages.contains(sourceLanguage);
  }

  private static Set<Class<? extends KnowledgeBaseProcessor>> getProcessorClassesForLanguages(Set<SourceLanguage> sourceLanguages) {
    final Set<Class<? extends KnowledgeBaseProcessor>> processorClasses = new Reflections().getSubTypesOf(KnowledgeBaseProcessor.class);

    return processorClasses
      .stream()
      .filter((processorClass) -> isProperProcessor(processorClass, sourceLanguages))
      .collect(Collectors.toSet());
  }

  public static Set<KnowledgeBaseProcessor> getProcessorsFor(Set<SourceLanguage> sourceLanguages) {
    System.out.println("Finding processors for languages: " + sourceLanguages);
    return getProcessorClassesForLanguages(sourceLanguages)
      .stream()
      .map(KnowledgeBaseProcessorFactory::createProcessor)
      .filter(Objects::nonNull)
      .collect(Collectors.toSet());
  }
}
