package com.lys.schedulemanagement.user;

import com.lys.schedulemanagement.jwt.JwtTokenProvider;
import com.lys.schedulemanagement.user.exception.AdminTokenMismatchException;
import com.lys.schedulemanagement.user.exception.DuplicateUsernameException;
import com.lys.schedulemanagement.user.exception.UnauthorizedExcpetion;
import com.lys.schedulemanagement.user.exception.UserNotFoundException;
import com.lys.schedulemanagement.user.model.LoginRequestDto;
import com.lys.schedulemanagement.user.model.SignupRequestDto;
import com.lys.schedulemanagement.user.model.User;
import com.lys.schedulemanagement.user.model.UserRoleEnum;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    // ADMIN_TOKEN
    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    public UserService(UserRepository userRepository, JwtTokenProvider jwtTokenProvider){
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Transactional
    public void signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        if(userRepository.findByUsername(username).isPresent()){
            throw new DuplicateUsernameException("중복된 아이디입니다.");
        }

        UserRoleEnum role = UserRoleEnum.USER;
        System.out.println(requestDto.isAdmin());
        if (requestDto.isAdmin()){
            System.out.println("!@$!@$" + requestDto.getAdminToken());
            if(!ADMIN_TOKEN.equals(requestDto.getAdminToken())){
                throw new AdminTokenMismatchException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }

        // 사용자 등록
        User user = new User(username, requestDto.getPassword(), requestDto.getNickname(), role);
        userRepository.save(user);
    }

    @Transactional
    public String login(LoginRequestDto requestDto, HttpServletResponse response) {
        User user = userRepository.findByUsername(requestDto.getUsername())
                .orElseThrow(() -> new UserNotFoundException("회원을 찾을 수 없습니다."));

        if(!user.getPassword().equals(requestDto.getPassword())){
            throw new UnauthorizedExcpetion("비밀번호가 일치하지 않습니다.");
        }

        String token = jwtTokenProvider.generateToken(user.getUsername(), user.getRole());
        jwtTokenProvider.addJwtToCookie(token, response);
        return token;
    }
}
