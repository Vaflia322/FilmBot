package bot;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;


class LogicDialogTest {
    @Test
    public void apiGenreDramaTest(){
        ApiFilm apiFilmMock = mock(ApiFilm.class);
        Dialog dialogMock = mock(Dialog.class);
        when(dialogMock.takeArg()).thenReturn("драма","хватит","стоп");
        Movies movies = new Movies();
        movies.addFilm(new Film("НАЗВАНИЕ","ОПИСАНИЕ","РЕЙТИНГ",List.of("драма")));
        when(apiFilmMock.takeFilms(TypeOfFilmRequest.GENRE,"драма")).thenReturn(movies);
        LogicDialog logicDialog= new LogicDialog(apiFilmMock, dialogMock);
        logicDialog.startDialog("жанр");
        verify(apiFilmMock).takeFilms(TypeOfFilmRequest.GENRE,"драма");
    }
    @Test
    public void apiGenreTestFault(){
        ApiFilm apiFilmMock = mock(ApiFilm.class);
        Dialog dialogMock = mock(Dialog.class);
        when(dialogMock.takeArg()).thenReturn("любой","хватит","стоп");
        when(apiFilmMock.takeFilms(TypeOfFilmRequest.GENRE,"любой")).thenReturn(new Fault("Вы ввели некоректный жанр"));
        LogicDialog logicDialog= new LogicDialog(apiFilmMock, dialogMock);
        logicDialog.startDialog("жанр");
        verify(apiFilmMock).takeFilms(TypeOfFilmRequest.GENRE,"любой");
    }
}