package business.facade;

import business.exceptions.ObjectNotFoundException;
import business.system.Category;
import dao.structure.CategoryDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class CategoryfacadeTest {

    @Mock
    CategoryDAO mockCategoryDao;

    @BeforeEach
    void setUp() {
        this.mockCategoryDao = mock(CategoryDAO.class);
    }

    @Test
    public void getCategory_categoryDoesntExist_throwObjectNotFoundException() {
        try(MockedStatic<CategoryDAO> categoryDAOMockedStatic = mockStatic(CategoryDAO.class)) {

            Category category = new Category(5,"cat title", true);

            when(this.mockCategoryDao.find(5)).thenReturn(category);
            categoryDAOMockedStatic.when(CategoryDAO::getInstance).thenReturn(this.mockCategoryDao);

            assertThrows(ObjectNotFoundException.class,() ->{
                CategoryFacade.getInstance().getCategory(5465);
            });

        }
    }

    @Test
    public void getCategory_returnCategory_noException() {
        try(MockedStatic<CategoryDAO> categoryDAOMockedStatic = mockStatic(CategoryDAO.class)) {

            Category category = new Category(5,"cat title", true);

            when(this.mockCategoryDao.find(5)).thenReturn(category);
            categoryDAOMockedStatic.when(CategoryDAO::getInstance).thenReturn(this.mockCategoryDao);

            assertEquals(category,CategoryFacade.getInstance().getCategory(5));

        } catch (ObjectNotFoundException e) {
            e.printStackTrace();
        }
    }

}
