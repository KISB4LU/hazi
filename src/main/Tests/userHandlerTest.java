package window.login;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.awt.Color;

import indicators.indicator;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class userHandlerTest {

    // This class tests the readUsers method in the userHandler class

    @Test
    public void testReadUsers_validJson() throws IOException {
        userHandler handler = new userHandler();
        String exampleJson = "[{\"name\":\"user1\"}, {\"name\":\"user2\"}]";
        BufferedReader reader = mock(BufferedReader.class);
        when(reader.readLine()).thenReturn(exampleJson, null);
        FileReader fileReader = mock(FileReader.class);
        Mockito.whenNew(FileReader.class).withArguments("src/main/data/users.json").thenReturn(fileReader);
        Mockito.whenNew(BufferedReader.class).withArguments(fileReader).thenReturn(reader);
        User[] result = handler.readUsers();
        assertEquals(2, result.length);
    }

    @Test
    public void testReadUsers_emptyJson() throws IOException {
        userHandler handler = new userHandler();
        String exampleJson = "[]";
        BufferedReader reader = mock(BufferedReader.class);
        when(reader.readLine()).thenReturn(exampleJson, null);
        FileReader fileReader = mock(FileReader.class);
        Mockito.whenNew(FileReader.class).withArguments("src/main/data/users.json").thenReturn(fileReader);
        Mockito.whenNew(BufferedReader.class).withArguments(fileReader).thenReturn(reader);
        User[] result = handler.readUsers();
        assertEquals(0, result.length);
    }

    @Test
    public void testReadUsers_ioException() throws IOException {
        userHandler handler = new userHandler();
        FileReader fileReader = mock(FileReader.class);
        Mockito.whenNew(FileReader.class).withArguments("src/main/data/users.json").thenThrow(new IOException("Test exception"));
        User[] result = handler.readUsers();
        assertNull(result);
    }

    @Test
    public void testReadUsers_corruptedJson() throws IOException {
        userHandler handler = new userHandler();
        BufferedReader reader = mock(BufferedReader.class);
        when(reader.readLine()).thenReturn("{this:is_not_json}", null);
        FileReader fileReader = mock(FileReader.class);
        Mockito.whenNew(FileReader.class).withArguments("src/main/data/users.json").thenReturn(fileReader);
        Mockito.whenNew(BufferedReader.class).withArguments(fileReader).thenReturn(reader);
        assertThrows(ClassCastException.class, () -> handler.readUsers());
    }
}

@Test
public void testReadUsers_normalScenario() throws IOException {
    userHandler handler = new userHandler();
    String exampleJson = "[{\"name\":\"user3\"}]";
    BufferedReader reader = mock(BufferedReader.class);
    when(reader.readLine()).thenReturn(exampleJson, null);
    FileReader fileReader = mock(FileReader.class);
    Mockito.whenNew(FileReader.class).withArguments("src/main/data/users.json").thenReturn(fileReader);
    Mockito.whenNew(BufferedReader.class).withArguments(fileReader).thenReturn(reader);
    User[] result = handler.readUsers();
    assertEquals(1, result.length);
    assertEquals("user3", result[0].getName());
}

@Test
public void testReadUsers_multipleValidUsers() throws IOException {
    userHandler handler = new userHandler();
    String exampleJson = "[{\"name\":\"user1\"}, {\"name\":\"user2\"}, {\"name\":\"user3\"}]";
    BufferedReader reader = mock(BufferedReader.class);
    when(reader.readLine()).thenReturn(exampleJson, null);
    FileReader fileReader = mock(FileReader.class);
    Mockito.whenNew(FileReader.class).withArguments("src/main/data/users.json").thenReturn(fileReader);
    Mockito.whenNew(BufferedReader.class).withArguments(fileReader).thenReturn(reader);
    User[] result = handler.readUsers();
    assertEquals(3, result.length);
    assertEquals("user1", result[0].getName());
    assertEquals("user2", result[1].getName());
    assertEquals("user3", result[2].getName());
}


@Test
public void testReadUsers_nonExistentFile() throws IOException {
    userHandler handler = new userHandler();
    FileReader fileReader = mock(FileReader.class);
    Mockito.whenNew(FileReader.class).withArguments("invalid/path/users.json").thenThrow(new IOException("Test exception"));
    Mockito.whenNew(BufferedReader.class).withArguments(fileReader).thenReturn(null);
    assertThrows(IOException.class, () -> handler.readUsers());
}

@Test
public void testReadUsers_nullJson() throws IOException {
    userHandler handler = new userHandler();
    BufferedReader reader = mock(BufferedReader.class);
    when(reader.readLine()).thenReturn(null);
    FileReader fileReader = mock(FileReader.class);
    Mockito.whenNew(FileReader.class).withArguments("src/main/data/users.json").thenReturn(fileReader);
    Mockito.whenNew(BufferedReader.class).withArguments(fileReader).thenReturn(reader);
    User[] result = handler.readUsers();
    assertNull(result);
}

@Test
public void testReadUsers_bufferedReaderException() throws IOException {
    userHandler handler = new userHandler();
    BufferedReader reader = mock(BufferedReader.class);
    when(reader.readLine()).thenThrow(new IOException("Test exception"));
    FileReader fileReader = mock(FileReader.class);
    Mockito.whenNew(FileReader.class).withArguments("src/main/data/users.json").thenReturn(fileReader);
    Mockito.whenNew(BufferedReader.class).withArguments(fileReader).thenReturn(reader);
    assertThrows(IOException.class, () -> handler.readUsers());
}

@Test
public void testWriteUsers_NullInput() {
    userHandler handler = new userHandler();
    assertThrows(RuntimeException.class, () -> handler.writeUsers(null));
}

@Test
public void testWriteUsers_ValidInput() throws IOException {
    userHandler handler = new userHandler();
    User[] users = new User[]{new User("user1")};
    handler.writeUsers(users);
    User[] readUsers = handler.readUsers();
    assertEquals(users.length, readUsers.length);
    assertEquals(users[0].getName(), readUsers[0].getName());
}

@Test
public void testWriteUsers_FileIOException() throws IOException {
    userHandler handler = new userHandler();
    User[] users = new User[]{new User("user1")};
    PrintWriter pw = mock(PrintWriter.class);
    Mockito.doThrow(new IOException("Test exception")).when(pw).println(anyString());
    assertThrows(RuntimeException.class, () -> handler.writeUsers(users));
}

@Test
public void testWriteUsers_EmptyInput() throws IOException {
    userHandler handler = new userHandler();
    User[] users = new User[0];
    handler.writeUsers(users);
    User[] readUsers = handler.readUsers();
    assertEquals(0, readUsers.length);
}
}
