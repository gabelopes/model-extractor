package br.unisinos.parthenos.generator.prolog.fact;

import br.unisinos.parthenos.generator.annotation.Arity;
import br.unisinos.parthenos.generator.annotation.Functor;
import br.unisinos.parthenos.generator.exception.ArityNotFoundException;
import br.unisinos.parthenos.generator.prolog.term.Term;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Arrays;
import java.util.Objects;

public abstract class Fact {
  public abstract Term<?>[] getArguments();

  public String getPredicate() {
    final Class<? extends Fact> factClass = this.getClass();
    final Functor functor = factClass.getAnnotation(Functor.class);

    if (functor == null) {
      throw new ArityNotFoundException(factClass);
    }

    return functor.value();
  }

  public int getArity() {
    final Class<? extends Fact> factClass = this.getClass();
    final Arity arity = factClass.getAnnotation(Arity.class);

    if (arity == null) {
      throw new ArityNotFoundException(factClass);
    }

    return arity.value();
  }

  @Override
  public int hashCode() {
    final HashCodeBuilder hashCodeBuilder = new HashCodeBuilder(17, 37)
      .append(this.getPredicate())
      .append(this.getArity());

    for (Term<?> argument : this.getArguments()) {
      hashCodeBuilder.append(argument);
    }

    return hashCodeBuilder.toHashCode();
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof Fact)) {
      return false;
    }

    final Fact otherFact = (Fact) object;

    return
      Objects.equals(this.getPredicate(), otherFact.getPredicate())
        && this.getArity() == otherFact.getArity()
        && Arrays.equals(this.getArguments(), otherFact.getArguments());
  }
}
