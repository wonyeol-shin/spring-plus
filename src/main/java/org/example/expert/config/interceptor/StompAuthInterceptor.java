package org.example.expert.config.interceptor;

import lombok.RequiredArgsConstructor;
import org.example.expert.config.JwtUtil;
import org.example.expert.domain.common.dto.AuthUser;
import org.example.expert.domain.user.enums.UserRole;
import org.example.expert.domain.user.repository.UserRepository;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.security.sasl.AuthenticationException;
import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor
public class StompAuthInterceptor implements ChannelInterceptor {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Override
    public @Nullable Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (accessor == null) {
            throw new MessageDeliveryException("message is null");
        }

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {

            String authorization = accessor.getFirstNativeHeader("Authorization");

            if (authorization == null || authorization.isEmpty() ) {
                throw new MessageDeliveryException("Invalid JWT token");
            }

            String token = jwtUtil.substringToken(authorization);

            if (!jwtUtil.validateToken(token)) {
                throw new MessageDeliveryException("Invalid JWT token");
            }

            Long id = jwtUtil.getUserId(token);
            String email = jwtUtil.extractUserEmail(token);
            String nickname = jwtUtil.extractUserNickname(token);
            UserRole role = UserRole.of(jwtUtil.extractUserRole(token));

            AuthUser authUser = new AuthUser(id,email,nickname,role);

            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(
                    "ROLE_" + role.name());

            Collection<? extends GrantedAuthority> authorities = List.of(grantedAuthority);

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(authUser, null, authorities);

            accessor.setUser(authentication);

        }

        return message;
    }
}
