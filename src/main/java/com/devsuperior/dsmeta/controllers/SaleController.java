package com.devsuperior.dsmeta.controllers;

import com.devsuperior.dsmeta.dto.SaleSummaryBySellerDTO;
import com.devsuperior.dsmeta.repositories.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.services.SaleService;

import java.util.List;

@RestController
@RequestMapping(value = "/sales")
public class SaleController {

	@Autowired
	private SaleService service;

	@Autowired
	private SaleRepository repository;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<SaleMinDTO> findById(@PathVariable Long id) {
		SaleMinDTO dto = service.findById(id);
		return ResponseEntity.ok(dto);
	}

	@GetMapping(value = "/report")
	public ResponseEntity<Page<SaleMinDTO>> getReport(

		@RequestParam(value = "minDate", required = false) String minDateStr,
		@RequestParam(value = "maxDate", required = false) String maxDateStr,
		@RequestParam(value = "name", required = false) String name,
		Pageable pageable) {

			Page<SaleMinDTO> report = service.searchReport(minDateStr, maxDateStr, name, pageable);
			return ResponseEntity.ok(report);
	}

	@GetMapping(value = "/summary")
	public ResponseEntity<List<SaleSummaryBySellerDTO>> getSummaryBySeller(
			@RequestParam(value = "minDate", required = false) String minDateStr,
			@RequestParam(value = "maxDate", required = false) String maxDateStr) {

		List<SaleSummaryBySellerDTO> summary = service.sumBySeller(minDateStr, maxDateStr);
		return ResponseEntity.ok(summary);
	}
}
