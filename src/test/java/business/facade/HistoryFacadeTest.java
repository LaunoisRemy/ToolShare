package business.facade;


import business.system.offer.Offer;
import business.system.offer.ToolSate;
import business.system.user.User;
import dao.structure.HistoryDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class HistoryFacadeTest {

    @Mock
    HistoryDAO mockHistoryDao;
    SessionFacade mockSessionFacade;

    @BeforeEach
    void setUp() {
        this.mockHistoryDao = mock(HistoryDAO.class);
        this.mockSessionFacade = mock(SessionFacade.class);
    }

    @Test
    public void getAllOffers_returnOffers_noException() {
        try(MockedStatic<HistoryDAO> historyDAOMockedStatic = mockStatic(HistoryDAO.class)) {

            User user = new User("test", "test", "test", "RAaIRIIvNeeeEAvEANeIRNvvRNdvAiASIASeeNRieeiEAReSnnVeNRVSvIiERARVRNAIdieNIRIdeeeaAvAViReNSvvneevIevAVEAISeaIveeReAedARRvnndiidRAV", "test", "test", "EIeeSVRARInSadadIVnAEARvENdReAdI", true);
            Offer offer1 = new Offer("title", 12, "desc", ToolSate.EXCELLENT, true, user, null);
            Offer offer2 = new Offer("title2", 2, "desc", ToolSate.EXCELLENT, true, user, null);
            List<Offer> offers = new ArrayList<>();
            offers.add(offer1);
            offers.add(offer2);

            try (MockedStatic<SessionFacade> sessionFacadeMockedStatic = mockStatic(SessionFacade.class)) {

                when(this.mockHistoryDao.getHistory(user)).thenReturn(offers);
                historyDAOMockedStatic.when(HistoryDAO::getInstance).thenReturn(this.mockHistoryDao);

                when(this.mockSessionFacade.getUser()).thenReturn(user);
                sessionFacadeMockedStatic.when(SessionFacade::getInstance).thenReturn(this.mockSessionFacade);

                assertEquals(offers, HistoryFacade.getInstance().getAllOffers());
            }
        }
    }
}
