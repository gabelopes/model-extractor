package br.unisinos.parthenos.generator.enumerator;


import br.unisinos.parthenos.generator.prolog.term.Atom;
import br.unisinos.parthenos.generator.prolog.term.Term;

public enum EdgeLabel implements Term<String> {
  NAME,
  PACKAGE,
  RETURN,
  PARENT,
  TYPE,
  MODIFIER,
  PARAMETER,
  INTERFACE,
  METHOD,
  ATTRIBUTE,
  ORDER,
  SOURCE;

  @Override
  public String getContent() {
    return this.name();
  }

  @Override
  public String getTerm() {
    return new Atom(this.getContent().toLowerCase()).getTerm();
  }
}
