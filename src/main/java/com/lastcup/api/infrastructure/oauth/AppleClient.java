package com.lastcup.api.infrastructure.oauth;

import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class AppleClient implements OAuthTokenVerifier {

    private static final String APPLE_ISSUER = "https://appleid.apple.com";

    private final RestClient restClient = RestClient.builder().build();
    private final String keyUrl;
    private final String clientId;

    public AppleClient(
            @Value("${app.oauth.apple.key-url}") String keyUrl,
            @Value("${app.oauth.apple.client-id}") String clientId
    ) {
        this.keyUrl = keyUrl;
        this.clientId = clientId;
    }

    @Override
    public SocialProvider getProvider() {
        return SocialProvider.APPLE;
    }

    @Override
    public VerifiedOAuthUser verify(String idToken) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(idToken);
            JWSHeader header = signedJWT.getHeader();

            RSAKey key = fetchKey(header.getKeyID());
            JWSVerifier verifier = new RSASSAVerifier(key.toRSAPublicKey());

            if (!signedJWT.verify(verifier)) {
                throw new OAuthVerificationException("APPLE_ID_TOKEN_INVALID");
            }

            JWTClaimsSet claims = signedJWT.getJWTClaimsSet();

            if (!APPLE_ISSUER.equals(claims.getIssuer())) {
                throw new OAuthVerificationException("APPLE_ID_TOKEN_ISSUER_INVALID");
            }

            if (!claims.getAudience().contains(clientId)) {
                throw new OAuthVerificationException("APPLE_ID_TOKEN_AUDIENCE_INVALID");
            }

            return new VerifiedOAuthUser(
                    claims.getSubject(),
                    claims.getStringClaim("email"),
                    null
            );
        } catch (Exception e) {
            throw new OAuthVerificationException("APPLE_ID_TOKEN_INVALID");
        }
    }

    private RSAKey fetchKey(String keyId) {
        try {
            String jwkJson = restClient.get()
                    .uri(keyUrl)
                    .retrieve()
                    .body(String.class);

            JWKSet jwkSet = JWKSet.parse(jwkJson);
            JWK jwk = jwkSet.getKeyByKeyId(keyId);

            if (!(jwk instanceof RSAKey rsaKey)) {
                throw new OAuthVerificationException("APPLE_JWK_TYPE_INVALID");
            }
            return rsaKey;
        } catch (Exception e) {
            throw new OAuthVerificationException("APPLE_JWK_FETCH_FAILED");
        }
    }
}
