package bot;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;


class LogicDialogTest {
    @Test
    public void apiGenreDramaTest(){
        ApiFilm apiFilmMock = mock(ApiFilm.class);
        ConsoleInterface consoleInterfaceMock = mock(ConsoleInterface.class);
        when(consoleInterfaceMock.takeArg()).thenReturn("драма","хватит","стоп");
        Movies movies = new Movies();
        movies.addFilm(new Film("НАЗВАНИЕ","ОПИСАНИЕ","РЕЙТИНГ"));
        when(apiFilmMock.takeFilms(TypeOfFilmRequest.GENRE,"драма")).thenReturn(movies);
        LogicDialog logicDialog= new LogicDialog(apiFilmMock,consoleInterfaceMock);
        logicDialog.startDialog("жанр");
        verify(apiFilmMock).takeFilms(TypeOfFilmRequest.GENRE,"драма");
    }
    @Test
    public void apiGenreTestException(){
        ApiFilm apiFilmMock = mock(ApiFilm.class);
        ConsoleInterface consoleInterfaceMock = mock(ConsoleInterface.class);
        when(consoleInterfaceMock.takeArg()).thenReturn("любой","хватит","стоп");
        when(apiFilmMock.takeFilms(TypeOfFilmRequest.GENRE,"любой")).thenThrow(new RuntimeException("Ошибка"));
        LogicDialog logicDialog= new LogicDialog(apiFilmMock,consoleInterfaceMock);
        try {
            logicDialog.startDialog("жанр");
        } catch(RuntimeException e){
            System.out.println(e.getMessage());
            assertEquals("Ошибка",e.getMessage());
        }
        verify(apiFilmMock).takeFilms(TypeOfFilmRequest.GENRE,"любой");
    }
}