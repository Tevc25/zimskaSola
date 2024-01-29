package si.um.feri.dao;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import si.um.feri.vao.Uporabnik;

@ApplicationScoped
public class UporabnikRepository implements PanacheRepository<Uporabnik> {
}
