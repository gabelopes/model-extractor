package br.unisinos.parthenos.generator.analyzer;

import br.unisinos.parthenos.generator.annotation.Language;
import br.unisinos.parthenos.generator.enumerator.SourceLanguage;
import br.unisinos.parthenos.generator.io.SourceFile;
import org.reflections.Reflections;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class FactAnalyzerFactory {
  private static Map<SourceLanguage, Class<? extends FactAnalyzer>> FACT_ANALYZERS;

  static {
    createFactAnalyzerPool();
  }

  private static void createFactAnalyzerPool() {
    FactAnalyzerFactory.FACT_ANALYZERS = new HashMap<>();

    Reflections reflections = new Reflections();
    Set<Class<? extends FactAnalyzer>> factAnalyzers = reflections.getSubTypesOf(FactAnalyzer.class);

    for (Class<? extends FactAnalyzer> factAnalyzer : factAnalyzers) {
      final SourceLanguage sourceLanguage = getFactAnalyzerLanguage(factAnalyzer);

      if (sourceLanguage != null) {
        FactAnalyzerFactory.getFactAnalyzers().put(sourceLanguage, factAnalyzer);
      }
    }
  }

  private static SourceLanguage getFactAnalyzerLanguage(Class<? extends FactAnalyzer> factAnalyzer) {
    final Language language = factAnalyzer.getAnnotation(Language.class);

    if (language == null) {
      return null;
    }

    return language.value();
  }

  private static Map<SourceLanguage, Class<? extends FactAnalyzer>> getFactAnalyzers() {
    return FactAnalyzerFactory.FACT_ANALYZERS;
  }

  public static FactAnalyzer getFactAnalyzerFor(SourceFile sourceFile) {
    System.out.print("Finding analyzer for " + sourceFile + ".");
    Class<? extends FactAnalyzer> factAnalyzerClass = getFactAnalyzers().get(sourceFile.getLanguage());

    try {
      final Constructor<? extends FactAnalyzer> factAnalyzerConstructor = factAnalyzerClass.getConstructor(File.class);
      return factAnalyzerConstructor.newInstance(sourceFile.getFile());
    } catch (Exception ignored) {
      System.out.println(" Analyzer not found!");
      return null;
    }
  }
}
