package newproject.project.jwt;

public class JwtResponse {
    private String token;

    // Default constructor
    public JwtResponse() {
        this.token = null; // Initialize to avoid error
    }

    // Constructor with token
    public JwtResponse(String token) {
        this.token = token;
    }

    // Getter and Setter
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}