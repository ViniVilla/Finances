package br.edu.ifsp.finances;

import br.edu.ifsp.finances.util.CleanDatabase;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseTest {

    @Autowired
    private CleanDatabase cleanDatabase;

    @BeforeEach
    public void clean(){
        cleanDatabase.clean();
    }

}
