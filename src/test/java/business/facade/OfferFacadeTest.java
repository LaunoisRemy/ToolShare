package business.facade;

import business.exceptions.MissingParametersException;
import business.system.Category;
import business.system.offer.Offer;
import business.system.offer.ToolSate;
import business.system.user.User;
import dao.structure.CategoryDAO;
import dao.structure.OfferDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


class OfferFacadeTest {

    @Mock
    private OfferDAO mockOfferDao;
    private CategoryDAO mockCategoryDao;
    private SessionFacade mockSessionFacade;

    @BeforeEach
    void setUp() {
        this.mockOfferDao = mock(OfferDAO.class);
        this.mockCategoryDao = mock(CategoryDAO.class);
        this.mockSessionFacade = mock(SessionFacade.class);
    }

    @Test
    public void createOffer_incompleteDates_throwMissingParametersException() {
        try(MockedStatic<OfferDAO> offerDAOMockedStatic = mockStatic(OfferDAO.class)){

            User user = new User("test","test","test","RAaIRIIvNeeeEAvEANeIRNvvRNdvAiASIASeeNRieeiEAReSnnVeNRVSvIiERARVRNAIdieNIRIdeeeaAvAViReNSvvneevIevAVEAISeaIveeReAedARRvnndiidRAV","test","test","EIeeSVRARInSadadIVnAEARvENdReAdI",true);
            Category category = new Category("cat title", true);
            Offer offer = new Offer("title", 12, "desc", ToolSate.EXCELLENT, true, user, category);

            try(MockedStatic<CategoryDAO> categoryDAOMockedStatic = mockStatic(CategoryDAO.class)) {

                when(this.mockCategoryDao.getCategoryByName(category.getCategoryName())).thenReturn(category);
                categoryDAOMockedStatic.when(CategoryDAO::getInstance).thenReturn(this.mockCategoryDao);

                try(MockedStatic<SessionFacade> sessionFacadeMockedStatic = mockStatic(SessionFacade.class)) {

                    when(this.mockSessionFacade.getUser()).thenReturn(user);
                    sessionFacadeMockedStatic.when(SessionFacade::getInstance).thenReturn(this.mockSessionFacade);

                    when(this.mockOfferDao.create(offer)).thenReturn(offer);
                    offerDAOMockedStatic.when(OfferDAO::getInstance).thenReturn(this.mockOfferDao);

                    assertThrows(MissingParametersException.class,() ->{
                        OfferFacade.getInstance().createOffer("title",12, "desc", ToolSate.EXCELLENT, true, category.getCategoryName(), null, null);
                    });
                }
            }
        }
    }
}