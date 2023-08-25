package com.br.personniMoveis.service;

import com.br.personniMoveis.dto.product.DetailDto;
import com.br.personniMoveis.dto.product.get.DetailGetDto;
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

    public List<DetailGetDto> getAllDetails() {
        return detailRepository.findAll().stream()
                .map(DetailMapper.INSTANCE::detailToDetailGetDto).toList();
    }

    public void updateDetail(Long detailId, DetailDto detailPostDto) {
        this.findDetailOrThrowNotFoundException(detailId);
        Detail detailToBeUpdated = DetailMapper.INSTANCE.toDetail(detailPostDto);
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
