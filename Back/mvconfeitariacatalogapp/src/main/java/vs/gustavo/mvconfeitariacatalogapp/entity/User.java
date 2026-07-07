package vs.gustavo.mvconfeitariacatalogapp.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private String login;
    @Column(nullable = false)
    private String password;
//    @OneToMany(fetch = FetchType.LAZY)
//    private List<Cake> catalog;

    private User(String name, String login, String password) {
        this.name = name;
        this.login = login;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    public List<Cake> getCatalog() {
//        return catalog;
//    }
//
//    public void setCatalog(List<Cake> catalog) {
//        this.catalog = catalog;
//    }

    public static final class Builder {

        private String name;
        private String login;
        private String password;

        private Builder(){}

        public static Builder builder() {
            return new Builder();
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder login(String val) {
            login = val;
            return this;
        }

        public Builder password(String val) {
            password = val;
            return this;
        }

        public User build() {
            return new User(name, login, password);
        }
    }
}
