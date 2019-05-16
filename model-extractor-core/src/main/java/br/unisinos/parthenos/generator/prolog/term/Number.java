package br.unisinos.parthenos.generator.prolog.term;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Objects;

@Getter
@AllArgsConstructor
public class Number<T extends java.lang.Number> implements Term<T> {
  private T content;

  @Override
  public T getContent() {
    return this.content;
  }

  @Override
  public String getTerm() {
    return this.getContent().toString();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
      .append(this.getContent())
      .toHashCode();
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof Number)) {
      return false;
    }

    return Objects.equals(this.getContent(), ((Number) object).getContent());
  }
}
