package com.demo.rest.laboratorul11.controller;

import com.demo.rest.laboratorul11.management.EvaluationRebuilder;
import com.demo.rest.laboratorul11.management.SagaOrchestrator;
import com.demo.rest.laboratorul11.model.Evaluation;
import com.demo.rest.laboratorul11.service.EvaluationService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@RequestScoped
@Path("/evaluations")
public class EvaluationController {

    @Inject
    private SagaOrchestrator sagaOrchestrator;

    @Inject
    EvaluationService evaluationService;

    @Inject
    EvaluationRebuilder evaluationRebuilder;


    @POST
    @Path("/send/{username}/{teacherName}/{courseName}/{activityName}/{grade}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response sendEvaluation(
            @PathParam("username") String username,
            @PathParam("teacherName") String teacherName,
            @PathParam("courseName") String courseName,
            @PathParam("activityName") String activityName,
            @PathParam("grade") int grade,
            @QueryParam("comment") String comment) {

//        boolean success = evaluationService.sendEvaluation(
//                username, teacherName, courseName, activityName, grade, comment
//        );

        boolean success = sagaOrchestrator.submitEvaluation(
                username,
                teacherName,
                courseName,
                activityName,
                grade,
                comment
        );

        if (success) {
            return Response.status(Response.Status.CREATED).entity("Evaluation submitted successfully").build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Failed to submit evaluation").build();
        }
    }

    @GET
    @Path("/reconstruct/{username}/{course}/{activity}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReconstructEvaluation(@PathParam("username") String username,
                                  @PathParam("course") String course,
                                  @PathParam("activity") String activity) {
        Evaluation evaluation = evaluationRebuilder.reconstructEvaluation(username, course, activity);
        if (evaluation == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(evaluation).build();
    }

}
