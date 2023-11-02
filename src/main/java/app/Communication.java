package app;

import app.configs.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class Communication {

    private RestTemplate restTemplate;
    private HttpHeaders httpHeaders;
    private String URL ="http://94.198.50.185:7081/api/users";

    public Communication(RestTemplate restTemplate, HttpHeaders httpHeaders) {
        this.restTemplate = restTemplate;
        this.httpHeaders = httpHeaders;
        this.httpHeaders.set("Cookie",
                String.join(";", restTemplate.headForHeaders(URL).get("set-Cookie")));
    }

    public String getAnswer() {
        return addUser().getBody() + updateUser().getBody() + deleteUser().getBody();
    }
    private List<User> getAllUsers() {
        ResponseEntity<List<User>> responseEntity = restTemplate.exchange(URL, HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>() { });
        System.out.println(responseEntity.getHeaders());
        return responseEntity.getBody();
    }

    // Добавление пользователя - …/api/users ( POST )
    private ResponseEntity<String> addUser() {
        User user = new User(3L, "James", "Brown", (byte) 28);
        HttpEntity<User> entity = new HttpEntity<>(user, httpHeaders);
        return restTemplate.postForEntity(URL, entity, String.class);
    }

    // Изменение пользователя - …/api/users ( PUT )
    private ResponseEntity<String> updateUser() {
        User user = new User(3L, "Thomas", "Shelby", (byte) 5);
        HttpEntity<User> entity = new HttpEntity<>(user, httpHeaders);
        return restTemplate.exchange(URL, HttpMethod.PUT, entity, String.class, 3);
    }

    // Удаление пользователя - …/api/users /{id} ( DELETE )
    private ResponseEntity<String> deleteUser() {
        Map<String, Long> uriVariables = new HashMap<String, Long>() {{
            put("id", 3L);
        }};
        HttpEntity<String> entity = new HttpEntity<>(null, httpHeaders);
        return restTemplate.exchange(URL + "/{id}", HttpMethod.DELETE, entity, String.class, uriVariables);
    }
}
