package br.unisinos.parthenos.generator.exception;

import lombok.Getter;

import java.io.File;

@Getter
public class CouldNotCreateOutputFolderException extends RuntimeException {
  private File file;

  public CouldNotCreateOutputFolderException(File file) {
    this.file = file;
  }

  @Override
  public String toString() {
    return "Could not create output folder represented by path '" + this.file + "'";
  }
}
