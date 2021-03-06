package com.capitalone.dashboard.service;

import com.capitalone.dashboard.model.Collector;
import com.capitalone.dashboard.model.CollectorItem;
import com.capitalone.dashboard.model.CollectorType;
import com.capitalone.dashboard.repository.CollectorItemRepository;
import com.capitalone.dashboard.repository.CollectorRepository;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CollectorServiceTest {
	private static final String FILTER_STRING = "";

	@Mock
	private CollectorRepository collectorRepository;
	@Mock
	private CollectorItemRepository collectorItemRepository;
	@InjectMocks
	private CollectorServiceImpl collectorService;

	@Test
	@SuppressWarnings("unchecked")
	public void collectorItemsByType() {
		Collector c = makeCollector();
		CollectorItem item1 = makeCollectorItem();
		CollectorItem item2 = makeCollectorItem();
		when(collectorRepository.findByCollectorType(CollectorType.Build)).thenReturn(Arrays.asList(c));

		Page<CollectorItem> page = new PageImpl<>(Arrays.asList(item1, item2), PageRequest.of(1, 1), 2);
		when(collectorItemRepository.findByCollectorIdAndSearchField(any(List.class),any(String.class), any(String.class),
				any(Pageable.class))).thenReturn(page);
		Page<CollectorItem> items = collectorService.collectorItemsByTypeWithFilter(CollectorType.Build, FILTER_STRING,
				PageRequest.of(1, 1));
		assertThat(items.getTotalElements(), is(2L));
		assertTrue(items.getContent().contains(item1));
		assertTrue(items.getContent().contains(item2));
	}

	private Collector makeCollector() {
		Collector collector = new Collector();
		collector.setId(ObjectId.get());
		return collector;
	}

	private CollectorItem makeCollectorItem() {
		CollectorItem item = new CollectorItem();
		item.setId(ObjectId.get());
		return item;
	}
}