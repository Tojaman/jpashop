package jpabook.jpashop.domain.item;


import jakarta.persistence.*;
import jpabook.jpashop.domain.Category;

import jpabook.jpashop.domain.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
// SINGLE_TABLE 전략 : 한 테이블에 자식 테이블 데이터를 다 때려 넣음(자식 테이블 구분 컬럼(DTYPE) 설정 필수
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter
@Setter
public abstract class Item { // 추상 클래스

    @Id
    @GeneratedValue // 키 값을 자동으로 생성(전략 설정 안하면 1, 2, 3... 정수로 설정됨)
    @Column(name = "item_id") // DB 테이블 열 이름
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categorys = new ArrayList<>();

    //==비즈니스 로직==//
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0) { // 재고가 -인 경우
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }
}
