package br.unisinos.parthenos.generator.prolog.term;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Objects;

@Getter
@AllArgsConstructor
public class Text implements Term<String> {
  private String content;

  @Override
  public String portray() {
    return "\"" + this.getContent() + "\"";
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
      .append(this.getContent())
      .toHashCode();
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof Text)) {
      return false;
    }

    return Objects.equals(this.getContent(), ((Text) object).getContent());
  }

  @Override
  public String toString() {
    return this.portray();
  }
}
