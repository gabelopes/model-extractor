package br.unisinos.parthenos.generator.io;

import br.unisinos.parthenos.generator.exception.CouldNotCreateOutputFolderException;
import br.unisinos.parthenos.generator.prolog.fact.Fact;
import br.unisinos.parthenos.generator.prolog.fact.Repository;
import br.unisinos.parthenos.generator.prolog.fact.Use;
import br.unisinos.parthenos.generator.prolog.knowledgeBase.KnowledgeBase;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Map.Entry;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class KnowledgeBaseWriter {
  private static final String PROLOG_EXTENSION = ".pl";

  @NonNull
  private File repositoryFolder;

  @NonNull
  private File outputFolder;

  @NonNull
  private Map<SourceFile, KnowledgeBase> knowledgeBases;

  private File linkingFile;

  private Fact createRepositoryFact() {
    return new Repository(this.getRepositoryFolder());
  }

  private Set<Use> createUsesFacts(Set<Path> linkableFilePaths) {
    return linkableFilePaths
      .stream()
      .map(Use::new)
      .collect(Collectors.toSet());
  }

  private void writeLinkingFile(Set<Path> linkableFilePaths) {
    final Path linkingFilePath = this.getOutputFolder().toPath().resolve(this.getLinkingFile().toPath());
    final Set<Fact> linkingFileFacts = new HashSet<>();

    linkingFileFacts.add(this.createRepositoryFact());
    linkingFileFacts.addAll(this.createUsesFacts(linkableFilePaths));
    
    final List<String> prologUsesFacts = KnowledgeBase.from(linkingFileFacts).toPrologFacts();

    try {
      System.out.println("Writing linking file into " + linkingFilePath);
      Files.write(linkingFilePath, prologUsesFacts);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private boolean shouldWriteLinkingFile() {
    return this.getLinkingFile() != null;
  }

  private String createKnowledgeBaseFileName(SourceFile sourceFile, int disambiguation) {
    return sourceFile.getFile().getName().replace(".", "_") + (disambiguation == 0 ? "" : disambiguation) + PROLOG_EXTENSION;
  }

  private Path getKnowledgeBaseFilePath(SourceFile sourceFile, int disambiguation) {
    final Path writeFolderPath = this.getOutputFolder().toPath();
    final String knowledgeBaseFileName = this.createKnowledgeBaseFileName(sourceFile, disambiguation);

    return writeFolderPath.resolve(knowledgeBaseFileName);
  }

  private Path getAvailableKnowledgeBaseFilePath(SourceFile sourceFile) {
    Path knowledgeBaseFilePath = this.getKnowledgeBaseFilePath(sourceFile, 0);

    for (int i = 1; Files.exists(knowledgeBaseFilePath); i++) {
      knowledgeBaseFilePath = this.getKnowledgeBaseFilePath(sourceFile, i);
    }

    return knowledgeBaseFilePath;
  }

  private Path writeKnowledgeBase(SourceFile sourceFile, KnowledgeBase knowledgeBase) {
    final Path knowledgeBaseFilePath = this.getAvailableKnowledgeBaseFilePath(sourceFile);
    final List<String> prologFacts = knowledgeBase.toPrologFacts();

    try {
      System.out.println("Writing knowledge base from " + sourceFile + " into " + knowledgeBaseFilePath);
      Files.write(knowledgeBaseFilePath, prologFacts);
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }

    return knowledgeBaseFilePath;
  }

  private void createOutputFolder() {
    if (this.getOutputFolder().exists()) {
      return;
    }

    final boolean created = this.getOutputFolder().mkdir();

    if (!created) {
      throw new CouldNotCreateOutputFolderException(this.getOutputFolder());
    }
  }

  public void writeKnowledgeBases() {
    final Set<Path> writtenFilePaths = new HashSet<>();

    this.createOutputFolder();

    for (Entry<SourceFile, KnowledgeBase> entry : this.getKnowledgeBases().entrySet()) {
      final Path writtenFilePath = this.writeKnowledgeBase(entry.getKey(), entry.getValue());

      if (writtenFilePath != null) {
        writtenFilePaths.add(writtenFilePath);
      }
    }

    if (this.shouldWriteLinkingFile()) {
      this.writeLinkingFile(writtenFilePaths);
    }
  }
}
