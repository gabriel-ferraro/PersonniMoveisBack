package com.br.personniMoveis.service;

import com.br.personniMoveis.dto.product.DetailDto;
import com.br.personniMoveis.exception.ResourceNotFoundException;
import com.br.personniMoveis.mapper.product.DetailMapper;
import com.br.personniMoveis.model.product.Detail;
import com.br.personniMoveis.repository.DetailRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetailService {

    private final DetailRepository detailRepository;

    public DetailService(DetailRepository detailRepository) {
        this.detailRepository = detailRepository;
    }

    public Detail findDetailOrThrowNotFoundException(Long detailId) {
        return detailRepository.findById(detailId).orElseThrow(
                () -> new ResourceNotFoundException("Detalhe do produto não existe"));
    }

    public List<DetailDto> getAllDetails() {
        return detailRepository.findAll().stream()
                .map(DetailMapper.INSTANCE::detailToDetailGetDto).toList();
    }

    public Detail createDetail(Detail detail) {
        return detailRepository.save(detail);
    }

    public DetailDto createDetail(DetailDto detailDto) {
        // Persistindo e retornando detail.
        Detail newDetail = detailRepository.save(DetailMapper.INSTANCE.detailDtoToDetail(detailDto));
        return DetailMapper.INSTANCE.detailToDetailGetDto(newDetail);
    }

    public void updateDetail(Long detailId, DetailDto detailDto) {
        this.findDetailOrThrowNotFoundException(detailId);
        Detail detailToBeUpdated = DetailMapper.INSTANCE.detailDtoToDetail(detailDto);
        detailToBeUpdated.setDetailId(detailId);
        detailRepository.save(detailToBeUpdated);
    }

    public void updateDetail(Detail detail) {
        detailRepository.save(detail);
    }

    public void deleteDetail(Long detailId) {
        detailRepository.delete(findDetailOrThrowNotFoundException(detailId));
    }
}
