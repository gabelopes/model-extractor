package br.unisinos.parthenos.generator.exception;

import lombok.AllArgsConstructor;

import java.io.File;

@AllArgsConstructor
public class FileIsNotFolderException extends RuntimeException {
  private File file;

  @Override
  public String toString() {
    return "File " + file.getAbsolutePath() + " is not a folder or does not exist!";
  }
}
