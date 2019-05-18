package br.unisinos.parthenos.generator.io.repository;

import br.unisinos.parthenos.generator.exception.FileIsNotFolderException;
import br.unisinos.parthenos.generator.pool.SourceLanguage;
import lombok.Getter;

import java.io.File;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

@Getter
public class FolderRepository extends Repository {
  private File folder;

  public FolderRepository(File folder, Set<SourceLanguage> sourceLanguages) {
    super(sourceLanguages);

    if (!folder.isDirectory()) {
      throw new FileIsNotFolderException(folder);
    }

    this.folder = folder;
  }

  @Override
  protected Set<File> getFiles() {
    return this.getFiles(this.getFolder());
  }

  private Set<File> getFiles(File folder) {
    final Set<File> files = new HashSet<>();
    final File[] listedFiles = folder.listFiles();

    if (listedFiles == null) {
      return Collections.emptySet();
    }

    Stream.of(listedFiles)
      .filter(File::isFile)
      .forEach(files::add);

    Stream.of(listedFiles)
      .filter(File::isDirectory)
      .map(this::getFiles)
      .forEach(files::addAll);

    return files;
  }
}
