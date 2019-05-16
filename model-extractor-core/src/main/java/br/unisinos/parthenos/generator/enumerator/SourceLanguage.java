package br.unisinos.parthenos.generator.enumerator;

public enum SourceLanguage {
  JAVA(".java");

  String[] extensions;

  SourceLanguage(String... extensions) {
    this.extensions = extensions;
  }

  public String[] getExtensions() {
    return this.extensions;
  }
}
