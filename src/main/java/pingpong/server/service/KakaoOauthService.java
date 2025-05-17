package pingpong.server.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.RequiredArgsConstructor;
import pingpong.server.dto.KakaoUserInfoDto;

@Service
@RequiredArgsConstructor
public class KakaoOauthService {

	@Value("${kakao.client-id}")
	private String clientId;

	@Value("${kakao.redirect-uri}")
	private String redirectUri;

	public KakaoUserInfoDto getUserInfo(String code) {
		String accessToken = requestAccessToken(code);
		return requestUserInfo(accessToken);
	}

	private String requestAccessToken(String code) {
		String tokenUrl = "https://kauth.kakao.com/oauth/token";
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		String body = "grant_type=authorization_code"
				+ "&client_id=" + clientId
				+ "&redirect_uri=" + redirectUri
				+ "&code=" + code;

		HttpEntity<String> entity = new HttpEntity<>(body, headers);
		ResponseEntity<JsonNode> response = restTemplate.exchange(tokenUrl, HttpMethod.POST, entity, JsonNode.class);

		return response.getBody().get("access_token").asText();
	}

	private KakaoUserInfoDto requestUserInfo(String accessToken) {
		String userInfoUrl = "https://kapi.kakao.com/v2/user/me";
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<Void> entity = new HttpEntity<>(headers);
		ResponseEntity<JsonNode> response = restTemplate.exchange(userInfoUrl, HttpMethod.GET, entity, JsonNode.class);

		JsonNode kakaoAccount = response.getBody().get("kakao_account");
		String email = kakaoAccount.get("email").asText();
		String nickname = kakaoAccount.get("profile").get("nickname").asText();

		return new KakaoUserInfoDto(email, nickname);
	}
}