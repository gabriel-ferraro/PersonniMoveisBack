package com.br.personniMoveis.service.product;

import com.br.personniMoveis.exception.ResourceNotFoundException;
import com.br.personniMoveis.model.product.Material;
import com.br.personniMoveis.repository.MaterialRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaterialService {

    private final MaterialRepository materialRepository;

    @Autowired
    public MaterialService(MaterialRepository materialRepository) {
        this.materialRepository = materialRepository;
    }

    public Material findMaterialByIdOrThrowNotFoundException(Long materialId) {
        return materialRepository.findById(materialId).orElseThrow(() -> new ResourceNotFoundException("Material not found"));
    }

    public Page<Material> getAllMaterials(Pageable pageable) {
        return materialRepository.findAll(pageable);
    }

//    public List<Material> getAllMaterialsFromProductById(Long productId) {
//        return materialRepository.findMaterialsByProductsProductId(productId);
//    }

    public void createMaterial(Material material) {
        materialRepository.save(material);
    }

//    public void updateMaterial(Long materialId, String materialName) {
//        Material updatedMaterial = findMaterialByIdOrThrowNotFoundException(materialId);
//        updatedMaterial.setMaterialName(materialName);
//        materialRepository.save(updatedMaterial);
//    }

//    @Transactional
//    public void deleteMaterial(Long materialId) {
//        Material material = findMaterialByIdOrThrowNotFoundException(materialId);
//        // Deleta o material de todos os produtos que o tem.
//        material.getProduct().forEach(p -> p.getMaterials().clear());
//        // Deleta material
//        materialRepository.deleteById(materialId);
//    }
}
