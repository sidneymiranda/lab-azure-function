package br.sidney;

import com.microsoft.azure.functions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class FunctionTest {


    @Mock
    private ExecutionContext context;

    @Mock
    private Logger logger;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(context.getLogger()).thenReturn(logger);
    }


    @SuppressWarnings("unchecked")
    @Test
    void testValidCpfRequest() {
        HttpRequestMessage<Optional<String>> request = mock(HttpRequestMessage.class);

        String jsonBody = "{\"cpf\": \"123.456.789-09\"}";
        when(request.getBody()).thenReturn(Optional.of(jsonBody));

        HttpResponseMessage.Builder responseBuilder = mock(HttpResponseMessage.Builder.class);
        HttpResponseMessage response = mock(HttpResponseMessage.class);
        when(request.createResponseBuilder(HttpStatus.OK)).thenReturn(responseBuilder);
        when(responseBuilder.body(anyString())).thenReturn(responseBuilder);
        when(responseBuilder.build()).thenReturn(response);

        Function function = new Function();
        HttpResponseMessage result = function.run(request, context);

        assertNotNull(result);
        verify(request).getBody();
        verify(responseBuilder).body("CPF válido!");
    }

    @SuppressWarnings("unchecked")
    @Test
    void testEmptyRequestBody() {
        HttpRequestMessage<Optional<String>> request = mock(HttpRequestMessage.class);

        when(request.getBody()).thenReturn(Optional.empty());

        HttpResponseMessage.Builder responseBuilder = mock(HttpResponseMessage.Builder.class);
        HttpResponseMessage response = mock(HttpResponseMessage.class);
        when(request.createResponseBuilder(HttpStatus.BAD_REQUEST)).thenReturn(responseBuilder);
        when(responseBuilder.body("O corpo da requisição está vazio!")).thenReturn(responseBuilder);
        when(responseBuilder.build()).thenReturn(response);

        Function function = new Function();
        HttpResponseMessage result = function.run(request, context);

        assertNotNull(result);
        verify(responseBuilder).body("O corpo da requisição está vazio!");
    }
        
}
