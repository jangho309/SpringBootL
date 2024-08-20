package com.bit.springboard.controller;

import com.bit.springboard.dto.MemberDto;
import com.bit.springboard.dto.ResponseDto;
import com.bit.springboard.service.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.lang.reflect.Member;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    // @Controller를 사용해서 Contoller bean객체를 생성했을 때는
    // String 값을 리턴했을 때 ViewResolver가 동작했지만
    // @RestController에서 String 값을 리턴하면
    // ViewResolver가 동작하지 않고 String 값을 그대로 리턴한다.
    // @RestController에서 화면을 리턴하기 위해서는
    // ModelAndView 객체를 리턴해야 한다.
    @GetMapping("/join")
    public ModelAndView joinView() {
        ModelAndView mav = new ModelAndView();

//        mav.addObject("name", "bitcamp");
        mav.setViewName("member/join");

        return mav;
    }

    @GetMapping("/login")
    public ModelAndView loginView() {
        ModelAndView mav = new ModelAndView();

        mav.setViewName("member/login");
        return mav;
    }

    @PostMapping("/username-check")
    public ResponseEntity<?> usernameCheck(MemberDto memberDto) {
        ResponseDto<Map<String, String>> responseDto = new ResponseDto<>();
        Map<String, String> returnMap = new HashMap<>();

        try {
            returnMap = memberService.usernameCheck(memberDto.getUsername());

            responseDto.setStatusCode(HttpStatus.OK.value());
            responseDto.setStatusMessage("OK");
            responseDto.setData(returnMap);

            return ResponseEntity.ok(responseDto);
        } catch(Exception e) {
            if(e.getMessage().equals("username duplicated")) {
                // 사용자 정의 에러코드
                responseDto.setStatusCode(601);
            } else {
                responseDto.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            }
            responseDto.setStatusMessage(e.getMessage());

            return ResponseEntity.internalServerError().body(responseDto);
        }
    }

    @PostMapping("/nickname-check")
    public ResponseEntity<?> nicknameCheck(MemberDto memberDto) {
        ResponseDto<Map<String, String>> responseDto = new ResponseDto<>();

        try {
            Map<String, String> returnMap = memberService.nicknameCheck(memberDto.getNickname());

            responseDto.setStatusCode(200);
            responseDto.setStatusMessage("OK");
            responseDto.setData(returnMap);

            return ResponseEntity.ok(responseDto);
        } catch (Exception e) {
            if(e.getMessage().equals("nickname duplicated")) {
                responseDto.setStatusCode(602);
            } else {
                responseDto.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            }

            responseDto.setStatusMessage(e.getMessage());

            return ResponseEntity.internalServerError().body(responseDto);
        }
    }

    @PostMapping("/join")
    public ModelAndView join(MemberDto memberDto) {
        memberService.join(memberDto);

        ModelAndView mav = new ModelAndView();
        mav.setViewName("member/login");

        return mav;
    }

    @PostMapping("/login")
    public ModelAndView login(MemberDto memberDto,
                              HttpSession session,
                              HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();

        try {
            MemberDto loginMember = memberService.login(memberDto);

            session.setAttribute("loginMember", loginMember);

            // "/"로 리다이렉트 시키기
            response.sendRedirect("/");
        } catch(Exception e) {
            mav.addObject("loginFailMsg", e.getMessage());
            mav.setViewName("member/login");
        }

        return mav;
    }

    @GetMapping("/logout")
    public void logout(HttpSession session,
                       HttpServletResponse response) {
        try {
            session.invalidate();
            response.sendRedirect("/member/login");
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }






}
