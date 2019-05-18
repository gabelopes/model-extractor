package br.unisinos.parthenos.generator;

import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.util.Set;

import static picocli.CommandLine.Option;
import static picocli.CommandLine.Parameters;

@Getter
@Setter
public class CLI {
  @Option(names = {"-r", "--repository"})
  private File repositoryFolder;

  @Option(names = {"-f", "--outputFolder"})
  private File outputFolder;

  @Option(names = {"-L", "--link"})
  private File linkingFile;

  @Option(names = {"-l", "--languages"}, required = true)
  private Set<String> sourceLanguages;

  @Option(names = {"-e", "--extensions"}, required = true)
  private Set<File> extensions;

  @Parameters
  private Set<File> sourceFiles;

  public Processor interpret() {
    return new Processor(this.getRepositoryFolder(), this.getOutputFolder(), this.getLinkingFile(), this.getSourceLanguages(), this.getExtensions(), this.getSourceFiles());
  }
}
