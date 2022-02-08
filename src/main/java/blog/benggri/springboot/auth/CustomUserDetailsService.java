package blog.benggri.springboot.auth;

import blog.benggri.springboot.jpa.entity.member.MemberEntity;
import blog.benggri.springboot.jpa.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

import static blog.benggri.springboot.comm.util.StringUtil.STEP;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        STEP(log, "Member");
        Optional<MemberEntity> entityMember = memberRepository.findById(username);
        STEP(log, "[entityMember]["+entityMember+"]");

        return memberRepository.findById(username)
                               .map(this::createUserDetails)
                               .orElseThrow(()-> new UsernameNotFoundException("존재하지 않는 회원입니다."));
    }

    private UserDetails createUserDetails(MemberEntity memberEntity) {
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(memberEntity.getAuth());
        return new User(
                memberEntity.getId(), memberEntity.getPwd(), Collections.singleton(grantedAuthority)
        );
    }
}
