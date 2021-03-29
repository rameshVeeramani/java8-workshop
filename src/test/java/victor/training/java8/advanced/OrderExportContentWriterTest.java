package victor.training.java8.advanced;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import victor.training.java8.advanced.model.Order;
import victor.training.java8.advanced.repo.OrderRepo;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.time.LocalDate;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OrderExportContentWriterTest {
   @Mock
   private OrderRepo orderRepoMock;
   @InjectMocks
   private OrderExportContentWriter target;

   @Test
   public void test() throws IOException {
      StringWriter writer = new StringWriter();
      Order order = new Order()
          .setId(1L)
          .setCreationDate(LocalDate.parse("2021-01-01"));
      Mockito.when(orderRepoMock.findByActiveTrue()).thenReturn(Stream.of(order));
      target.writeOrders(writer);
      String actual = writer.toString();

      assertThat(actual)
          .contains("OrderID;Date\n")
          .contains("1;2021-01-01");
   }

}