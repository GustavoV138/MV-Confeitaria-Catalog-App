package vs.gustavo.mvconfeitariacatalogapp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

@Entity
@Table(name = "cakes")
public class Cake {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Um título deve ser informado.")
    @Size(min = 4, message = "O título deve ter no mínimo 4 caracteres.")
    private String title;

    private String description;

    @Column(nullable = false)
    @NotBlank(message = "A quantidade de fatias deve ser informado.")
    @Min(1)
    private Integer slices;

    @Column(nullable = false)
    @NotBlank(message = "O preço deve ser informado.")
    private BigDecimal price;

    public Cake() {}

    private Cake(String title, String description, Integer slices, BigDecimal price) {
        this.title = title;
        this.description = description;
        this.slices = slices;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSlices() {
        return slices;
    }

    public void setSlices(Integer slices) {
        this.slices = slices;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public static final class Builder {
        private String title;
        private String description;
        private Integer slices;
        private BigDecimal price;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder title(String val) {
            title = val;
            return this;
        }

        public Builder description(String val) {
            description = val;
            return this;
        }

        public Builder slices(Integer val) {
            slices = val;
            return this;
        }

        public Builder price(BigDecimal val) {
            price = val;
            return this;
        }

        public Cake build() {
            return new Cake(title, description, slices, price);
        }
    }

    @Override
    public String toString() {
        return "Cake{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", slices=" + slices +
                ", price=" + price +
                '}';
    }
}
