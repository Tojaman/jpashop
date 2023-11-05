package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
// JPA의 로직들은 가급적이면 Transaction 안에서 실행되어야 함
// readOnly = true 옵션을 주면 JPA 조회 성능 최적화(읽기 전용이므로 필요 없는 로직 뺴서 리소스 덜 씀)
// 클래스에 @Transactional 쓰면 내부 함수들 전체에 적용
@Transactional(readOnly = true)

// 모든 필드에 대한 생성자를 생성해줌
// @AllArgsConstructor

// final 필드에 대한 생성자만 만들어줌
@RequiredArgsConstructor
public class MemberService {

//    필드 주입
//    단점 1. 변경 불가능(test할 때 바꿔야 하는 경우가 있는 바꿀 수가 없음)
    @Autowired
    private MemberRepository memberRepository;

//    해결 방법
//    1. setter 주입 : MemberRepository 객체 불변성 보장 X(중간에 바꿀 수 있음)
//    단점 :
    @Autowired
    public void setMemberRepository(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    //    2. 생성자 주입 : MemberRepository 객체 불변성 보장 O(final 키워드 사용)
    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

//    public static void main(String[] args) {
//        MemberService memberService = new MemberService(Mock()); // 생성 시점에 어디에 의존하는지를 명확히 알 수 있음?
//    }

    /**
     * 회원가입
     */
    @Transactional // 함수에 @Transactional을 사용하면 클래스 수준의 @Transactional보다 우선 -> readOnly = true 무시
    public Long join(Member member) {
        // 중복 회원 검증
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    // 중복 회원 검증 로직
    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    // 회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    // 특정 회원 단건 조회
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}
