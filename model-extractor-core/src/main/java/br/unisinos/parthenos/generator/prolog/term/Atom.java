package br.unisinos.parthenos.generator.prolog.term;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Objects;

@Getter
@AllArgsConstructor
public class Atom implements Term<String> {
  private String content;

  @Override
  public String getTerm() {
    return "'" + this.getContent() + "'";
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
      .append(this.getContent())
      .toHashCode();
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof Atom)) {
      return false;
    }

    return Objects.equals(this.getContent(), ((Atom) object).getContent());
  }

  @Override
  public String toString() {
    return this.getTerm();
  }
}
