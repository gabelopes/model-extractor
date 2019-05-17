package br.unisinos.parthenos.generator;

import br.unisinos.parthenos.generator.enumerator.SourceLanguage;
import br.unisinos.parthenos.generator.io.KnowledgeBaseWriter;
import br.unisinos.parthenos.generator.io.SourceFile;
import br.unisinos.parthenos.generator.io.repository.FileRepository;
import br.unisinos.parthenos.generator.io.repository.FolderRepository;
import br.unisinos.parthenos.generator.io.repository.Repository;
import br.unisinos.parthenos.generator.prolog.knowledgeBase.KnowledgeBase;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.File;
import java.security.InvalidParameterException;
import java.util.Map;
import java.util.Set;

@Getter
@AllArgsConstructor
public class Processor {
  private File repositoryFolder;
  private File outputFolder;
  private File linkingFile;
  private Set<SourceLanguage> sourceLanguages;
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

  private Repository getRepository() {
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
    final KnowledgeBaseWriter knowledgeBaseWriter = new KnowledgeBaseWriter(outputFolder, knowledgeBases, this.getLinkingFile());

    knowledgeBaseWriter.writeKnowledgeBases();
  }
}
