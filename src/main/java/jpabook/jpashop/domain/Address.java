package jpabook.jpashop.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable // 임베디드 자료형으로 사용하기 위한 선언
@Getter // Setter가 열려 있으면 변경 포인트가 너무 많아서 유지보수 어려움. 따라서 실무에선 가급적 Getter만 사용
public class Address {

    private String city;
    private String street;
    private String zipcode;

    // JPA가 생성을 할 때 기본 생성자가 필요함
    // 1. 외부에서 직접 호출 되지 않게 하기위해(너무 아무나 막 만들면 안되니깐) protected(JPA가 protected까지 지원)
    // 2. 무결성 유지를 위해 객체는 수정되면 안되고 수정하고 싶으면 새로운 객체를 만들어야함.
    //    따라서 protected로 선언(public은 외부에서 접근 가능, 수정 가능)
    protected Address() {
    }

    // 위 기본 생성자와 다르게 외부에서 호출 가능 But 기본 생성자와 같이 수정 불가능(Getter만 설정)
    // 기본 생성자와 같이 생성할 때만 생성되고 변경을 하고 싶으면 새로 만들어야 함
    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
