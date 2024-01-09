package team.mediasoft.study.spocktestcontainers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ApplicationContextStartTest {

    @Autowired
    private Application application;

    @Test
    public void checkContextStarts() {
        assertNotNull(application);
    }
}
