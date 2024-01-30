package si.um.feri.rest;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import si.um.feri.dao.UporabnikRepository;
import si.um.feri.dto.UporabnikDTO;
import si.um.feri.vao.Uporabnik;
import javax.ws.rs.core.HttpHeaders;
import io.vertx.ext.web.RoutingContext;

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
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response postuporabnik(UporabnikDTO dto) {
        try {
            log.info("Received payload: " + dto.toString());

            Uporabnik uporabnik = new Uporabnik(dto);
            uporabnikRepository.persist(uporabnik);

            log.info("User created successfully");

            return Response.ok(uporabnik.toDto()).status(Response.Status.CREATED).build();
        } catch (Exception e) {
            log.severe("Error while processing the request: " + e.getMessage());
            e.printStackTrace();  // log the stack trace for detailed debugging
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error while processing the request").build();
        }
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
