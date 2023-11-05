package jpabook.jpashop.domain;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;
import org.aspectj.weaver.ast.Or;

import java.util.ArrayList;
import java.util.List;

@Entity // DB 테이블(엔티티)에 매핑(JPA 기능)(장고 model과 같은 기능)
@Getter
@Setter // lombok 사용(자동으로 생성해줌)
public class Member {

    @Id // PK 지정
    @GeneratedValue // 키 값을 자동으로 생성(전략 설정 안하면 1, 2, 3... 정수로 설정됨)
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded // 임베디드 자료형
    private Address address;

    @OneToMany(mappedBy = "member") // mappedBy : 읽기만 가능(주인이 아니라서)
    private List<Order> orders = new ArrayList<>();


}
