package com.api_anime.anime.controller;


import com.api_anime.anime.entity.FilmCmt;
import com.api_anime.anime.model.CommentResponse;
import com.api_anime.anime.model.FilmCmtModel;
import com.api_anime.anime.model.ObjectResponse;
import com.api_anime.anime.service.FilmCmtService;
import com.api_anime.anime.utils.ApplicationUrl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class FilmCommentController {


    @Autowired(required = false)
    private FilmCmtService filmCmtService;

    // get all comment status = true  of film
    @GetMapping("/api/v1/private/film/comment") // id : is id of film
    private ResponseEntity<List<CommentResponse>> getAllCommentByFilm(
            @RequestParam(value = "status", required = false) Boolean status,
            @RequestParam(value = "id", required = false) Long id,
            HttpServletRequest request) {


        List<FilmCmt> filmCmtList = new ArrayList<>();

        if (status!=null && id!=null) {
            filmCmtList = filmCmtService.getAllCmtByFilmAndStatus(id, status);
        } else if (status==null && id!=null) { // find by film id
            filmCmtList = filmCmtService.getAllCmtByFilm(id);

        } else if (status!=null && id==null) filmCmtList = filmCmtService.getAllCmtByStatus(status);
        else filmCmtList = filmCmtService.getAllCmt();

        List<CommentResponse> commentResponseList = new ArrayList<>();
        for (FilmCmt filmCmt: filmCmtList) {
            CommentResponse commentResponse = setDataCommentResponse(filmCmt, request);
            commentResponseList.add(commentResponse);
        }
        return  ResponseEntity.ok(commentResponseList);
    }

    // get all comment
    @GetMapping("/api/v1/public/film/comment/{id}")
    private ResponseEntity<List<CommentResponse>> getAllCommentByFilmAndStatus(@PathVariable("id") long id, HttpServletRequest request) {

        List<FilmCmt> filmCmtList = filmCmtService.getAllCmtByFilmAndStatus(id, true);
        List<CommentResponse> commentResponseList = new ArrayList<>();
        for (FilmCmt filmCmt: filmCmtList) {
            CommentResponse commentResponse = setDataCommentResponse(filmCmt, request);
            commentResponseList.add(commentResponse);
        }
        return  ResponseEntity.ok(commentResponseList);
    }

    // insert comment
    @PostMapping("/api/v1/user/film/comment/{id}")
    private ResponseEntity<CommentResponse> insertFilmComment(@PathVariable("id") long id,
                                                @RequestBody FilmCmtModel filmCmtModel,
                                                HttpServletRequest request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        FilmCmt filmCmt = filmCmtService.insertFilmCmt(email, filmCmtModel, id);

        CommentResponse commentResponse = setDataCommentResponse(filmCmt, request);

        return ResponseEntity.ok(commentResponse);

    }

    public CommentResponse setDataCommentResponse(FilmCmt filmCmt, HttpServletRequest request) {
        CommentResponse commentResponse = new CommentResponse();

        commentResponse.setCommentContent(filmCmt.getCommentContent());
        commentResponse.setFilmCmtId(filmCmt.getFilmCmtId());
        commentResponse.setFilmCmtParentId(filmCmt.getFilmCmtParentId());
        commentResponse.setStatus(filmCmt.isStatus());
        commentResponse.setCreatedAt(filmCmt.getCreatedAt());
        commentResponse.setUserName(filmCmt.getUser().getRealUserName());

        commentResponse.setUserAvatar(ApplicationUrl.getUrlImage(filmCmt.getUser().getAvatar(),request));
        return commentResponse;

    }

    // accept commend
    @PutMapping("/api/v1/private/film/comment/{id}") // id : is id of film
    private ResponseEntity<ObjectResponse> updateFilmComment(@PathVariable("id") long id) {


        filmCmtService.updateFilmCmt(id);

        return  ResponseEntity.ok(new ObjectResponse(
                201,
                "Update oke"
        ));
    }

    // delete film comment
    @DeleteMapping("/api/v1/private/film/comment/{id}") // id : is id of film
    private ResponseEntity<ObjectResponse> deleteFilmComment(@PathVariable("id") long id) {

        filmCmtService.deleteFilm(id);

        return  ResponseEntity.ok(new ObjectResponse(
                201,
                "delete oke"
        ));
    }






}
