package com.makersharks.assessment;

import com.makersharks.assessment.repository.supplier.SupplierRepository;
import com.makersharks.assessment.service.LocationService;
import com.makersharks.assessment.service.impl.supplier.SupplierServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MakersharksAssesmentApplicationTests {

	@Mock
	private SupplierRepository supplierRepository;

	@Mock
	private LocationService locationService;


	@InjectMocks
	private SupplierServiceImpl service;

	@Test
	void contextLoads() {
	}

}
