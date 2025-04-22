package ru.sibsutis.shop.api.security;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import ru.sibsutis.shop.api.dto.ErrorResponseDto;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        ErrorResponseDto errorResponse = new ErrorResponseDto(
                HttpServletResponse.SC_UNAUTHORIZED,
                "Unauthorized: " + authException.getMessage()
        );
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        objectMapper.writeValue(response.getWriter(), errorResponse);
//        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }
}
