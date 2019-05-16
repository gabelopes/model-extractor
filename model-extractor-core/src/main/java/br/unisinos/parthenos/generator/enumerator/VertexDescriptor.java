package br.unisinos.parthenos.generator.enumerator;

import br.unisinos.parthenos.generator.prolog.term.Atom;
import br.unisinos.parthenos.generator.prolog.term.Term;

public enum VertexDescriptor implements Term<String> {
  PRIMITIVE,
  NO_TYPE,
  CLASS,
  INTERFACE,
  UNKNOWN_TYPE,
  ATTRIBUTE,
  METHOD,
  PARAMETER,
  MODIFIER,
  ARRAY,
  DEFAULT_TYPE;

  @Override
  public String getContent() {
    return this.name();
  }

  @Override
  public String getTerm() {
    return new Atom(this.getContent().toLowerCase()).getTerm();
  }
}
