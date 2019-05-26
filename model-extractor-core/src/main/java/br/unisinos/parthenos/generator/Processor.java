package br.unisinos.parthenos.generator;

import br.unisinos.parthenos.generator.io.KnowledgeBaseWriter;
import br.unisinos.parthenos.generator.io.SourceFile;
import br.unisinos.parthenos.generator.io.repository.FileRepository;
import br.unisinos.parthenos.generator.io.repository.FolderRepository;
import br.unisinos.parthenos.generator.io.repository.Repository;
import br.unisinos.parthenos.generator.pool.SourceLanguage;
import br.unisinos.parthenos.generator.pool.SourceLanguagePool;
import br.unisinos.parthenos.generator.prolog.knowledgeBase.KnowledgeBase;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.File;
import java.security.InvalidParameterException;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Getter
@AllArgsConstructor
public class Processor {
  private File repositoryFolder;
  private File outputFolder;
  private File linkingFile;
  private Set<String> sourceLanguagesNames;
  private Set<File> extensions;
  private Set<File> sourceFiles;

  private boolean useFolderRepository() {
    return this.getRepositoryFolder() != null;
  }

  private void checkForErrors() {
    final boolean hasErrors =
      this.getRepositoryFolder() == null
        && (this.getSourceFiles() == null || this.getSourceFiles().isEmpty() || this.getOutputFolder() == null);

    if (hasErrors) {
      throw new InvalidParameterException();
    }
  }

  private Set<SourceLanguage> getSourceLanguages() {
    return this.getSourceLanguagesNames()
      .stream()
      .map(SourceLanguagePool::get)
      .collect(toSet());
  }

  private Repository getRepository() {
    final Set<SourceLanguage> sourceLanguages = this.getSourceLanguages();

    if (this.useFolderRepository()) {
      return new FolderRepository(this.getRepositoryFolder(), this.getSourceLanguages());
    }

    return new FileRepository(this.getSourceFiles(), this.getSourceLanguages());
  }

  private File inferOutputFolder() {
    final File outputFolder = this.getOutputFolder();

    if (outputFolder == null) {
      return this.getRepositoryFolder();
    }

    return outputFolder;
  }

  public void process() {
    this.checkForErrors();

    Extension.includeAll(this.getExtensions());

    final Repository repository = this.getRepository();
    final File outputFolder = this.inferOutputFolder();

    final KnowledgeBaseGenerator knowledgeBaseGenerator = new KnowledgeBaseGenerator(repository);
    final Map<SourceFile, KnowledgeBase> knowledgeBases = knowledgeBaseGenerator.generate();
    final KnowledgeBaseWriter knowledgeBaseWriter = new KnowledgeBaseWriter(this.getRepositoryFolder(), outputFolder, knowledgeBases, this.getLinkingFile());

    knowledgeBaseWriter.writeKnowledgeBases();
  }
}
