package com.example.laboratorul8.controller;

import com.example.laboratorul8.filter.CacheFilter;
import com.example.laboratorul8.model.Course;
import com.example.laboratorul8.model.Evaluation;
import com.example.laboratorul8.model.Teacher;
import com.example.laboratorul8.repository.EvaluationRepository;
import com.example.laboratorul8.service.EvaluationServiceImpl;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/evaluations")
public class EvaluationController {


    @Inject
    EvaluationServiceImpl evaluationServiceImpl;

    @POST
    @Path("/send/{studentUsername}/{teacherName}/{courseName}/{activityName}/{grade}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response sendEvaluation(
            @PathParam("studentUsername") String studentUsername,
            @PathParam("teacherName") String teacherName,
            @PathParam("courseName") String courseName,
            @PathParam("activityName") String activityName,
            @PathParam("grade") int grade,
            @QueryParam("comment") String comment) {

        boolean success = evaluationServiceImpl.sendEvaluation(
                studentUsername, teacherName, courseName, activityName, grade, comment
        );

        CacheFilter.invalidateCache();

        if (success) {
            return Response.status(Response.Status.CREATED).entity("Evaluation submitted successfully").build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Failed to submit evaluation").build();
        }
    }

    @GET
    @Path("/list")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEvaluations(
            @QueryParam("teacherUsername") String teacherUsername,
            @QueryParam("role") String role) {

        // Call the service method to fetch evaluations
        List<Evaluation> evaluations = evaluationServiceImpl.getEvaluations(teacherUsername, role);

        String cacheKey = "teacherUsername=" + teacherUsername + "&role=" + role;
        System.out.println("CACHE KEY: "+cacheKey);

        if (evaluations == null || evaluations.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("No evaluations found for the provided teacher and role")
                    .build();
        }

        return Response.ok(evaluations).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response replaceEvaluation(@PathParam("id") Long id, Evaluation updatedEvaluation) {


        Evaluation existingEvaluation = evaluationServiceImpl.getEvaluationById(id);

        if (existingEvaluation == null) {
            // Return 404 if the Evaluation is not found
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Evaluation not found with ID " + id)
                    .build();
        }

        existingEvaluation.setStudent(updatedEvaluation.getStudent());
        existingEvaluation.setTeacher(updatedEvaluation.getTeacher());
        existingEvaluation.setCourse(updatedEvaluation.getCourse());

        existingEvaluation.setActivity(updatedEvaluation.getActivity());
        existingEvaluation.setGrade(updatedEvaluation.getGrade());
        existingEvaluation.setComment(updatedEvaluation.getComment());
        existingEvaluation.setRegistrationNumber(updatedEvaluation.getRegistrationNumber());

        evaluationServiceImpl.update(existingEvaluation);

        CacheFilter.invalidateCache();

        return Response.ok(existingEvaluation).build();

    }

    @DELETE
    @Path("/{id}")
    public Response deleteEvaluation(@PathParam("id") Long id) {

        Evaluation existingEvaluation = evaluationServiceImpl.getEvaluationById(id);

        if (existingEvaluation == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Evaluation not found with ID " + id)
                    .build();
        }

        // Proceed to delete the evaluation if it exists
        boolean success = evaluationServiceImpl.delete(id);

        if (success) {

            CacheFilter.invalidateCache();

            return Response.status(Response.Status.NO_CONTENT)  // 204 No Content for successful deletion
                    .entity("Evaluation with ID " + id + " deleted successfully")
                    .build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Failed to delete evaluation with ID " + id)
                    .build();
        }
    }

    @POST
    @Path("/generate-random/{teacherUsername}/{courseName}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response generateRandomEvaluation(
            @QueryParam("sentiment") String sentiment,
            @PathParam("teacherUsername") String teacherName,
            @PathParam("courseName") String courseName) {


        try {
            Evaluation evaluation = evaluationServiceImpl.generateRandomEvaluation(sentiment, teacherName, courseName);

            if (evaluation == null) {

                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Invalid teacher or course name")
                        .build();
            }

            return Response.ok(evaluation).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }

    }

    @GET
    @Path("/analyze-sentiment/{teacherUsername}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response analyzeSentiment(@PathParam("teacherUsername") String teacherUsername) {
        try {
            String sentimentReport = evaluationServiceImpl.analyzeTeacherSentiment(teacherUsername);
            return Response.ok(sentimentReport).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }


}
