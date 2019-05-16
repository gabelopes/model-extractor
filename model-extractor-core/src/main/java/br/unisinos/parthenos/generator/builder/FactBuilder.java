package br.unisinos.parthenos.generator.builder;

import br.unisinos.parthenos.generator.prolog.fact.Fact;
import br.unisinos.parthenos.generator.prolog.term.Term;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class FactBuilder {
  private static final String ARITY_SEPARATOR = "/";
  private static final String ARGUMENTS_OPENER = "(";
  private static final String ARGUMENTS_CLOSER = ")";
  private static final String ARGUMENT_SEPARATOR = ", ";
  private static final String IGNORED_ARGUMENT = "_";
  private static final String FACT_TERMINATOR = ".";

  private Fact fact;

  public String buildSignature() {
    final StringBuilder stringBuilder = new StringBuilder();

    this.writePredicate(stringBuilder);
    this.writeArity(stringBuilder);
    this.terminateFact(stringBuilder);

    return stringBuilder.toString();
  }

  public String buildFact() {
    final StringBuilder stringBuilder = new StringBuilder();

    this.writePredicate(stringBuilder);
    this.writeArguments(stringBuilder);
    this.terminateFact(stringBuilder);

    return stringBuilder.toString();
  }

  private void writeArity(final StringBuilder stringBuilder) {
    final int arity = this.getFact().getArity();

    stringBuilder
      .append(ARITY_SEPARATOR)
      .append(arity);
  }

  private void writePredicate(final StringBuilder stringBuilder) {
    final String predicate = this.getFact().getPredicate();
    stringBuilder.append(predicate);
  }

  private void writeArguments(final StringBuilder stringBuilder) {
    final int arity = this.getFact().getArity();
    final Term<?>[] arguments = this.getFact().getArguments();

    if (arity == 0) {
      return;
    }

    this.openArguments(stringBuilder);

    for (int i = 0; i < arity; i++) {
      final String argument = this.getArgumentText(arguments, i);
      stringBuilder.append(argument);

      if (i < arity - 1) {
        stringBuilder.append(ARGUMENT_SEPARATOR);
      }
    }

    this.closeArguments(stringBuilder);
  }

  private void openArguments(final StringBuilder stringBuilder) {
    stringBuilder.append(ARGUMENTS_OPENER);
  }

  private String getArgumentText(final Term[] arguments, final int index) {
    return index < arguments.length ? arguments[index].getTerm() : IGNORED_ARGUMENT;
  }

  private void closeArguments(final StringBuilder stringBuilder) {
    stringBuilder.append(ARGUMENTS_CLOSER);
  }

  private void terminateFact(final StringBuilder stringBuilder) {
    stringBuilder.append(FACT_TERMINATOR);
  }
}
