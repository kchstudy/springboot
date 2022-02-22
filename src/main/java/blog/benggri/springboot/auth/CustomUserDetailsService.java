package blog.benggri.springboot.auth;

import blog.benggri.springboot.jpa.entity.usr.UsrEntity;
import blog.benggri.springboot.jpa.repository.usr.UsrRepository;
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
    private UsrRepository usrRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        STEP(log, "Member");
        Optional<UsrEntity> entityMember = usrRepository.findByUsrId(username);
        STEP(log, "[entityMember]["+entityMember+"]");

        return usrRepository.findByUsrId(username)
                            .map(this::createUserDetails)
                            .orElseThrow(()-> new UsernameNotFoundException("존재하지 않는 회원입니다."));
    }

    private UserDetails createUserDetails(UsrEntity usrEntity) {
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(usrEntity.getUsrId());
//        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(usrEntity.getUsrClsCd());
        return new User(
                usrEntity.getUsrId(), usrEntity.getEncUsrPwd(), Collections.singleton(grantedAuthority)
        );
    }
}
