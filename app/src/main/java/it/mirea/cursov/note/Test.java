import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class MainActivityTest {

    @Mock
    private FirebaseAuth mockAuth;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testOnStart_WhenUserIsNull_ShouldStartLoginActivity() {
        // Arrange
        MainActivity activity = spy(new MainActivity());
        when(mockAuth.getCurrentUser()).thenReturn(null);
        doReturn(mockAuth).when(activity).getFirebaseAuth();
        doNothing().when(activity).startActivity(any(Intent.class));

        // Act
        activity.onStart();

        // Assert
        verify(activity).startActivity(any(Intent.class));
    }

    @Test
    void testOnStart_WhenUserIsNotNull_ShouldNotStartLoginActivity() {
        // Arrange
        MainActivity activity = spy(new MainActivity());
        when(mockAuth.getCurrentUser()).thenReturn(mock(FirebaseUser.class));
        doReturn(mockAuth).when(activity).getFirebaseAuth();
        doNothing().when(activity).startActivity(any(Intent.class));

        // Act
        activity.onStart();

        // Assert
        verify(activity, never()).startActivity(any(Intent.class));
    }

    @Test
    void testSetCategoryRecycler_ShouldSetCategoryAdapter() {
        // Arrange
        MainActivity activity = new MainActivity();
        RecyclerView categoryRecycler = mock(RecyclerView.class);
        CategoryAdapter categoryAdapter = mock(CategoryAdapter.class);
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(new Category(1, "Драма"));

        // Act
        activity.setCategoryRecycler(categoryList);

        // Assert
        verify(categoryRecycler).setLayoutManager(any(LinearLayoutManager.class));
        verify(categoryRecycler).setAdapter(categoryAdapter);
    }

    // Add more unit tests for other methods in MainActivity if needed

    // Helper method to mock FirebaseAuth instance
    private FirebaseAuth getFirebaseAuth() {
        return mock(FirebaseAuth.class);
    }
}