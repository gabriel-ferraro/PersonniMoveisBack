package com.br.personniMoveis.service.product;

import com.br.personniMoveis.exception.ResourceNotFoundException;
import com.br.personniMoveis.model.product.Tag;
import com.br.personniMoveis.repository.TagRepository;
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

    public Tag findTagOrThrowNotFoundException(Long tagId) {
        return tagRepository.findById(tagId).orElseThrow(() -> new ResourceNotFoundException("Tag não encontrada."));
    }

    public Page<Tag> getAllTags(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    public Tag createTag(Tag tag) {
        return tagRepository.save(tag);
    }

    public void updateTag(Long tagId, String tagName) {
        Tag updatedTag = this.findTagOrThrowNotFoundException(tagId);
        updatedTag.setTagName(tagName);
        tagRepository.save(updatedTag);
    }

    public void deleteTag(Long tagId) {
        Tag tag = this.findTagOrThrowNotFoundException(tagId);
        // Deleta a tag de todos os produtos que a possuem.
        tag.getProducts().forEach(p -> p.getTags().removeIf(t -> t.getTagId().equals(tagId)));
        // Deleta tag
        tagRepository.deleteById(tagId);
    }
}
