package com.lastcup.api.domain.auth.service;

import com.lastcup.api.domain.auth.dto.request.SocialLoginRequest;
import com.lastcup.api.domain.auth.dto.response.AuthResponse;
import com.lastcup.api.domain.auth.dto.response.AuthTokensResponse;
import com.lastcup.api.domain.auth.dto.response.UserSummaryResponse;
import com.lastcup.api.domain.user.domain.SocialAuth;
import com.lastcup.api.domain.user.domain.User;
import com.lastcup.api.domain.user.repository.SocialAuthRepository;
import com.lastcup.api.domain.user.repository.UserRepository;
import com.lastcup.api.infrastructure.oauth.OAuthTokenVerifier;
import com.lastcup.api.infrastructure.oauth.SocialProvider;
import com.lastcup.api.infrastructure.oauth.VerifiedOAuthUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SocialLoginService {

    private final List<OAuthTokenVerifier> verifiers;
    private final UserRepository userRepository;
    private final SocialAuthRepository socialAuthRepository;
    private final NicknameGenerator nicknameGenerator;
    private final TokenService tokenService;

    public SocialLoginService(
            List<OAuthTokenVerifier> verifiers,
            UserRepository userRepository,
            SocialAuthRepository socialAuthRepository,
            NicknameGenerator nicknameGenerator,
            TokenService tokenService
    ) {
        this.verifiers = verifiers;
        this.userRepository = userRepository;
        this.socialAuthRepository = socialAuthRepository;
        this.nicknameGenerator = nicknameGenerator;
        this.tokenService = tokenService;
    }

    @Transactional
    public AuthResponse login(SocialProvider provider, SocialLoginRequest request) {
        OAuthTokenVerifier verifier = findVerifier(provider);

        VerifiedOAuthUser verified =
                verifier.verify(request.providerToken());

        String email = verified.email() != null
                ? verified.email()
                : request.email();

        VerifiedOAuthUser resolved =
                new VerifiedOAuthUser(
                        verified.providerUserKey(),
                        email,
                        verified.profileImageUrl()
                );

        return socialAuthRepository
                .findByProviderAndProviderUserKey(provider, resolved.providerUserKey())
                .map(this::loginExistingUser)
                .orElseGet(() -> signupNewUser(provider, resolved));
    }

    private OAuthTokenVerifier findVerifier(SocialProvider provider) {
        return verifiers.stream()
                .filter(v -> v.getProvider() == provider)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("unsupported provider"));
    }

    private AuthResponse loginExistingUser(SocialAuth socialAuth) {
        User user = userRepository.findById(socialAuth.getUserId())
                .orElseThrow(() -> new IllegalStateException("user not found"));

        AuthTokensResponse tokens = tokenService.createTokens(user.getId());
        return new AuthResponse(
                new UserSummaryResponse(user.getId(), user.getNickname()),
                tokens,
                false
        );
    }

    private AuthResponse signupNewUser(SocialProvider provider, VerifiedOAuthUser verified) {
        if (verified.email() != null && userRepository.existsByEmail(verified.email())) {
            throw new IllegalArgumentException("email already exists");
        }

        User user = userRepository.save(
                User.create(
                        nicknameGenerator.create(),
                        verified.email(),
                        verified.profileImageUrl()
                )
        );

        socialAuthRepository.save(
                SocialAuth.create(
                        user.getId(),
                        provider,
                        verified.providerUserKey(),
                        verified.email()
                )
        );

        AuthTokensResponse tokens = tokenService.createTokens(user.getId());
        return new AuthResponse(
                new UserSummaryResponse(user.getId(), user.getNickname()),
                tokens,
                true
        );
    }
}
