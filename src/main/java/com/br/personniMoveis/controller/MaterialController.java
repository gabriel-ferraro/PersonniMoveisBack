package com.br.personniMoveis.controller;

import com.br.personniMoveis.model.product.Material;
import com.br.personniMoveis.service.product.MaterialService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("materials")
@SecurityRequirement(name = "bearer-key")
public class MaterialController {

    private final MaterialService materialService;

    @Autowired
    public MaterialController(MaterialService materialService) {
        this.materialService = materialService;
    }

    @GetMapping
    public ResponseEntity<List<Material>> getAllMaterial() {
        return ResponseEntity.ok(materialService.getAllMaterials());
    }

    @PostMapping
    public ResponseEntity<Material> createMaterial(@RequestBody @Valid Material material) {
        return ResponseEntity.ok(materialService.saveMaterial(material));
    }

    @PutMapping(path = "/{materialId}")
    public ResponseEntity<Material> updateMaterial(@PathVariable("materialId") Long materialId, @RequestBody @Valid Material material) {
        return ResponseEntity.ok(materialService.updateMaterial(materialId, material));
    }

    @DeleteMapping(path = "/{materialId}")
    public ResponseEntity<HttpStatus> deleteMaterial(@PathVariable("materialId") Long materialId) {
        materialService.deleteMaterial(materialId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
