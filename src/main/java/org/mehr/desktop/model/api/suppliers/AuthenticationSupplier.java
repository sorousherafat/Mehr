package org.mehr.desktop.model.api.suppliers;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.hc.client5.http.async.methods.SimpleHttpRequest;
import org.apache.hc.client5.http.async.methods.SimpleHttpResponse;
import org.apache.hc.client5.http.async.methods.SimpleRequestBuilder;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.nio.AsyncClientEndpoint;
import org.mehr.desktop.App;
import org.mehr.desktop.model.api.Path;
import org.mehr.desktop.model.api.inputs.AuthenticationInput;
import org.mehr.desktop.model.api.responses.AuthenticationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Supplier;

public class AuthenticationSupplier extends APISupplier<String> {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationSupplier.class);

    private final AuthenticationInput input;

    public AuthenticationSupplier(HttpHost host, AsyncClientEndpoint endpoint, Supplier<String> tokenSupplier) {
        super(host, endpoint, tokenSupplier, Path.AUTHENTICATE, 1);
        String username = App.properties.getProperty("username");
        String password = App.properties.getProperty("password");
        this.input = new AuthenticationInput(username, password);
    }

    private String getBody() throws JsonProcessingException {
        return objectMapper.writeValueAsString(input);
    }

    @Override
    protected void doAction() throws Exception {
        SimpleHttpRequest request = getRequestBuilder(SimpleRequestBuilder::post).setBody(getBody(), ContentType.APPLICATION_JSON).build();
        execute(request);
    }

    @Override
    protected void doComplete(SimpleHttpResponse httpResponse) throws Exception {
        AuthenticationResponse response = objectMapper.readValue(httpResponse.getBodyText(), AuthenticationResponse.class);
        if (!response.isSuccess()) {
            throw new RuntimeException(response.getDescription());
        }
        success = true;
        state = response.getToken();
    }

    @Override
    protected void doFail(Exception e) throws Exception {
        logger.error("Request failed.", e);
    }

    @Override
    protected void doCancel() throws Exception {
        logger.error("Request cancelled.");
    }
}
