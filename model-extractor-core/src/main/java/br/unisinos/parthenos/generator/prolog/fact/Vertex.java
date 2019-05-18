package br.unisinos.parthenos.generator.prolog.fact;

import br.unisinos.parthenos.generator.annotation.Arity;
import br.unisinos.parthenos.generator.annotation.Functor;
import br.unisinos.parthenos.generator.builder.FactBuilder;
import br.unisinos.parthenos.generator.enumerator.VertexDescriptor;
import br.unisinos.parthenos.generator.prolog.term.Term;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Functor("vertex")
@Arity(2)
public class Vertex extends Fact {
  private VertexDescriptor descriptor;
  private Term<?> label;

  @Override
  public Term<?>[] getArguments() {
    return new Term<?>[]{this.getDescriptor(), this.getLabel()};
  }

  @Override
  public String toString() {
    return new FactBuilder(this).buildSignature();
  }
}
