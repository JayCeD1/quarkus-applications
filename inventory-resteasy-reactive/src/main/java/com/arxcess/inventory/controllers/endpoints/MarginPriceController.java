package com.arxcess.inventory.controllers.endpoints;

import com.arxcess.inventory.controllers.services.MarginPriceService;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;

@Path("selling-calculator")
@Produces("application/json")
@Consumes("application/json")
@Tag(name = "Selling Price Margin Calculator")
public class MarginPriceController {

    @Inject
    MarginPriceService service;

    @GET
    @Path("average/items/{id: \\d+}")
    @APIResponse(responseCode = "200", description = "Get average cost for an inventory item", content = @Content(mediaType =
            MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.OBJECT, implementation = BigDecimal.class)))
    public Uni<Response> getAverage(@PathParam("id") Long id) {
        return service.getFromAverageCost(id);
    }

    @GET
    @Path("highest-in-first-out/items/{id: \\d+}")
    @APIResponse(responseCode = "200", description = "Get Highest In, First Out (HIFO) cost for an inventory item", content = @Content(mediaType =
            MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.OBJECT, implementation = BigDecimal.class)))
    public Uni<Response> getHIFOCost(@PathParam("id") Long id) {
        return service.getFromHIFOCost(id);
    }

    @GET
    @Path("first-in-first-out/items/{id: \\d+}")
    @APIResponse(responseCode = "200", description = "Get First In, First Out (FIFO) cost for an inventory item", content = @Content(mediaType =
            MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.OBJECT, implementation = BigDecimal.class)))
    public Uni<Response> getFIFOCost(@PathParam("id") Long id) {
        return service.getFromFIFOCost(id);
    }

    @GET
    @Path("last-in-first-out/items/{id: \\d+}")
    @APIResponse(responseCode = "200", description = "Get Last In, First Out (LIFO) cost for an inventory item", content = @Content(mediaType =
            MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.OBJECT, implementation = BigDecimal.class)))
    public Uni<Response> getLIFOCost(@PathParam("id") Long id) {
        return service.getFromLIFOCost(id);
    }

    @GET
    @Path("first-expiry-first-out/items/{id: \\d+}")
    @APIResponse(responseCode = "200", description = "Get First Expiry, First Out (FEFO) cost for an inventory item", content = @Content(mediaType =
            MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.OBJECT, implementation = BigDecimal.class)))
    public Uni<Response> getFEFOCost(@PathParam("id") Long id) {
        return service.getFromFEFOCost(id);
    }

}
