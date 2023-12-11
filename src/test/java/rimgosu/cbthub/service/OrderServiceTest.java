package rimgosu.cbthub.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.templateresolver.FileTemplateResolver;
import rimgosu.cbthub.domain.Address;
import rimgosu.cbthub.domain.Member;
import rimgosu.cbthub.domain.Order;
import rimgosu.cbthub.domain.OrderStatus;
import rimgosu.cbthub.domain.item.Book;
import rimgosu.cbthub.domain.item.Item;
import rimgosu.cbthub.exception.NotEnoughStockException;
import rimgosu.cbthub.repository.OrderRepository;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest
public class OrderServiceTest {

    @Autowired EntityManager em;
    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception{
        // given
        Member member = createMember();

        Book book = createBook("시골 jpa", 10000, 10);

        // when
        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);


        // then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals("상품 주문시 상태는 order", OrderStatus.ORDER, getOrder.getStatus());
        assertEquals("주문한 상품 수가 정확해야 한다", 1, getOrder.getOrderItems().size());
        assertEquals("주문 가격은 가격 * 수량이다.", 10000 * orderCount, getOrder.getTotalPrice());
        assertEquals("주문하면 재고가 줄어야 한다", 8, book.getStockQuantity());

    }

    @Test(expected = NotEnoughStockException.class)
    public void 상품주문_재고수량초과() throws Exception{
        // given
        Member member = createMember();
        Book book = createBook("시골 jpa", 10000, 10);

        int orderCount = 11;

        // when
        orderService.order(member.getId(), book.getId(), orderCount);

        // then
        fail("재고 수량 부족 예외가 발생하지 않아야 한다");
    }

    @Test
    public void 주문취소() throws Exception{
        // given
        Member member = createMember();
        Book item = createBook("시골 jpa", 10000, 10);

        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        // when
        orderService.cancelOrder(orderId);

        // then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals("주문 취소시 상태는 cancel이다.", OrderStatus.CANCEL, getOrder.getStatus());
        assertEquals("주문이 취소된 상품은 그만큼 재고가 증가해야 한다.", 10, item.getStockQuantity());

    }



    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울", "강가", "123-123"));
        em.persist(member);
        return member;
    }




}