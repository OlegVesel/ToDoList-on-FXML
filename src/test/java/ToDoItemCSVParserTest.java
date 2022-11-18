import com.homeproject.todolist.domen.ToDoItem;
import com.homeproject.todolist.service.ToDoItemCSVParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ToDoItemCSVParserTest {
    private final ToDoItemCSVParser parser = new ToDoItemCSVParser();

    @Test
    public void shouldParseOneLine() throws Exception{
        final String line = "1476453101|23|123|false|2022-03-13|null" ;
        ToDoItem result = parser.parseLine(line);
        Assertions.assertEquals("23", result.getName());
        Assertions.assertNull(result.getDateExecute());
    }
}
