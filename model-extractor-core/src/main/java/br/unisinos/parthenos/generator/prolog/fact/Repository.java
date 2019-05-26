package br.unisinos.parthenos.generator.prolog.fact;

import br.unisinos.parthenos.generator.annotation.Arity;
import br.unisinos.parthenos.generator.annotation.Functor;
import br.unisinos.parthenos.generator.prolog.term.Atom;
import br.unisinos.parthenos.generator.prolog.term.Term;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.File;
import java.nio.file.Path;

@Functor("repository")
@Arity(1)
@Getter
@AllArgsConstructor
public class Repository extends Fact {
  private Path path;

  public Repository(File path) {
    this.path = path.toPath();
  }

  private String getEscapedPath() {
    return this.getPath().toAbsolutePath().toString().replace("\\", "/");
  }

  private Atom getPathAtom() {
    return new Atom(this.getEscapedPath());
  }

  @Override
  public Term<?>[] getArguments() {
    return new Term<?>[]{this.getPathAtom()};
  }
}
