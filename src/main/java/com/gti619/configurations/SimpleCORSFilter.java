package com.gti619.configurations;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Source : https://stackoverflow.com/questions/22846309/cors-filter-not-working-as-intended
 *
 * Filtre qui protège l'utilisateur d'une attaque de type CSRF ("Cross-Site Request Forgery").
 *
 * Le code suivant a été directement tiré de la source précédemment mentionnée.
 */

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SimpleCORSFilter implements Filter {

    @Override
    /**
     * Initialiser le filtre de configuration
     * @param fc la configuration du filtre
     * @throws ServletException
     */
    public void init(FilterConfig fc) throws ServletException {
    }

    @Override
    /**
     * Exécuter le filtre de configuration
     * @param req la requête
     * @param resp la réponse
     * @param chain la chaîne de filtrage
     * @throws IOException
     * @throws ServletException
     */
    public void doFilter(ServletRequest req, ServletResponse resp,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpServletRequest request = (HttpServletRequest) req;
        response.setHeader("Access-Control-Allow-Origin", "localhost:8080/**");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with, authorization, Content-Type," +
                "Authorization, credential, X-XSRF-TOKEN");

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            chain.doFilter(req, resp);
        }
    }

    @Override
    public void destroy() {
    }
}