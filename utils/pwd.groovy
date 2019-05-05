@Grapes(
    @Grab(group='org.springframework.security', module='spring-security-crypto', version='5.1.5.RELEASE')
)
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
println(bcrypt.encode("ziegler12"));

