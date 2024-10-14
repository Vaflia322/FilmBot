package bot;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


class LogicDialogTest {
    @Test
    public void apiTest(){
        ApiFilm apiFilmMock = mock(ApiFilm.class);
        when(apiFilmMock.takeFilms("жанр","драма")).thenReturn("Фильм жанра драма");
        LogicDialog logicDialog= new LogicDialog(apiFilmMock);
        String input = "драма\nстоп";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        logicDialog.startDialog("жанр");
        verify(apiFilmMock).takeFilms("жанр","драма");
        System.setIn(System.in);
    }
}