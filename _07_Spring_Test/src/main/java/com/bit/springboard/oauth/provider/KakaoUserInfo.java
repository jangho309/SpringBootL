package com.bit.springboard.oauth.provider;

import java.util.Map;

// 카카오 자원 서버로부터 전달받은 사용자의 정보를 매핑할 클래스
public class KakaoUserInfo implements OAuth2UserInfo {
    /**
     * Kakao 자원 서버에서 제공하는 사용자 정보 데이터 형식
     * {
     *     id: 28312749174891,
     *     kakao_account: {
     *         profile: {
     *             nickname: 'bit'
     *         },
     *         email: 'bit@bitcamp.co.kr'
     *     }
     * }
     */
    Map<String, Object> attributes;

    // kakao_account의 내용만 담아줄 map
    Map<String, Object> properties;

    public KakaoUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
        this.properties = (Map<String, Object>) attributes.get("kakao_account");
    }

    @Override
    public String getProviderId() {
        return this.attributes.get("id").toString();
    }

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getEmail() {
        return this.properties.get("email").toString();
    }

    @Override
    public String getName() {
        Map<String, Object> profile = (Map<String, Object>) this.properties.get("profile");
        return profile.get("nickname").toString();
    }
}
