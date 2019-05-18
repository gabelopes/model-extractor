package br.unisinos.parthenos.generator.io.repository;

import br.unisinos.parthenos.generator.io.SourceFile;
import br.unisinos.parthenos.generator.pool.SourceLanguage;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@AllArgsConstructor
public abstract class Repository {
  private Set<SourceLanguage> sourceLanguages;

  protected abstract Set<File> getFiles();

  public List<SourceFile> findSourceFiles() {
    final Set<File> files = this.getFiles();
    final List<SourceFile> sourceFiles = new ArrayList<>();

    if (files == null) {
      return null;
    }

    for (File file : files) {
      SourceLanguage sourceLanguage = this.matchSourceLanguage(file);

      if (isSourceFile(file, sourceLanguage)) {
        sourceFiles.add(new SourceFile(sourceLanguage, file));
      }
    }

    return sourceFiles;
  }

  private boolean isSourceFile(File file, SourceLanguage sourceLanguage) {
    return
      !file.isDirectory()
        && sourceLanguage != null;
  }

  private boolean belongsToSourceLanguage(File file, SourceLanguage sourceLanguage) {
    final String fileName = file.getName();

    for (String extension : sourceLanguage.getExtensions()) {
      if (fileName.endsWith(extension)) {
        return true;
      }
    }

    return false;
  }

  private SourceLanguage matchSourceLanguage(File sourceFile) {
    for (SourceLanguage sourceLanguage : this.getSourceLanguages()) {
      boolean belongsToSourceLanguage = this.belongsToSourceLanguage(sourceFile, sourceLanguage);

      if (belongsToSourceLanguage) {
        return sourceLanguage;
      }
    }

    return null;
  }
}
