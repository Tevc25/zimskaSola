package si.um.feri.vao;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import si.um.feri.dto.UporabnikDTO;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Uporabnik {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;
    private String email;
    private String geslo;

    public Uporabnik(UporabnikDTO dto) {
        setUsername(dto.username());
        setEmail(dto.email());
        setGeslo(dto.geslo());
    }

    public void updateUporabnik(UporabnikDTO dto) {
        setUsername(dto.username());
        setEmail(dto.email());
        setGeslo(dto.geslo());
    }

    public UporabnikDTO toDto() {
        return new UporabnikDTO(id, username, email, geslo);
    }
}
