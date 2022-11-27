package com.qcadoo.security.internal.filters;

import com.beust.jcommander.internal.Lists;
import org.apache.commons.codec.binary.Hex;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Objects;

public class ContentSecurityPolicyFilter implements Filter {

    private static final String ORIGIN_LOCATION_REF = "'self'";

    private static final String DEFAULT_RESPONSE_HEADER_CONTENT_SECURITY_POLICY_VALUE = "default-src 'self' 'unsafe-inline' 'unsafe-eval'";

    private static final String DEFAULT_RESPONSE_HEADER_XSS_PROTECTION_VALUE = "1; mode=block";

    private static final String RESPONSE_HEADER_CONTENT_SECURITY_POLICY = "responseHeaderContentSecurityPolicy";

    private static final String RESPONSE_HEADER_XSS_PROTECTION = "responseHeaderXSSProtection";

    /**
     * Configuration member to specify if web app use web fonts
     */
    private static final boolean APP_USE_WEBFONTS = true;

    /**
     * Configuration member to specify if web app use videos or audios
     */
    private static final boolean APP_USE_AUDIOS_OR_VIDEOS = true;

    /**
     * Configuration member to specify if filter must add CSP directive used by Mozilla (Firefox)
     */
    private static final boolean INCLUDE_MOZILLA_CSP_DIRECTIVES = true;

    private static final boolean INCLUDE_NONCE_VALUE = false;

    private static final boolean INCLUDE_SECURE_FRAMES = true;

    private static final boolean INCLUDE_ACCESS_CONTROL_ALLOW_ORIGIN = false;

    /**
     * List CSP HTTP Headers
     */
    private List<String> cspHeaders = Lists.newArrayList();

    /**
     * Collection of CSP polcies that will be applied
     */
    private String policies = null;

    /**
     * Used for Script Nonce
     */
    private SecureRandom prng = null;

    private MessageDigest sha;

    private String responseHeaderXSSProtection = DEFAULT_RESPONSE_HEADER_XSS_PROTECTION_VALUE;

    private String responseHeaderContentSecurityPolicy = DEFAULT_RESPONSE_HEADER_CONTENT_SECURITY_POLICY_VALUE;

    private boolean includeSandboxForFrames = true;

    private boolean useDefaultCSP = false;

    public void setIncludeSandboxForFrames(final boolean includeSandboxForFrames) {
        this.includeSandboxForFrames = includeSandboxForFrames;
    }

    public void setResponseHeaderXSSProtection(final String responseHeaderXSSProtection) {
        if (StringUtils.hasText(responseHeaderXSSProtection) && !RESPONSE_HEADER_XSS_PROTECTION.equals(responseHeaderXSSProtection)) {
            this.responseHeaderXSSProtection = responseHeaderXSSProtection;
        }
    }

    public void setResponseHeaderContentSecurityPolicy(final String responseHeaderContentSecurityPolicy) {
        //Define list of CSP HTTP Headers
        this.cspHeaders.add("Content-Security-Policy");
        this.cspHeaders.add("X-Content-Security-Policy");
        this.cspHeaders.add("X-WebKit-CSP");

        //If CSP was not set in properties use default one
        if (StringUtils.hasText(responseHeaderContentSecurityPolicy) && !RESPONSE_HEADER_CONTENT_SECURITY_POLICY.equals(responseHeaderContentSecurityPolicy)) {
            this.responseHeaderContentSecurityPolicy = responseHeaderContentSecurityPolicy;
        } else {
            try {
                initDefaultCSP();
            } catch (ServletException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                useDefaultCSP = true;
            }
        }
    }

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
    }

    private void initDefaultCSP() throws ServletException {
        // Init secure random
        try {
            this.prng = SecureRandom.getInstance("SHA1PRNG");

            sha = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            throw new ServletException(e);
        }

        // Define CSP policies
        // Loading policies for Frame and Sandboxing will be dynamically defined : We need to know if context use Frame
        List<String> cspPolicies = Lists.newArrayList();

        // Disable default source in order to avoid browser fallback loading using 'default-src' locations
        cspPolicies.add("default-src 'none'");
        // Define loading policies for Plugins
        cspPolicies.add("object-src " + ORIGIN_LOCATION_REF);
        // Define loading policies for Styles (CSS)
        cspPolicies.add("style-src " + ORIGIN_LOCATION_REF + " 'unsafe-inline'");
        // Define loading policies for Images
        cspPolicies.add("img-src " + ORIGIN_LOCATION_REF + " data:");
        // Define loading policies for Form
        cspPolicies.add("form-action " + ORIGIN_LOCATION_REF);
        // Define loading policies for Audios/Videos

        if (APP_USE_AUDIOS_OR_VIDEOS) {
            cspPolicies.add("media-src " + ORIGIN_LOCATION_REF);
        }

        // Define loading policies for Fonts
        if (APP_USE_WEBFONTS) {
            cspPolicies.add("font-src " + ORIGIN_LOCATION_REF);
        }

        // Define loading policies for Connection
        cspPolicies.add("connect-src " + ORIGIN_LOCATION_REF);
        // Define loading policies for Plugins Types
        // Directive plugin-types is no longer supported
        //cspPolicies.add("plugin-types application/pdf");

        // Target formatting
        this.policies = cspPolicies.toString().replaceAll("(\\[|\\])", "").replaceAll(",", ";").trim();
    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        //set X-XSS-Protection
        httpServletResponse.setHeader("X-XSS-Protection", responseHeaderXSSProtection);

        //set Content-Security-Policy
        String cspHeaderValue;

        if (useDefaultCSP) {
            cspHeaderValue = prepareDefaultCSP(httpServletRequest);
        } else {
            cspHeaderValue = responseHeaderContentSecurityPolicy;
        }

        // Add policies to all HTTP headers
        for (String header : this.cspHeaders) {
            httpServletResponse.setHeader(header, cspHeaderValue);
        }

        //set Access-Control-Allow-Origin
        if (INCLUDE_ACCESS_CONTROL_ALLOW_ORIGIN) {
            String origin = httpServletRequest.getScheme() + "://" + httpServletRequest.getServerName() + ":" + httpServletRequest.getServerPort();
            httpServletResponse.setHeader("Access-Control-Allow-Origin", origin);
            httpServletResponse.setHeader("Vary", "Origin");
        }

        filterChain.doFilter(request, response);
    }

    private String prepareDefaultCSP(final HttpServletRequest httpServletRequest) {
        StringBuilder policiesBuffer = new StringBuilder(this.policies);

        // Define loading policies for Scripts
        policiesBuffer.append(";").append("script-src " + ORIGIN_LOCATION_REF + " 'unsafe-eval'");

        if (INCLUDE_NONCE_VALUE) {
            // Add Script Nonce CSP Policy
            String scriptNonce = getNonce();
            policiesBuffer.append(" 'nonce-").append(scriptNonce).append("'");

            // Made available script nonce in view app layer
            httpServletRequest.setAttribute("CSP_SCRIPT_NONCE", scriptNonce);
            httpServletRequest.setAttribute("CSP_STYLE_NONCE", scriptNonce);
        } else {
            // It must be as a last one because of possibility to add nonce code
            policiesBuffer.append(" 'unsafe-inline'");
        }

        // If resource is a frame add Frame/Sandbox CSP policy
        String userAgent = httpServletRequest.getHeader("User-Agent");

        if (INCLUDE_SECURE_FRAMES && Objects.nonNull(userAgent)) {
            // Frame + Sandbox : Here sandbox allow nothing, customize sandbox options depending on your app....
            if (userAgent.contains("Edge")) {
                policiesBuffer.append(";").append("frame-src 'self'");
            } else {
                policiesBuffer.append(";").append("child-src 'self'");

                if (INCLUDE_MOZILLA_CSP_DIRECTIVES) {
                    policiesBuffer.append(";").append("frame-ancestors 'self'");
                }
            }

            if (includeSandboxForFrames && !userAgent.contains("Trident")) {
                policiesBuffer.append(";").append("sandbox allow-scripts allow-modals allow-same-origin allow-forms allow-popups allow-top-navigation-by-user-activation allow-popups-to-escape-sandbox allow-downloads");
            }
        }

        return policiesBuffer.toString();
    }

    private String getNonce() {
        //Generate a random number
        String randomNum = Integer.toString(this.prng.nextInt());
        //Get its digest
        byte[] digest = sha.digest(randomNum.getBytes());
        //Encode it into HEXA
        String scriptNonce = Hex.encodeHexString(digest);

        return scriptNonce;
    }

    @Override
    public void destroy() {

    }

}
