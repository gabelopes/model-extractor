package br.unisinos.parthenos.generator.enumerator;

import br.unisinos.parthenos.generator.prolog.term.Atom;
import br.unisinos.parthenos.generator.prolog.term.Term;

public interface EdgeLabel extends Enumerator, Term<String> {
  @Override
  default String getContent() {
    return this.name();
  }

  @Override
  default String getTerm() {
    return new Atom(this.getContent().toLowerCase()).getTerm();
  }
}
