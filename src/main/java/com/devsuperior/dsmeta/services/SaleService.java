package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import com.devsuperior.dsmeta.dto.SaleSummaryBySellerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	public Page<SaleMinDTO> searchReport(String minDateStr, String maxDateStr, String name, Pageable pageable) {

		LocalDate maxDate = (maxDateStr == null || maxDateStr.isBlank())
				? LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault())
				: LocalDate.parse(maxDateStr);

		LocalDate minDate = (minDateStr == null || minDateStr.isBlank())
				? maxDate.minusYears(1L)
				: LocalDate.parse(minDateStr);

		if (name == null || name.isBlank()) name = "";

		Page<Sale> page = repository.searchAll(minDate, maxDate, name, pageable);
		return page.map(SaleMinDTO::new);
	}

	public List<SaleSummaryBySellerDTO> sumBySeller(String minDateStr, String maxDateStr) {
		LocalDate maxDate = (maxDateStr == null || maxDateStr.isBlank())
				? LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault())
				: LocalDate.parse(maxDateStr);

		LocalDate minDate = (minDateStr == null || minDateStr.isBlank())
				? maxDate.minusYears(1L)
				: LocalDate.parse(minDateStr);

		return repository.sumBySeller(minDate, maxDate);
	}

}
