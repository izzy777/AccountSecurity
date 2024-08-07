package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.StringJoiner;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Customer;
import com.example.CustomerFactory;
import com.example.Token;
import com.example.util.JWTHelper;

@RestController
@RequestMapping("/token")
public class TokenApi {
    

    public static Token token;

    @GetMapping
    public String getAllTokens(){
        return "jwt-fake-token-asdfasdfasfa".toString();
    }

    @PostMapping
    public ResponseEntity<?> verifyCustomer(@RequestBody Customer customer){
        String username = customer.getName();
        String password = customer.getPassword();
        Token token;
        ResponseEntity<?> response;
        if(username.equals(customer.getName()) && username.equals(customer.getPassword()) && username != null && username.length() > 0 && verifyPassword(username, password)){
			token = createToken(username);
			response = ResponseEntity.ok(token);
			return response;			
		}
		return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    public boolean verifyPassword(String username, String password) {
        try {
            Customer customer = getNameFromCustomerAPI(username);
            return customer != null && username.equals(customer.getName()) && password.equals(customer.getPassword());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    private Customer getNameFromCustomerAPI(String username) throws IOException {
        URL url = new URL("http://localhost:8080/api/customers/byname/" + username);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("authorization","Bearer " + getAppUserToken().getToken());
    
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            StringJoiner joiner = new StringJoiner("\n");
            reader.lines().forEach(joiner::add);
            String response = joiner.toString();
            return CustomerFactory.getCustomer(response);
        } finally {
            conn.disconnect();
        }
    }

    public static Token getAppUserToken() {
		if(token == null || token.getToken() == null || token.getToken().length() == 0) {
			token = createToken("ApiClientApp");
		}
		return token;
	}
	
    private static Token createToken(String username) {
    	String packagePath = "com.example.TokenApi";
    	if( username.equalsIgnoreCase("ApiClientApp")) {
    		packagePath = "com.webage.auth.apis";
    	}
    	String token = JWTHelper.createToken(packagePath);
    	return new Token(token);
    }
}