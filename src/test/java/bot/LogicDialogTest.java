package bot;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


class LogicDialogTest {
    @Test
    public void apiGenreDramaTest(){
        ApiFilm apiFilmMock = mock(ApiFilm.class);
        ConsoleInterface consoleInterfaceMock = mock(ConsoleInterface.class);
        when(consoleInterfaceMock.takeArg()).thenReturn("драма","хватит","стоп");
        Films films = new Films();
        films.addFilm(1,"НАЗВАНИЕ","ОПИСАНИЕ","РЕЙТИНГ");
        when(apiFilmMock.takeFilms(Type.жанр,"драма")).thenReturn(films);
        LogicDialog logicDialog= new LogicDialog(apiFilmMock,consoleInterfaceMock);
        logicDialog.startDialog("жанр");
        verify(apiFilmMock).takeFilms(Type.valueOf("жанр"),"драма");
    }
    @Test
    public void apiGenreTestException(){
        ApiFilm apiFilmMock = mock(ApiFilm.class);
        ConsoleInterface consoleInterfaceMock = mock(ConsoleInterface.class);
        when(consoleInterfaceMock.takeArg()).thenReturn("любой","хватит","стоп");
        when(apiFilmMock.takeFilms(Type.жанр,"любой")).thenThrow(new RuntimeException("Ошибка"));
        LogicDialog logicDialog= new LogicDialog(apiFilmMock,consoleInterfaceMock);
        try {
            logicDialog.startDialog("жанр");
        } catch(RuntimeException e){
            System.out.println(e.getMessage());
            assertEquals("Ошибка",e.getMessage());
        }
        verify(apiFilmMock).takeFilms(Type.valueOf("жанр"),"любой");
    }
}