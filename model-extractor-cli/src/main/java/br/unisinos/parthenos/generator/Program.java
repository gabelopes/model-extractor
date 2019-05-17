package br.unisinos.parthenos.generator;

import picocli.CommandLine;

public class Program {
  public static void main(String[] args) {
    CommandLine
      .populateCommand(new CLI(), args)
      .interpret()
      .process();
  }
}
