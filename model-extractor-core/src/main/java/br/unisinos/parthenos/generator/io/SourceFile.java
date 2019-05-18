package br.unisinos.parthenos.generator.io;

import br.unisinos.parthenos.generator.pool.SourceLanguage;
import br.unisinos.parthenos.generator.prolog.term.Atom;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.File;

@Getter
@Setter
@AllArgsConstructor
public class SourceFile {
  private SourceLanguage language;
  private File file;

  private String getEscapedFilePath() {
    return this.getFile().getAbsolutePath().replace("\\", "/");
  }

  public Atom getFileAtom() {
    return new Atom(this.getEscapedFilePath());
  }

  @Override
  public String toString() {
    return this.getFile().toString();
  }
}
