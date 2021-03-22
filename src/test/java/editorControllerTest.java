import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import textEditor.exceptions.LengthException;
import textEditor.exceptions.MixedUpException;
import textEditor.services.EditorServiceImpl;
import textEditor.services.IEditorService;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class editorControllerTest {

    @InjectMocks
    private EditorServiceImpl editorService;

    @BeforeEach
    public void beforeMethodCleanUp() {
        editorService.clear();
    }

    @Test
    void testTheAdd() {
        String expectedText = "Test1";
        String testText = editorService.add("Test1");
        Assertions.assertEquals(expectedText, testText, "Test to add new text");
    }

    @Test
    void testTheAddToIndex() {
        String expectedText = "T2est1";
        editorService.add("Test1");
        String testText = editorService.add("2", 1);
        Assertions.assertEquals(expectedText, testText, "Test to add new text to Index");
    }

    @Test
    void testTheBold() throws MixedUpException, LengthException {
        String expectedText = "<b>Test</b>";
        editorService.add("Test");
        String testText = editorService.bold(0, 4);
        Assertions.assertEquals(expectedText, testText, "Bold modification");
    }

    @Test
    void testTheUndo() {
        String expectedText = "Test";
        editorService.add("Test");
        editorService.add("NewLines");
        String testText = editorService.undo();
        Assertions.assertEquals(expectedText, testText, "Undo");
    }

    @Test
    void testTheRedo() {
        String expectedText = "TestNewLines";
        editorService.add("Test");
        editorService.add("NewLines");
        editorService.undo();
        String testText = editorService.redo();
        Assertions.assertEquals(expectedText, testText, "Redo");
    }

    @Test
    void testThePrint() {
        String expectedText = "TestNewLines";
        editorService.add("Test");
        editorService.add("NewLines");
        String testText = editorService.print();
        Assertions.assertEquals(expectedText, testText, "Print");
    }

}
