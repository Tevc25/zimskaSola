package si.um.feri.rest;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import si.um.feri.dao.UporabnikRepository;
import si.um.feri.dto.UporabnikDTO;
import si.um.feri.vao.Uporabnik;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Path("/uporabniks")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UporabnikController {

    private static final Logger log = Logger.getLogger(UporabnikController.class.getName());

    @Inject
    UporabnikRepository uporabnikRepository;

    @GET
    public List<UporabnikDTO> getAllUsers() {
        UporabnikRepository repository = new UporabnikRepository();
        return repository.listAll().stream()
                .map(Uporabnik::toDto)
                .collect(Collectors.toList());
    }

    @GET
    @Path("/{id}")
    public Response getuporabnikById(@PathParam("id") int id) {
        Uporabnik uporabnik = uporabnikRepository.findById((long) id);
        if (uporabnik == null) {
            log.info(() -> "/uporabniks/" + id + " ; uporabnik not found!");
            return Response.status(Response.Status.NOT_FOUND).entity("uporabnik not found").build();
        }
        return Response.ok(uporabnik.toDto()).build();
    }

    @POST
    @Transactional
    public Response postuporabnik(UporabnikDTO dto) {
        Uporabnik uporabnik = new Uporabnik(dto);
        uporabnikRepository.persist(uporabnik);
        return Response.ok(uporabnik.toDto()).status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response putuporabnik(@PathParam("id") int id, UporabnikDTO dto) {
        Uporabnik uporabnik = uporabnikRepository.findById((long) id);
        if (uporabnik == null) {
            log.info(() -> "/uporabniks/" + id + " ; uporabnik not found!");
            return Response.status(Response.Status.NOT_FOUND).entity("uporabnik not found").build();
        }
        uporabnik.updateUporabnik(dto);
        uporabnikRepository.persist(uporabnik);
        return Response.ok(uporabnik.toDto()).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteuporabnik(@PathParam("id") int id) {
        Uporabnik uporabnik = uporabnikRepository.findById((long) id);
        if (uporabnik == null) {
            log.info(() -> "/uporabniks/" + id + " ; uporabnik not found!");
            return Response.status(Response.Status.NOT_FOUND).entity("uporabnik not found").build();
        }
        uporabnikRepository.delete(uporabnik);
        return Response.ok("uporabnik deleted").build();
    }
}
