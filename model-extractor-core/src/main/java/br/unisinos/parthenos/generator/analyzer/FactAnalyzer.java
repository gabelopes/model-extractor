package br.unisinos.parthenos.generator.analyzer;

import br.unisinos.parthenos.generator.prolog.fact.Fact;

import java.util.Set;

public interface FactAnalyzer {
  Set<Fact> retrieveFacts();
}
