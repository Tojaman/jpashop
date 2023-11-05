package jpabook.jpashop.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
// SINGLE_TABLE 전략이기 때문에 자식 테이블인 Book은 구분 컬럼(DTYPE)을 설정한다.
@DiscriminatorValue("B")
@Getter
@Setter
public class Book  extends Item {

    private String author;
    private String isbn;
}
