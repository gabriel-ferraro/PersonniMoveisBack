package com.br.personniMoveis.controller;

import com.br.personniMoveis.model.product.Tag;
import com.br.personniMoveis.service.product.TagService;
import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("tags")
@SecurityRequirement(name = "bearer-key")
public class TagController {

    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping(path = "/{tagId}")
    public ResponseEntity<Tag> getTagById(@PathVariable("tagId") Long tagId) {
        return ResponseEntity.ok(tagService.findTagOrThrowNotFoundException(tagId));
    }

    @GetMapping
    public ResponseEntity<List<Tag>> getAllTags() {
        return ResponseEntity.ok(tagService.getAllTags());
    }

    @PostMapping
    public ResponseEntity<Tag> createTag(@RequestBody Tag tag) {
        return ResponseEntity.ok(tagService.createTag(tag));
    }

    @PutMapping("/{tagId}")
    public ResponseEntity<HttpStatus> updateTag(@PathVariable("tagId") Long tagId, @RequestBody JsonNode reqBody) {
        // Adquire atributo "tagName" do json.
        String tagName = reqBody.get("tagName").asText();
        tagService.updateTag(tagId, tagName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{tagId}")
    public ResponseEntity<HttpStatus> deleteTag(@PathVariable("tagId") Long tagId) {
        tagService.deleteTag(tagId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
