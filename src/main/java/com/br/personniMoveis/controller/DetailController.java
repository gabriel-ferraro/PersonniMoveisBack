package com.br.personniMoveis.controller;

import com.br.personniMoveis.service.product.DetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("details")
@CrossOrigin(origins = "*")
public class DetailController {

    private final DetailService detailService;

    @Autowired
    public DetailController(DetailService detailService) {
        this.detailService = detailService;
    }

    @DeleteMapping(path = "/{detailId}")
    public ResponseEntity<HttpStatus> deleteDetail(@PathVariable("detailId") Long detailId) {
        detailService.deleteDetail(detailId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
