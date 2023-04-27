package hello.core.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import lombok.RequiredArgsConstructor;

@Component // 구현체 클래스에 사용
// @RequiredArgsConstructor // final이 붙은 필드로 생성자 생성
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
    // @Qualifier 끼리 매칭 -> 빈이름 매칭 -> 예외발생
    @Autowired // 생성자에 사용 , 생성자가 딱 1개만 있으면 생략 가능
    // 타입이 같은 빈 2개가 있으면 파라미터 이름이나 필드명으로 빈이름 등록
    public OrderServiceImpl(MemberRepository memberRepository, @MainDiscountPolicy DiscountPolicy discountPolicy) {
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
