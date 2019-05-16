package br.unisinos.parthenos.generator.prolog.fact;

import br.unisinos.parthenos.generator.annotation.Arity;
import br.unisinos.parthenos.generator.annotation.Predicate;
import br.unisinos.parthenos.generator.builder.FactBuilder;
import br.unisinos.parthenos.generator.enumerator.EdgeLabel;
import br.unisinos.parthenos.generator.prolog.term.Term;
import br.unisinos.parthenos.generator.prolog.term.Text;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Predicate("edge")
@Arity(3)
public class Edge extends Fact {
  private Term<?> head;
  private EdgeLabel label;
  private Term<?> tail;

  public Edge(Term<?> head, EdgeLabel label, String tail) {
    this.head = head;
    this.label = label;
    this.tail = new Text(tail);
  }

  @Override
  public Term<?>[] getArguments() {
    return new Term<?>[]{this.getHead(), this.getLabel(), this.getTail()};
  }

  @Override
  public String toString() {
    return new FactBuilder(this).buildSignature();
  }
}
