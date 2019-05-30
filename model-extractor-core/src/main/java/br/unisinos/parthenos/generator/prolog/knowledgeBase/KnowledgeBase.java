package br.unisinos.parthenos.generator.prolog.knowledgeBase;

import br.unisinos.parthenos.generator.builder.FactBuilder;
import br.unisinos.parthenos.generator.prolog.fact.Edge;
import br.unisinos.parthenos.generator.prolog.fact.Fact;
import br.unisinos.parthenos.generator.prolog.fact.Vertex;
import br.unisinos.parthenos.generator.prolog.term.Term;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@NoArgsConstructor
public class KnowledgeBase extends HashSet<Fact> {
  private KnowledgeBase(Set<Fact> facts) {
    super(facts);
  }

  public <T extends Fact> T findFact(Class<T> factType, Term<?>... arguments) {
    for (Fact fact : this) {
      if (factType.isInstance(fact) && this.containsArguments(fact, arguments)) {
        return factType.cast(fact);
      }
    }

    return null;
  }

  public <T extends Fact> T findFact(Class<T> factType, String... arguments) {
    for (Fact fact : this) {
      if (factType.isInstance(fact) && this.containsArguments(fact, arguments)) {
        return factType.cast(fact);
      }
    }

    return null;
  }

  @SafeVarargs
  public final <T extends Fact> T findFact(Class<T> factType, Predicate<Fact>... predicates) {
    for (Fact fact : this) {
      if (factType.isInstance(fact) && this.containsArguments(fact, predicates)) {
        return factType.cast(fact);
      }
    }

    return null;
  }

  public <T extends Fact> Set<T> findFacts(Class<T> factType, Term<?>... arguments) {
    final Set<T> foundFacts = new HashSet<>();

    for (Fact fact : this) {
      if (factType.isInstance(fact) && this.containsArguments(fact, arguments)) {
        foundFacts.add(factType.cast(fact));
      }
    }

    return foundFacts;
  }

  public <T extends Fact> Set<T> findFacts(Class<T> factType, String... arguments) {
    final Set<T> foundFacts = new HashSet<>();

    for (Fact fact : this) {
      if (factType.isInstance(fact) && this.containsArguments(fact, arguments)) {
        foundFacts.add(factType.cast(fact));
      }
    }

    return foundFacts;
  }

  @SafeVarargs
  public final <T extends Fact> Set<T> findFacts(Class<T> factType, Predicate<Fact>... predicates) {
    final Set<T> foundFacts = new HashSet<>();

    for (Fact fact : this) {
      if (factType.isInstance(fact) && this.containsArguments(fact, predicates)) {
        foundFacts.add(factType.cast(fact));
      }
    }

    return foundFacts;
  }

  public Vertex findVertex(Term<?>... arguments) {
    return this.findFact(Vertex.class, arguments);
  }

  public Vertex findVertex(String... arguments) {
    return this.findFact(Vertex.class, arguments);
  }

  @SafeVarargs
  public final Vertex findVertex(Predicate<Fact>... predicates) {
    return this.findFact(Vertex.class, predicates);
  }

  public Set<Vertex> findVertices(Term<?>... arguments) {
    return this.findFacts(Vertex.class, arguments);
  }

  public Set<Vertex> findVertices(String... arguments) {
    return this.findFacts(Vertex.class, arguments);
  }

  @SafeVarargs
  public final Set<Vertex> findVertices(Predicate<Fact>... predicates) {
    return this.findFacts(Vertex.class, predicates);
  }

  public Edge findEdge(Term<?>... arguments) {
    return this.findFact(Edge.class, arguments);
  }

  public Edge findEdge(String... arguments) {
    return this.findFact(Edge.class, arguments);
  }

  @SafeVarargs
  public final Edge findEdge(Predicate<Fact>... predicates) {
    return this.findFact(Edge.class, predicates);
  }

  public Set<Edge> findEdges(Term<?>... arguments) {
    return this.findFacts(Edge.class, arguments);
  }

  public Set<Edge> findEdges(String... arguments) {
    return this.findFacts(Edge.class, arguments);
  }

  @SafeVarargs
  public final Set<Edge> findEdges(Predicate<Fact>... predicates) {
    return this.findFacts(Edge.class, predicates);
  }

  public Vertex findAnyVertex() {
    return (Vertex) this
      .stream()
      .filter((fact) -> fact instanceof Vertex)
      .findAny()
      .orElse(null);
  }

  public Edge findAnyEdge() {
    return (Edge) this
      .stream()
      .filter((fact) -> fact instanceof Edge)
      .findAny()
      .orElse(null);
  }

  public boolean contains(Class<? extends Fact> factType, Term<?>... arguments) {
    return this.findFact(factType, arguments) != null;
  }

  public boolean contains(Class<? extends Fact> factType, String... arguments) {
    return this.findFact(factType, arguments) != null;
  }

  public boolean containsVertex(Term<?>... arguments) {
    return this.findFact(Vertex.class, arguments) != null;
  }

  public boolean containsVertex(String... arguments) {
    return this.findFact(Vertex.class, arguments) != null;
  }

  public boolean containsEdge(Term<?>... arguments) {
    return this.findFact(Edge.class, arguments) != null;
  }

  public boolean containsEdge(String... arguments) {
    return this.findFact(Edge.class, arguments) != null;
  }

  public List<String> toPrologFacts() {
    return this
      .stream()
      .map(FactBuilder::new)
      .map(FactBuilder::buildFact)
      .sorted()
      .collect(Collectors.toList());
  }

  private boolean containsArguments(Fact fact, String... arguments) {
    if (fact.getArguments().length != arguments.length) {
      return false;
    }

    for (int i = 0; i < arguments.length; i++) {
      final String factArgument = fact.getArguments()[i].getContent().toString();
      final String argument = arguments[i];

      if (argument != null && !factArgument.equals(argument)) {
        return false;
      }
    }

    return true;
  }

  private boolean containsArguments(Fact fact, Term<?>... arguments) {
    if (fact.getArguments().length != arguments.length) {
      return false;
    }

    for (int i = 0; i < arguments.length; i++) {
      final Term<?> factArgument = fact.getArguments()[i];
      final Term<?> argument = arguments[i];

      if (argument != null && !factArgument.portray().equals(argument.portray())) {
        return false;
      }
    }

    return true;
  }

  @SafeVarargs
  private final boolean containsArguments(Fact fact, Predicate<Fact>... predicates) {
    if (fact.getArguments().length != predicates.length) {
      return false;
    }

    for (final Predicate<Fact> argument : predicates) {
      if (argument != null && !argument.test(fact)) {
        return false;
      }
    }

    return true;
  }

  public static KnowledgeBase from(Set<Fact> facts) {
    if (facts == null) {
      return new KnowledgeBase();
    }

    return new KnowledgeBase(facts);
  }
}
