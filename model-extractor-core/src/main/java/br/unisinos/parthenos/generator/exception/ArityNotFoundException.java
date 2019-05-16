package br.unisinos.parthenos.generator.exception;

import br.unisinos.parthenos.generator.prolog.fact.Fact;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ArityNotFoundException extends RuntimeException {
  private Class<? extends Fact> factClass;

  @Override
  public String toString() {
    return "Could not find arity for fact " + factClass;
  }
}
