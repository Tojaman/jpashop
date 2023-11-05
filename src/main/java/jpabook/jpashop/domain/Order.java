package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders") // ORDER가 ORDER BY 예약어로 걸려 있는 경우가 있어서 주로 ORDERS로 사용
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id") // DB 테이블 열 이름 설정(그냥 id로 하면 다른 테이블과 구분하기 어려움)
    private Long id;

    // // XToOne = 기본이 즉시로딩(EAGER)이므로 LAZY로 변경해줘야 한다.
    @ManyToOne(fetch = FetchType.LAZY) // fetch = FetchType.LAZY : 지연 로딩을 사용
    @JoinColumn(name = "member_id")
    private Member member;

    // mappedBy : order에 의해 매핑됨

    // cascade : Entity의 상태 변화를 전파시키는 옵션
    // 즉, 원래는 persist(orderItemA), persist(orderItemA), persist(orderItemA), persist(order)을 해야하지만
    // cascade 옵션을 사용하면 persist(order)만 적어도 나머지는 알아서 전파해준다.
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    // 일대일에선 FK를 둘 중 아무대나 둬도 되고 장단점이 있음
    // 강사님은 Access를 많이 하는 곳에 둠 -> 보통 Order를 보고 Delivery를 찾는 가정이기 때문에 자주 보는 Order에 놓음

    // XToOne = 기본이 즉시로딩(EAGER)이므로 LAZY로 변경해줘야 한다.
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id") // Order가 주인(FK 가짐)
    private Delivery delivery; // 배송 정보

    private LocalDateTime orderDate; // 주문시간

    // 열거형(Enum) 타입을 데이터베이스에 매핑할 때 사용하고 OrderStatus 열거형 타입의 데이터를 문자열로 저장
    @Enumerated(EnumType.STRING)
    private OrderStatus status; // 주문 상태 [ORDER, CANCEL]

    //==연관관계 메서드==//
    // Order와 Member는 서로 연관관계고 Order에 값이 추가되면 연관관계인 Member도 추가된 Order 값을 추가해야함.
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }
}
