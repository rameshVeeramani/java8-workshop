package victor.training.java8.advanced;

import victor.training.java8.advanced.model.Order;
import victor.training.java8.advanced.model.OrderLine;
import victor.training.java8.advanced.model.Product;
import victor.training.java8.advanced.repo.ProductRepo;

import java.time.LocalDate;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

// get the products frequently ordered during the past year


public class StreamWreck {
	private ProductRepo productRepo;

	public List<Product> getFrequentOrderedProducts(List<Order> orders) {
		return orders.stream()
				.filter(o -> o.getCreationDate().isAfter(LocalDate.now().minusYears(1)))
				.flatMap(o -> o.getOrderLines().stream())
				.collect(groupingBy(OrderLine::getProduct, summingInt(OrderLine::getItemCount)))
				.entrySet()
				.stream()
				.filter(e -> e.getValue() >= 10)
				.map(Entry::getKey)
				.filter(p -> !p.isDeleted())
				.filter(p -> !productRepo.findByHiddenTrue().stream().map(Product::getId).collect(toSet()).contains(p.getId()))
				.collect(toList());
	}
}


