package com.br.personniMoveis.service.product;

import com.br.personniMoveis.exception.ResourceNotFoundException;
import com.br.personniMoveis.model.product.Material;
import com.br.personniMoveis.repository.MaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<Material> getAllMaterials() {
        return materialRepository.findAll();
    }

    public Material saveMaterial(Material material) {
        return materialRepository.save(material);
    }

    public Material updateMaterial(Long materialId, Material material) {
        Material updatedMaterial = findMaterialByIdOrThrowNotFoundException(materialId);
        updatedMaterial.setMaterialId(materialId);
        updatedMaterial = this.saveMaterial(material);
        return updatedMaterial;
    }

    public void deleteMaterial(Long materialId) {
        findMaterialByIdOrThrowNotFoundException(materialId);
        materialRepository.deleteById(materialId);
    }
}
