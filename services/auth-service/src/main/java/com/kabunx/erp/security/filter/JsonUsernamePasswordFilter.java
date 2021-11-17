package com.kabunx.erp.security.filter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JsonUsernamePasswordFilter extends UsernamePasswordAuthenticationFilter {
    private final ObjectMapper objectMapper;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        UsernamePasswordAuthenticationToken authToken;
        try {
            ServletInputStream stream = request.getInputStream();
            JsonNode jsonNode = objectMapper.readTree(stream);
            String username = jsonNode.get(SPRING_SECURITY_FORM_USERNAME_KEY).textValue();
            String password = jsonNode.get(SPRING_SECURITY_FORM_PASSWORD_KEY).textValue();
            authToken = new UsernamePasswordAuthenticationToken(username, password);
        } catch (IOException e) {
            return null;
        }
        setDetails(request, authToken);
        return this.getAuthenticationManager().authenticate(authToken);
    }
}
