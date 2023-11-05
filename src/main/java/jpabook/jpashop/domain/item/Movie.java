package jpabook.jpashop.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
// SINGLE_TABLE 전략이기 때문에 자식 테이블인 Movie은 구분 컬럼(DTYPE)을 설정한다.
@DiscriminatorValue("M")
@Getter
@Setter
public class Movie extends Item {

    private String director;
    private String actor;
}
