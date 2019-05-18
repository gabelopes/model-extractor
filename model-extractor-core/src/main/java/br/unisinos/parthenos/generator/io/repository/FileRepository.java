package br.unisinos.parthenos.generator.io.repository;

import br.unisinos.parthenos.generator.pool.SourceLanguage;
import lombok.Getter;

import java.io.File;
import java.util.Set;

@Getter
public class FileRepository extends Repository {
  private Set<File> files;

  public FileRepository(Set<File> files, Set<SourceLanguage> sourceLanguages) {
    super(sourceLanguages);
    this.files = files;
  }
}
