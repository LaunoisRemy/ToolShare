package business.facade;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SessionFacadeTest {

    @Test
    public void checkCode() {
        // Arrange
        String code = "1234";

        // Act
        boolean check = SessionFacade.getInstance().checkCode(code);
        //TODO check mocking : method proxy mockito
        // Assert
        assertTrue(check);
    }
    @Test
    public void checkCode2() {
        // Arrange
        String code = "1";

        // Act
        boolean check = SessionFacade.getInstance().checkCode(code);

        // Assert
        assertTrue(check);
    }
}