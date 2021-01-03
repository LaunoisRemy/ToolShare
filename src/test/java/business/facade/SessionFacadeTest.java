package business.facade;

import business.exceptions.ObjectNotFoundException;
import business.exceptions.UserBannedException;
import business.exceptions.WrongPasswordException;
import business.system.user.User;
import dao.structure.UserDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SessionFacadeTest {

    UserDAO mockUserDao ;

    @BeforeEach
    void setUp() {
        mockUserDao = mock(UserDAO.class);
    }

    @Test
    public void login_badPassword_throwWrongPassword() throws WrongPasswordException, ObjectNotFoundException, UserBannedException {
        try(MockedStatic<UserDAO> userDAOMockedStatic = mockStatic(UserDAO.class)){
            User u = new User("test","test","test","RAaIRIIvNeeeEAvEANeIRNvvRNdvAiASIASeeNRieeiEAReSnnVeNRVSvIiERARVRNAIdieNIRIdeeeaAvAViReNSvvneevIevAVEAISeaIveeReAedARRvnndiidRAV","test","test","EIeeSVRARInSadadIVnAEARvENdReAdI",true);
            when(mockUserDao.getUserByEmail("test@gmail.com")).thenReturn(u);
            when(mockUserDao.getSalt("test@gmail.com")).thenReturn("EIeeSVRARInSadadIVnAEARvENdReAdI");
            userDAOMockedStatic.when(UserDAO::getInstance).thenReturn(mockUserDao);

            assertThrows(WrongPasswordException.class,() ->{
                SessionFacade.getInstance().login("test@gmail.com","wrongPassword");
            });

        }

    }


    @Test
    public void login_goodPassword_returnUser() throws WrongPasswordException, ObjectNotFoundException, UserBannedException {
        try(MockedStatic<UserDAO> userDAOMockedStatic = mockStatic(UserDAO.class)){
            User u = new User("test","test","test","RAaIRIIvNeeeEAvEANeIRNvvRNdvAiASIASeeNRieeiEAReSnnVeNRVSvIiERARVRNAIdieNIRIdeeeaAvAViReNSvvneevIevAVEAISeaIveeReAedARRvnndiidRAV","test","test","EIeeSVRARInSadadIVnAEARvENdReAdI",true);
            when(mockUserDao.getUserByEmail("test@gmail.com")).thenReturn(u);
            when(mockUserDao.getSalt("test@gmail.com")).thenReturn("EIeeSVRARInSadadIVnAEARvENdReAdI");
            userDAOMockedStatic.when(UserDAO::getInstance).thenReturn(mockUserDao);

            assertEquals(u,SessionFacade.getInstance().login("test@gmail.com","test"));


        }

    }
}