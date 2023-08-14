package com.br.personniMoveis.service;

import com.br.personniMoveis.exception.ResourceNotFoundException;
import com.br.personniMoveis.model.product.Tag;
import com.br.personniMoveis.repository.TagRepository;

import java.util.List;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TagService {

    private final TagRepository tagRepository;

    @Autowired
    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public Tag findTagByIdOrThrowNotFoundException(Long tagId) {
        return tagRepository.findById(tagId).orElseThrow(() -> new ResourceNotFoundException("Tag not found"));
    }

    public Page<Tag> getAllTags(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    public List<Tag> getAllTagsFromProductById(Long productId) {
        return tagRepository.findTagsByProductsProductId(productId);
    }

    public void createTag(Tag tag) {
        tagRepository.save(tag);
    }

    public void updateTag(Long tagId, String tagName) {
        Tag updatedTag = findTagByIdOrThrowNotFoundException(tagId);
        updatedTag.setTagName(tagName);
        tagRepository.save(updatedTag);
    }

    @Transactional
    public void deleteTag(Long tagId) {
        Tag tag = findTagByIdOrThrowNotFoundException(tagId);
        // Deleta a tag de todos os produtos que a possuem.
        tag.getProducts().forEach(p -> p.getTags().clear());
        // Deleta tag
        tagRepository.deleteById(tagId);
    }
}
