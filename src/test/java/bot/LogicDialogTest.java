package bot;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;


class LogicDialogTest {
    @Test
    public void apiGenreDramaTest(){
        ApiFilm apiFilmMock = mock(ApiFilm.class);
        DialogInterface dialogInterfaceMock = mock(DialogInterface.class);
        when(dialogInterfaceMock.takeArg()).thenReturn("драма","хватит","стоп");
        Movies movies = new Movies();
        movies.addFilm(new Record("НАЗВАНИЕ","ОПИСАНИЕ","РЕЙТИНГ","ЖАНРЫ"));
        when(apiFilmMock.takeFilms(TypeOfFilmRequest.GENRE,"драма")).thenReturn(movies);
        LogicDialog logicDialog= new LogicDialog(apiFilmMock, dialogInterfaceMock);
        logicDialog.startDialog("жанр");
        verify(apiFilmMock).takeFilms(TypeOfFilmRequest.GENRE,"драма");
    }
    @Test
    public void apiGenreTestFault(){
        ApiFilm apiFilmMock = mock(ApiFilm.class);
        DialogInterface dialogInterfaceMock = mock(DialogInterface.class);
        when(dialogInterfaceMock.takeArg()).thenReturn("любой","хватит","стоп");
        when(apiFilmMock.takeFilms(TypeOfFilmRequest.GENRE,"любой")).thenReturn(new Fault("Вы ввели некоректный жанр"));
        LogicDialog logicDialog= new LogicDialog(apiFilmMock, dialogInterfaceMock);
        logicDialog.startDialog("жанр");
        verify(apiFilmMock).takeFilms(TypeOfFilmRequest.GENRE,"любой");
    }
}