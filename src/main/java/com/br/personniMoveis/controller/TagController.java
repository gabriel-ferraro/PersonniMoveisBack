//package com.br.personniMoveis.controller;
//
//import com.br.personniMoveis.model.product.Tag;
////import com.br.personniMoveis.service.GenericFilterService;
//import com.fasterxml.jackson.databind.JsonNode;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("tags")
//public class TagController {
//
//    private final TagService tagService;
////    private final GenericFilterService<Tag> genericFilterService;
//
//    @Autowired
//    public TagController(TagService tagService) {
//        this.tagService = tagService;
////        this.genericFilterService = genericFilterService;
//    }
//
//    @GetMapping(path = "/{tagId}")
//    public ResponseEntity<Tag> getTagById(@PathVariable("tagId") Long tagId) {
//        return ResponseEntity.ok(tagService.findTagByIdOrThrowNotFoundException(tagId));
//    }
//
//    @GetMapping
//    public ResponseEntity<Page<Tag>> getAllTags(Pageable pageable) {
//        return ResponseEntity.ok(tagService.getAllTags(pageable));
//    }
//
////    @GetMapping("/search")
////    public ResponseEntity<List<Tag>> searchTags() {
////        return ResponseEntity.ok(genericFilterService.findFilteredEntity(filters, 0, 0));
////    }
//
//    /**
//     * Recebe o id de um produto e identifica o produto no banco, retorna uma
//     * lista de todas tags que o produto contém.
//     *
//     * @param productId id do produto.
//     * @return uma lista de todas tags do produto.
//     */
//    @GetMapping("/product/{productId}/tags")
//    public List<Tag> getAllTagsInProductById(@PathVariable("productId") Long productId) {
//        return tagService.getAllTagsFromProductById(productId);
//    }
//
//    @PostMapping
//    public ResponseEntity<HttpStatus> createTag(@RequestBody Tag tag) {
//        tagService.createTag(tag);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
//
//    @PutMapping("/{tagId}")
//    public ResponseEntity<HttpStatus> updateTag(@PathVariable("tagId") Long tagId, @RequestBody JsonNode reqBody) {
//        // Adquire atributo "tagName" do json.
//        String tagName = reqBody.get("tagName").asText();
//        tagService.updateTag(tagId, tagName);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
//
//    @DeleteMapping("/{tagId}")
//    public ResponseEntity<HttpStatus> deleteTag(@PathVariable("tagId") Long tagId) {
//        tagService.deleteTag(tagId);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
//}
