package rimgosu.cbthub.repository;


import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import rimgosu.cbthub.domain.Member;
import rimgosu.cbthub.service.MemberService;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class MemberRepositoryTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;


}
