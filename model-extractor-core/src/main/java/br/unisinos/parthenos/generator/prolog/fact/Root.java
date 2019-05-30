package br.unisinos.parthenos.generator.prolog.fact;

import br.unisinos.parthenos.generator.annotation.Arity;
import br.unisinos.parthenos.generator.annotation.Functor;
import br.unisinos.parthenos.generator.prolog.term.Term;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Arity(1)
@Functor("root")
public class Root extends Fact {
  final Vertex rootVertex;

  @Override
  public Term<?>[] getArguments() {
    return new Term[] { rootVertex };
  }
}
