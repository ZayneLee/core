package hello.core.order;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import hello.core.discount.FixDiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemoryMemberRepository;

public class OrderServiceImplTest {

    @Test
    void createOrder() {
        MemoryMemberRepository memoryMemberRepository = new MemoryMemberRepository();
        memoryMemberRepository.save(new Member(1L, "name", Grade.VIP));

        OrderServiceImpl orderServiceImpl = new OrderServiceImpl(memoryMemberRepository, new FixDiscountPolicy());
        Order order = orderServiceImpl.createOrder(1L, "itemA", 1000);
        Assertions.assertThat(order.getDiscountPrice()).isEqualTo(1000);
    }
}
