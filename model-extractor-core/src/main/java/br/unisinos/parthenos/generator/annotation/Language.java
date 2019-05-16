package br.unisinos.parthenos.generator.annotation;

import br.unisinos.parthenos.generator.enumerator.SourceLanguage;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Language {
  SourceLanguage value();
}
