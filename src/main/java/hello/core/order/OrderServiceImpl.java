package hello.core.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;

@Component // 구현체 클래스에 사용
public class OrderServiceImpl implements OrderService {

    // @Autowired 필드 주입, 권장하지 않음, DI 프레임워크가 없으면 사용 불가
    private final MemberRepository memberRepository; // final이 붙으면 수정 안되고 생성자나 디폴트 값으로 한번만 값 할당 가능
    private final DiscountPolicy discountPolicy;

    // @Autowired
    // public void setMemberRepository(MemberRepository memberRepository) {
    // this.memberRepository = memberRepository;
    // }

    // @Autowired
    // public void setDiscountPolicy(DiscountPolicy discountPolicy) {
    // this.discountPolicy = discountPolicy;
    // }

    @Autowired // 생성자에 사용 , 생성자가 딱 1개만 있으면 생략 가능
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        System.out.println("====================== 1. OrderServiceImpl");
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {

        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);

    }

    // 테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }

}
