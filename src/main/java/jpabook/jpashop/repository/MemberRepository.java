package jpabook.jpashop.repository;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceUnit;
import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
//    엔티티 매니저 팩토리를 직접 주입(쓸 일이 별로 없음?)
//    @PersistenceUnit
//    private EntityManagerFactory em;

//    https://colevelup.tistory.com/21
//    @PersistenceContext : Spring이 EntityManager를 생성해서 스프링 빈에 주입
//    즉, 앤티티 매니저가 영속성 컨텍스트를 사용할 수 있게 함
//    @PersistenceContext
//    private EntityManager em;

//    public MemberRepository(EntityManager em) {
//        this.em = em;
//    }

//    @PersistenceContext 대신 @Autowired를 사용할 수 있음
//    -> 생성자 주입을 사용할 수 있음 -> 생성자가 하나이므로 @Autowired를 생략할 수 있음
//    -> lombok 어노테이션인 @RequiredArgsConstructor를 이용해서 생성자를 생성할 수 있음
    private final EntityManager em;

//    em.persist(member) : 앤티티 매니저(em)을 통해 엔티티(member)를 영속성 컨텍스트에 저장
    public void save(Member member) {
        em.persist(member); // JPA에 member 저장
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id); // member를 찾아서 반환
    }

//    리스트 조회
    public List<Member> findAll() {
//        JPQL 사용 : 객체를 대상으로 쿼리(<-> SQL : 테이블 대상으로 쿼리) (from의 대상이 테이블이 아닌 객체)
//        ctrl+alt+n로 리턴값과 합치기
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();

//        아래 코드를 하나의 코드로 합침 ↑
//        List<Member> result = em.createQuery("select m from Member", Member.class)
//                .getResultList();
//        return result;
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }

}
