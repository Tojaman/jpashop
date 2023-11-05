package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class) // JUnit 실행할 때 스프링과 같이 실행
@SpringBootTest // 스프링 부트를 띄운 상태로 테스트 하기 위함
@Transactional // 테스트 끝나면 전부 롤백
public class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    EntityManager em;
    @Test
    @Rollback(false) // false로 설정하면 롤백 안하고 커밋 해버림? -> DB에 저장됨
    public void 회원가입() throws Exception {
        // given
        Member member = new Member();
        member.setName("cho");

        // when
        Long saveId = memberService.join(member);


        // then
        em.flush();
        assertEquals(member, memberRepository.findOne(saveId));

    }
//    IllegalStateException이 발생하는지 확인(발생하면 테스트 성공, 발생하지 않으면 테스트 실패)
    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception {
        // given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        // when
        memberService.join(member1);
        memberService.join(member2);

//        예외가 발생을 확인하기 위한 try catch 대신 @Test(expected = IllegalStateException.class)을 사용할 수 있음
//        memberService.join(member1);
//        try {
//            memberService.join(member2); // 예외가 발생 해야함
//        } catch (IllegalStateException e) {
//            return;
//        }

        // then
        fail("예외가 발생해야 한다."); // 위에서 예외가 발생하므로 then까지 오면 안됨
    }
}