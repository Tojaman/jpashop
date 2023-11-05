package jpabook.jpashop.domain;

import jakarta.persistence.*;
import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany
    // 관계형DB에선 다대다 관계에선 컬렉션 관계?를 못가져서 "다대일 - 일대다" 형태로 매핑해주는 중간 테이블 필요함
    @JoinTable(name = "categoey_item",
        joinColumns = @JoinColumn(name = "category_id"), // 중간 테이블에서 category 쪽으로 들어 가는 애
            inverseJoinColumns = @JoinColumn(name = "item_id")) // 중간 테이블에서 item 쪽으로 들어 가는 애
    private List<Item> items = new ArrayList<>();

    // // XToOne = 기본이 즉시로딩(EAGER)이므로 LAZY로 변경해줘야 한다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

    //==연관관계 메서드==//
    public void addChildCategory(Category child) {
        this.child.add(child);
        child.setParent(this);
    }
}
