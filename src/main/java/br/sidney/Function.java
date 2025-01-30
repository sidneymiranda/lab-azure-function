package br.sidney;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Optional;

/**
 * Azure Functions with HTTP Trigger.
 */
public class Function {
    /**
     * This function listens at endpoint "/api/HttpExample". Two ways to invoke it using "curl" command in bash:
     * 1. curl -d "HTTP Body" {your host}/api/HttpExample
     * 2. curl "{your host}/api/HttpExample?name=HTTP%20Query"
     */
    @FunctionName("HttpExample")
    public HttpResponseMessage run(
            @HttpTrigger(
                name = "req",
                methods = {HttpMethod.GET, HttpMethod.POST},
                authLevel = AuthorizationLevel.ANONYMOUS)
                HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) {
        
                context.getLogger().info("Recebendo requisição...");

                try {
                    String requestBody = request.getBody().orElse(null);
                    if (requestBody == null) {
                        return request.createResponseBuilder(HttpStatus.BAD_REQUEST)
                            .body("O corpo da requisição está vazio!")
                            .build();
                    }

                    ObjectMapper objectMapper = new ObjectMapper();
                    CpfRequest cpfRequest = objectMapper.readValue(requestBody, CpfRequest.class);

                    if (cpfRequest.cpf() == null || cpfRequest.cpf().isBlank()) {
                        return request.createResponseBuilder(HttpStatus.BAD_REQUEST)
                            .body("CPF não informado no corpo da requisição!")
                            .build();
                    }
                    
                    return request.createResponseBuilder(HttpStatus.OK)
                    .body("CPF válido!")
                    .build();
                } catch (JsonProcessingException e) {
                        return request.createResponseBuilder(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Erro ao processar a requisição: " + e.getMessage())
                        .build();
                }
            }

}
