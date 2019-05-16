package br.unisinos.parthenos.generator.prolog.term;

public interface Term<T> {
  T getContent();

  String getTerm();
}
